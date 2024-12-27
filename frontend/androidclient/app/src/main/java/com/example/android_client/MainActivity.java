package com.example.android_client;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

  EditText loginWidget, passwordWidget;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginWidget = findViewById(R.id.loginWidget);
        passwordWidget = findViewById(R.id.passwordWidget);
        loginButton = findViewById(R.id.loginButton);

        RequestQueue queue = Volley.newRequestQueue(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = loginWidget.getText().toString();
                String password = passwordWidget.getText().toString();

                if (login.isEmpty() || password.isEmpty()) {
                    return;
                }

                try {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.3:3000/login",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println("Response: " + response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("login", login);
                            params.put("password", password);
                            return params;
                        }
                    };

                    queue.add(stringRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
