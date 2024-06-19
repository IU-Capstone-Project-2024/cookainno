import os
from mistralai.client import MistralClient
from mistralai.models.chat_completion import ChatMessage
import config

# TODO: написать функцию, котрая принимает список ингридиентов и возвращает рецепт (словарь)
# TODO: генерируем 5 названий рецептов по ингредиентам, генерируем подробный рецепт по названию

# api_key = os.environ["MISTRAL_API_KEY"]

api_key = config.api_key
model = config.model
client = MistralClient(api_key=api_key)


def generate_recipes(ingredients):
    chat_response = client.chat(
        model=model,
        messages=[ChatMessage(role="user",
                              content=f"Propose recipes in which {ingredients} can be used. You need to provide 1 "
                                      f"variants of meals. It is sufficient just to show the name, ingredients list "
                                      f"for, and instruction for the preparation for each meal. Compose a response in "
                                      f"json format with fields: 'name', 'ingredients', and 'instruction'.")]
    )

    return chat_response.choices[0].message.content


# plan: normal consumption/deficit/surplus
def daily_advice(plan):
    chat_response = client.chat(
        model=model,
        messages=[ChatMessage(role="user",
                              content=f"Provide a short one-sentence daily advice for the calorie {plan}.")]
    )
    return chat_response.choices[0].message.content

# request = input("Enter the ingredients: ")
# print(generate_recipes(request))
