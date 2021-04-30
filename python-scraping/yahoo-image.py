from time import sleep
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
import pandas as pd

chrome_path = '/Users/nozomi/Downloads/chromedriver'

options = Options()
options.add_argument('--incognito')

driver = webdriver.Chrome(executable_path=chrome_path, options=options)

# Seleniumでurlを起動し、5秒後に閉じる（クローリング）
url = 'https://search.yahoo.co.jp/image'
driver.get(url)
sleep(3)

# ワードを検索する
query = 'プログラミング'
search_box = driver.find_element_by_class_name('SearchBox__searchInput')
search_box.send_keys(query)
search_box.submit()
sleep(3)

d_list = []
height = 1000
while height < 1100:
    # JSを実行
    driver.execute_script('window.scrollTo(0, {});'.format(height))
    height += 100
    print(height)
    sleep(1)

# 画像の要素を選択する
elements = driver.find_elements_by_class_name('sw-Thumbnail')
for i, element in enumerate(elements, start=1):
    filename = f'{query}_{i}'
    yahoo_image_url = element.find_element_by_tag_name('img').get_attribute('src')
    yahoo_image_alt = element.find_element_by_tag_name('img').get_attribute('alt')
    raw_url         = element.find_element_by_class_name('sw-ThumbnailGrid__details').get_attribute('href')

    d = {
        'filename': filename,
        'raw_url': raw_url,
        'yahoo_image_url': yahoo_image_url,
        'yahoo_image_alt': yahoo_image_alt
    }
    d_list.append(d)
    sleep(2)

df = pd.DataFrame(d_list)
df.to_csv('yahoo-image.csv', encoding='utf-8-sig')

driver.quit()
