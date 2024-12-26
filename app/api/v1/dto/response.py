from pydantic import BaseModel


class TokenResponse(BaseModel):
    access_token: str
    expire_in: int
    token_type: str
    scope: str

class TemperatureResponse(BaseModel):
    temperature: float
    humidity: float
    message: str

class DeviceResponse(BaseModel):
    status: str
    device: str
