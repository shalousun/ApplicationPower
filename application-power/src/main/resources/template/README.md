<h1 align="center">${applicationName}</h1>

## 项目简介

请补充你的项目说明。
## 主要技术

- SpringBoot 2.5.6
- Mybatis Starter 2.2.0
## 构建部署操作

构建和部署操作请参考本项目docs目录中的《DEPLOY.md》和《DOCKER.md》。
## 项目API文档

项目使用无代码侵入式的smart-doc来生成文档。可以通过idea的Maven Helper的plugins中找到smart-doc的插件。
然后通过插件来生成文档，默认的生成html5格式的api文档存放于`static/doc`中，在测试环境中可以在启动后访问api文档。
访问路径为
```
http(https)://{base_url}/doc/index.html
```
如果要查看项目接口postman collection的json文件可以参考下面url获取，
你可以将改postman collection导入到postman中做接口测试。
```
http(https)://{base_url}/doc/postman.json
```
如果要查看OpenApi 3.0的接口文档可以访问
```
http(https)://{base_url}/doc/OpenApi3.0.json
```
smart-doc更多使用配置请参考[《smart-doc官方wiki》](https://gitee.com/smart-doc-team/smart-doc/wikis)
## Jenkins构建项目
项目内置了Jenkins slave(主要是k8s上弹性部署的Jenkins)分布式构建的pipeline模板Jenkinsfile。
如果有构建环境可以直接根据实际情况修改后直接使用pipeline模板构建。
## 项目需求与设计文档

- [《xxx需求文档》](https://gitee.com/smart-doc-team/smart-doc/wikis)
- [《xxx设计文档》](https://gitee.com/smart-doc-team/smart-doc/wikis)

