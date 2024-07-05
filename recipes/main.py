from typing import List
from fastapi import FastAPI, Query
from starlette.responses import FileResponse

import mistral
import image_generation

app = FastAPI()


@app.get("/")
def read_root():
    return "No request found"


@app.get("/ingredients/")
def read_ingredient(items: List[str] = Query(None)):
    # Usage example: http://127.0.0.1:8000/ingredients/?items=apple&items=milk&items=strawberry%20jam
    return mistral.generate_recipes(items)


@app.get("/advice/{query}")
def read_advice(query: str):
    # Usage example: http://127.0.0.1:8000/advice/deficit
    return mistral.daily_advice(query)


@app.get("/generate_image/{query}")
def generate_image(query: str):
    # Usage example: http://127.0.0.1:8000/generate_image/chicken%20pasta
    img_path = image_generation.download_image(query)
    if not img_path.is_file():
        return {"error": "Image not found"}
    return FileResponse(img_path)
