from typing import List
from fastapi import FastAPI, Query, File, UploadFile
import shutil
import os
import recipe_generation.mistral as mistral
from ingredient_detection.detect import GroceryItemDetector

app = FastAPI()

model_path = './ingredient_detection/models/model_1.pt'
data_path = './ingredient_detection/data.yaml'
confidence = 0.5
detector = GroceryItemDetector(model_path, data_path, confidence)


@app.get("/")
def read_root():
    return "No request found"


@app.get("/ingredients/")
def read_ingredient(items: List[str] = Query(None)):
    # Usage example: http://127.0.0.1:8000/ingredients/?items=apple&items=milk&items=strawberry%jam

    return mistral.generate_recipes(items)


@app.get("/advice/{query}")
def read_advice(query: str):
    # Usage example: http://127.0.0.1:8000/advice/deficit
    return mistral.daily_advice(query)


@app.post("/detect/")
async def detect_ingredients(file: UploadFile = File(...)):
    # Usage: curl -X POST "http://127.0.0.1:8000/detect/" -F "file=@/path/to/your/image.jpg"
    file_location = f"{file.filename}"
    with open(file_location, "wb+") as file_object:
        shutil.copyfileobj(file.file, file_object)

    detected_items = detector.detect(file_location)
    os.remove(file_location)
    return detected_items

