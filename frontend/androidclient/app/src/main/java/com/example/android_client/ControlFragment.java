package com.example.android_client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ControlFragment extends Fragment {

  private EditText editTextDelay;
  private Button buttonStartStop, buttonSetDelay;

  private final String rpi = "http://192.168.0.3:8000";

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_control, container, false);
    editTextDelay = view.findViewById(R.id.editTextDelay);
    buttonStartStop = view.findViewById(R.id.buttonStartStop);
    buttonSetDelay = view.findViewById(R.id.buttonSetDelay);

    buttonStartStop.setOnClickListener(v -> startStopEngine());
    buttonSetDelay.setOnClickListener(v -> setDelay());

    return view;
  }

  private void startStopEngine() {
    sendRequest(rpi + "/startstop", "");
  }

  private void setDelay() {
    String delayStr = editTextDelay.getText().toString();
    int delay;
    try {
      delay = Integer.parseInt(delayStr);
      if (delay < 1 || delay > 43200) { // 43200 минут = 30 дней
        Toast.makeText(getActivity(), "Время должно быть от 1 до 43200 минут", Toast.LENGTH_SHORT).show();
        return;
      }
      sendRequest(rpi + "/delay?minutes=" + delay, "");
    } catch (NumberFormatException e) {
      Toast.makeText(getActivity(), "Введите корректное число", Toast.LENGTH_SHORT).show();
    }
  }

  private void sendRequest(String urlString, String requestBody) {
    new Thread(() -> {
      try {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        OutputStream os = connection.getOutputStream();
        os.write(requestBody.getBytes());
        os.flush();
        os.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
          getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Запрос отправлен", Toast.LENGTH_SHORT).show());
        } else {
          getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Ошибка: " + responseCode, Toast.LENGTH_SHORT).show());
        }
      } catch (Exception e) {
        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show());
      }
    }).start();
  }
}
