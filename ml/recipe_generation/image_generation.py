import json
import os

import requests
from dotenv import load_dotenv

load_dotenv()
api_key = os.getenv("SERPER_API_KEY")


def get_image_url(query):
    # API endpoint and payload setup
    url = "https://google.serper.dev/images"
    payload = json.dumps({"q": query})
    headers = {
        'X-API-KEY': api_key,
        'Content-Type': 'application/json'
    }

    # Make the request to the Serper API
    response = requests.post(url, headers=headers, data=payload)

    if response.status_code == 200:
        data = response.json()
        if 'images' in data and data['images']:
            image_url = data['images'][0]['imageUrl']
            return image_url
        else:
            print("No images found in response")
    else:
        print(f"Failed to retrieve images, status code: {response.status_code}")
    return None
