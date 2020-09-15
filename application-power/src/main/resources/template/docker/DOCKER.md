项目说明

# 环境要求

**构建：**  maven 3+

**系统：** linux /windows

**容器：** docker

**java：** jdk 1.8

# 构建镜像

第一种方式：使用maven命令
```
# mvn clean package docker:build -DskipTests
```
第二种方式【推荐】：使用docker.sh
```
//直接用sh执行
# sh docker.sh
//赋予权限再执行
# chmod 0755 docker.sh
# ./docker.sh 或 
// 构建时使用--env指定部署环境配置，如构建开发环境dev的镜像
# sh docker.sh --env dev
```
**注意：**  以上命令都是在需要构建的项目根目录运行

# docker.sh脚本说明

docker.sh目前针对maven环境下的构建处理已经比较完善，能够自动根据pom.xml中的配置自动构建镜像名称，
即便你修改pom.xml中的项目配置，也可以不理Dockerfile中的jar名称，docker自动在构建前修正Dockerfile，从目前来看docker.sh
的容错度比较高的，并且该脚本无需做修改就可移植到其他maven项目。

目前ApplicationPower中约定速成的镜像规则是：$GROUP/$PROJECT_NAME:$APP_VERSION，其中$GROUP代码group当前使用的基础包名；$PROJECT_NAME是
ApplicationPower生成项目时指定的项目名；$APP_VERSION目前使用的版本都是项目初期的0.0.1-SNAPSHOT。

docker.sh目前已经默认内置了将镜像推送到自建仓库的功能，需要则将脚本中的命令去注释打开即可。
# docker部署
```
docker run -dp $SERVER_PORT:$SERVER_PORT -t ${basePackage}/${applicationNameLowerCase}:1.0
```
## 查看docker部署日志
```
docker exec -it [containerId] /bin/sh
```
# 使用kubernetes部署
- 在使用k8s部署时在构建前可将docker.sh脚本中的docker run注释掉
```
# running container
docker run -dp $SERVER_PORT:$SERVER_PORT -t \${MYIMAGE}
```
- 启用docker.sh中的镜像推送,将前面的#移除

```
# docker login \${DOCKER_REGISTRY} -u admin -p admin123
echo "INFO：Starting push image of \${MYIMAGE} to docker registry \${DOCKER_REGISTRY}"
# docker tag \${MYIMAGE}  \${DOCKER_REGISTRY}/\${MYIMAGE}
# docker push \${DOCKER_REGISTRY}/\${MYIMAGE}
```
- 创建pod和service

ApplicationPower已经在项目中自动为创建了一个yaml的k8s部署配置。只需要做简单修改便可用于直接
创建pod和service.

First:修改yaml的images为自己的images，可以从harbor上直接获取。
```
image: harbor-registry/test/${basePackage}/${applicationNameLowerCase}:1.0
```
Second:修改端口
```
containerPort: 8080 //修改应用启动端口
port: 8080 //修改应用启动端口
nodePort: 30011 //暴露给外部访问的端口，默认范围30000-32767
```
Third:创建pod和service
```
kubectl apply -f ${deployment_cfg}
```
# 项目结构

项目标准结构(此图已省略部分)
```
├─docs
│      DOCKER.md
├─k8s
│      deployment.yaml
└─src
│   ├─main
│   │  ├─docker
│   │  │      Dockerfile
│   │  ├─java
│   │  │  └─com.company.script
│   │  └─resources
│   │                  
│   └─test
│      └─java
│         └─com.company.script
│  .gitignore
│  docker.sh
│  pom.xml
│  yaml.sh
```