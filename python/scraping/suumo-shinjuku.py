from time import sleep
from bs4 import BeautifulSoup
import requests
from pprint import pprint
import pandas as pd

url    = 'https://suumo.jp/chintai/tokyo/sc_shinjuku/?page={}'
d_list = []

for i in range(1, 4):
    print(f'd_listの数:  {len(d_list)}')
    target_url = url.format(i)

    r    = requests.get(target_url)
    # サーバーに負荷をかけないように
    sleep(1)
    soup = BeautifulSoup(r.text, 'html.parser')

    contents = soup.find_all('div', class_='cassetteitem')
    for content in contents:
        detail = content.find('div', class_='cassetteitem_content')
        table  = content.find('table', class_='cassetteitem_other')

        # 物件情報
        title   = detail.find('div', class_='cassetteitem_content-title').text
        address = detail.find('li', class_='cassetteitem_detail-col1').text
        access  = detail.find('li', class_='cassetteitem_detail-col2').text
        age     = detail.find('li', class_='cassetteitem_detail-col3').text

        # 部屋情報
        tr_tags = table.find_all('tr', class_='js-cassette_link')
        for tr_tag in tr_tags:
            floor, price, first_fee, capacity = tr_tag.find_all('td')[2:6]
            ## 賃料と管理費
            fee, management_fee = price.find_all('li')
            ## 敷金と礼金
            deposit, gratuity = first_fee.find_all('li')
            ## まどりと面積
            madori, menseki = first_fee.find_all('li')

            d = {
                'title': title,
                'address': address,
                'access': access,
                'age': age,
                'floor': floor.text,
                'fee': fee.text,
                'management_fee': management_fee.text,
                'deposit': deposit.text,
                'gratuity': gratuity.text,
                'madori': madori.text,
                'menseki': menseki.text
            }
            d_list.append(d)

# オブジェクトをデータフレームに変換
df = pd.DataFrame(d_list[:1])

# csvで保存
df.to_csv('suumo-shinjuku.csv', encoding='utf-8-sig')
