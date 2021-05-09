# Serverless Framework
## セットアップ
```
# install serverless
$ yarn global add serverless

# check if it's installed
$ serverless --version
# Framework Core: 2.40.0
# Plugin: 4.5.3
# SDK: 4.2.2
# Components: 3.9.2
``

## プロジェクト作成
```
serverless create --template aws-nodejs12 --name sample-service --path sample-service
```
以下のファイルが生成される。
```
└── sample-service
    ├── event.json
    ├── handler.js
    └── serverless.yml
```
