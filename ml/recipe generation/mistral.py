import os
from mistralai.client import MistralClient
from mistralai.models.chat_completion import ChatMessage
from fastapi.responses import JSONResponse
import json
import config

# api_key = os.environ["MISTRAL_API_KEY"]

api_key = config.api_key
model = config.model
client = MistralClient(api_key=api_key)


def generate_recipes(ingredients):
    """
    Generate recipes based on a list of ingredients.

    Args:
        ingredients (list): A list of ingredients.

    Returns:
        JSONResponse: A response containing the generated recipes as a JSON array.
    """
    chat_response = client.chat(
        model=model,
        messages=[ChatMessage(role="user",
                              content=f"Propose recipes in which {ingredients} can be used. You need to provide 5 "
                                      f"variants of meals. It is sufficient just to show the name, ingredients list "
                                      f"for, and instruction for the preparation for each meal. Compose a response in "
                                      f"json format with fields: 'name', 'ingredients', and 'instruction'.")]
    )
    # Parse the string response to a Python object
    recipes_list = json.loads(chat_response.choices[0].message.content)
    # Return a JSONResponse with the list of recipes
    return JSONResponse(content=recipes_list)


def daily_advice(plan):
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
