from fastapi import FastAPI

from app.core.routes import register_routers

register_routers(app)