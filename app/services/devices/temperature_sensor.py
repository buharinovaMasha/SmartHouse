import Adafruit_DHT

class TemperatureSensorService:

    SENSOR = Adafruit_DHT.DHT22
    PIN = 4  # GPIO пин, к которому подключен датчик

    @staticmethod
    def get_temperature_and_humidity():
        humidity, temperature = Adafruit_DHT.read_retry(TemperatureSensorService.SENSOR, TemperatureSensorService.PIN)
        if humidity is None or temperature is None:
            return {"error": "Failed to read from sensor"}
        return {"temperature": temperature, "humidity": humidity}