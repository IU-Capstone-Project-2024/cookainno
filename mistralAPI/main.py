from typing import List
from fastapi import FastAPI, Query
import mistral

app = FastAPI()


@app.get("/")
def read_root():
    return {"Hello": "World"}


# @app.get("/ingredients/{items}")
# def read_item(items: List[str]):
#     return mistral.generate_recipes(items)
#     # return {"item_id": item_id, "q": q}

@app.get("/ingredients/")
def read_ingredient(items: List[str] = Query(None)):
    return mistral.generate_recipes(items)


@app.get("/advice/{query}")
def read_advice(query: str):
    return mistral.daily_advice(query)
