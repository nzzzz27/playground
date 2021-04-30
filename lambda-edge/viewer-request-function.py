import requests
import re
import asyncio

BASE_URL = 'https://s3.amazonaws.com/webp-lambda-edge-test';
TIMEOUT  = 3 * 1000;

def lambda_viewer_request_function(event):
  request = event['Records'][0]['cf']['request']

  if re.compile('(\.jpg|\.jpeg|\.png)$').search(request['uri']):
    webpAcceptable = False

    try:
      if re.compile('image/webp').search(request['headers']['accept'][0]['value']):
        webpAcceptable = True
    except TypeError as e:
        print('[error] ' + e)

    if webpAcceptable:
       webpUri = request['uri'] + '.webp'
       res     = requests.head(BASE_URL + webpUri)
       print(res.status_code)

       if res.status_code >= 200 and res.status_code < 300:
           request['uri'] = webpUri
           print(request)

  return request

data = {
  "Records": [
    {
      "cf": {
        "config": {
          "distributionDomainName": "d111111abcdef8.cloudfront.net",
          "distributionId": "EDFDVBD6EXAMPLE",
          "eventType": "viewer-request",
          "requestId": "4TyzHTaYWb1GX1qTfsHhEqV6HUDd_BzoBZnwfnvQc_1oF26ClkoUSEQ=="
        },
        "request": {
          "clientIp": "203.0.113.178",
          "headers": {
            "host": [
              {
                "key": "Host",
                "value": "d111111abcdef8.cloudfront.net"
              }
            ],
            "user-agent": [
              {
                "key": "User-Agent",
                "value": "curl/7.66.0"
              }
            ],
            "accept": [
              {
                "key": "accept",
                "value": "*/*, image/webp"
              }
            ]
          },
          "method": "GET",
          "querystring": "",
          "uri": "/sample.png"
        }
      }
    }
  ]
}
lambda_viewer_request_function(data)
