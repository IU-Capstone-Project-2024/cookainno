import requests
from bs4 import BeautifulSoup
import urllib.request


def download_images(query):
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
            break
        except Exception as e:
            print(e)


meal = input()
download_images(meal)

