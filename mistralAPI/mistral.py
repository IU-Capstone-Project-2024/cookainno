import json
import os
from typing import List
from mistralai.client import MistralClient
from mistralai.models.chat_completion import ChatMessage
import config

# TODO: exceptions, context (нет рецептам из тараканов), role="system", лимит токенов

# api_key = os.environ["MISTRAL_API_KEY"]

api_key = config.api_key
model = config.model
client = MistralClient(api_key=api_key)


def generate_recipes(ingredients: List[str]) -> str:
    """
    Generate mistralAPI based on a list of ingredients.
    Args:
        ingredients (list): A list of ingredients.
    Returns:
        str: A JSON string containing the generated mistralAPI.
    """
    chat_response = client.chat(
        model=model,
        response_format={"type": "json_object"},
        messages=[ChatMessage(role="system",
                              content="You are the mistralAPI advisor. You need to provide mistralAPI with the use of "
                                      "client requested ingredients. Add extra ingredients only in case of crucial "
                                      "lack of entered items. In case of not eatable ingredients response with "
                                      "'Not food'."),
                  ChatMessage(role="user",
                              content=f"Propose mistralAPI in which {ingredients} can be used. You need to provide 5 "
                                      f"variants of meals. It is sufficient just to show the name, ingredients list "
                                      f"for, and instruction for the preparation for each meal. Compose a response as "
                                      f"JSON object 'mistralAPI': list of mistralAPI with fields: 'name', 'ingredients', "
                                      f"and 'instruction'.")]
    )
    response = chat_response.choices[0].message.content
    print(type(response))
    return response


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
