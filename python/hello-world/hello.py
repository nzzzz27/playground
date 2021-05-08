'''
PythonでPromise
https://qiita.com/HelloRusk/items/cbb9cb707fe927f8454b
'''
# hello出力1秒後にworldを出力
import asyncio

async def main():
    print('hello')
    await asyncio.sleep(1)
    print('world')

asyncio.run(main())

'''
HEADメソッドを使う
https://www.w3schools.com/python/ref_requests_head.asp
'''
#HEADメソッドのstatus codeを出力
import requests

#the required first parameter of the 'head' method is the 'url':
x = requests.head('https://www.w3schools.com/python/demopage.php')

#print the response headers (the HTTP headers of the requested file):
print(x.status_code)
