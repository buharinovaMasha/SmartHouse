from app.services.devices.base import BaseDeviceService


class HeaterService(BaseDeviceService):
   device_name = "heater"

@classmethod
def initialize(cls):
        cls.initialize_gpio(pin=17)