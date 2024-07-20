import os
import json

from typing import List

from dotenv import load_dotenv
from mistralai.client import MistralClient
from mistralai.models.chat_completion import ChatMessage

from recipe_generation.image_generation import get_image_url

# TODO: exceptions, json-conversion, context (нет рецептам из тараканов), role="system"

load_dotenv()
api_key = os.getenv("MISTRAL_API_KEY")
model = os.getenv("MODEL")

client = MistralClient(api_key=api_key)


def generate_recipes(ingredients: List[str]) -> 'str':
    """
    Generate recipes based on a list of ingredients.
    Args:
        ingredients (list): A list of ingredients.
    Returns:
        str: A JSON string containing the generated recipes.
    """
    chat_response = client.chat(
        model=model,
        response_format={"type": "json_object"},
        messages=[ChatMessage(role="system",
                              content="You are the recipes advisor. You need to provide recipes with the use of "
                                      "client requested ingredients. Add extra ingredients only in case of crucial "
                                      "lack of entered items. In case of not eatable ingredients response with "
                                      "'Uneatable food'."),
                  ChatMessage(role="user",
                              content=f"Propose recipes in which {ingredients} can be used. You need to provide 2 "
                                      f"variants of meals. It is sufficient just to show the name, ingredients list "
                                      f"for, and instruction for the preparation for each meal. Compose a response as "
                                      f"JSON object 'recipes': list of recipes with fields: 'name', 'ingredients', "
                                      f"and 'instruction'.")]
    )
    response = json.loads(chat_response.choices[0].message.content)

    for recipe in response.get("recipes", []):
        recipe_name = recipe.get("name", "")
        image_url = get_image_url(recipe_name)
        recipe["image_url"] = image_url

    return json.dumps(response, indent=4)


def daily_advice(plan: str) -> str:
    """
    Generate daily advice based on a calorie plan.
    Args:
        plan (str): The calorie plan (normal consumption, deficit, or surplus).
    Returns:
        str: A short one-sentence daily advice.
    """
    chat_response = client.chat(
        model=model,
        messages=[ChatMessage(role="user",
                              content=f"Provide a short one-sentence daily advice for the calorie {plan}.")]
    )
    return chat_response.choices[0].message.content

# request = input("Enter the ingredients: ")
# print(generate_recipes(request))
