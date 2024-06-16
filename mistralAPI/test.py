import os
from mistralai.client import MistralClient
from mistralai.models.chat_completion import ChatMessage

# TODO: написать функцию, котрая принимает список ингридиентов и возвращает рецепт (словарь)

# api_key = os.environ["MISTRAL_API_KEY"]
api_key = "5apUycqPJOt0iEN5fiv3RztSeV9SLhrC"
model = "open-mixtral-8x22b"

request = input("Enter the ingredients: ")
ingredients = request.split(",")

client = MistralClient(api_key=api_key)

chat_response = client.chat(
    model=model,
    messages=[ChatMessage(role="user", content=f"Propose a recepies in which {request} can be used. You need to provide 3 variants of meals. It is sufficient just to show the name and ingredients list for each meal.")]
)

print(chat_response.choices[0].message.content)