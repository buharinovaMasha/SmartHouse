from gpiozero import OutputDevice
from app.api.v1.dto.response import DeviceResponse

class BaseDeviceService:

    def __init__(self, pin: int, device_name: str):
        self.device_name = device_name
        self.relay = OutputDevice(pin, active_high=True, initial_value=False)

    def set_state(self, state: bool) -> DeviceResponse:
        if state:
            self.relay.on()
        else:
            self.relay.off()
        return DeviceResponse(
            status="on" if state else "off",
            device=self.device_name,
        )

    def get_status(self) -> DeviceResponse:
        status = "on" if self.relay.value else "off"
        return DeviceResponse(
            status=status,
            device=self.device_name,
        )