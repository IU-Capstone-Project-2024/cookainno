import requests
from bs4 import BeautifulSoup
import urllib.request
from pathlib import Path
import os


def download_image(query):
    query = query.split()
    name = '_'.join(query)
    query = '+'.join(query)
    url = "https://www.google.co.in/search?q=" + query + "&source=lnms&tbm=isch"
    headers = {'User-Agent': 'Mozilla/5.0'}
    response = requests.get(url, headers=headers)
    soup = BeautifulSoup(response.text, 'html.parser')
    images = soup.find_all('img')
    for i in range(1, len(images)):
        try:
            urllib.request.urlretrieve(images[i]['src'], f"{name}.jpg")
            img_path = Path(f"{name}.jpg")
            # os.remove(f"{name}.jpg")
            return img_path
        except Exception as e:
            print(e)


# meal = input()
# download_image(meal)

