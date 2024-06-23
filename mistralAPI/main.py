from typing import List
from fastapi import FastAPI, Query
import mistral

app = FastAPI()


@app.get("/")
def read_root():
    return {"Hello": "World"}


@app.get("/ingredients/")
def read_ingredient(items: List[str] = Query(None)):
    # Usage example: http://127.0.0.1:8000/ingredients/?items=apple&items=milk&items=strawberry%jam
    return mistral.generate_recipes(items)


@app.get("/advice/{query}")
def read_advice(query: str):
    # Usage example: http://127.0.0.1:8000/advice/deficit
    return mistral.daily_advice(query)
