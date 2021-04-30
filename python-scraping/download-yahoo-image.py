import os
from time import sleep

import pandas as pd
import requests

IMAGE_DIR = './images/'

df = pd.read_csv('yahoo-image.csv')

if os.path.isdir(IMAGE_DIR):
    print("既にあります")
else:
    os.makedirs(IMAGE_DIR)

# 画像の保存
for file_name, yahoo_image_url in zip(df.filename[:5], df.yahoo_image_url):
    image = requests.get(yahoo_image_url)
    with open(IMAGE_DIR + file_name + '.jpg', 'wb') as file:
        file.write(image.content)

    sleep(2)
