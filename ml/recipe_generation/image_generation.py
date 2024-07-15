import requests
import json, os
from pathlib import Path
from dotenv import load_dotenv

load_dotenv()
api_key = os.getenv("SERPER_API_KEY")
def download_image(query):
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
            name = '_'.join(query.split())
            # Ensure the images directory exists
            images_dir = Path('./recipe_generation/images')
            images_dir.mkdir(exist_ok=True)
            img_path = images_dir / f"{name}.jpg"

            # Download the image
            image_response = requests.get(image_url, stream=True)
            if image_response.status_code == 200:
                with open(img_path, 'wb') as file:
                    for chunk in image_response.iter_content(1024):
                        file.write(chunk)
                return img_path
            else:
                print(f"Failed to download image, status code: {image_response.status_code}")
        else:
            print("No images found in response")
    else:
        print(f"Failed to retrieve images, status code: {response.status_code}")
    return None
