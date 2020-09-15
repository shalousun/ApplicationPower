项目说明

# 环境要求

构建：gradle 4.10+

系统：linux /windows

容器：docker

jdk版本：jdk 1.8

# 构建镜像

第一种方式：使用gradle命令
```
# gradle build buildDocker
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

docker.sh目前针对gradle项目的构建处理已经相当其齐全，目前ApplicationPower中预定速成的镜像规则是：
$GROUP/$PROJECT_NAME:$APP_VERSION，其中$GROUP代码group当前使用的基础包名；$PROJECT_NAME是
ApplicationPower生成项目时指定的项目名；$APP_VERSION目前使用的版本都是项目初期的0.0.1-SNAPSHOT。
目前docker.sh已经能自动根据gradle的build.gradle和settings.gradle中的项目配置来修改正构建，因此推荐使用docker.sh替代直接的gradle命名。

docker.sh目前已经默认内置了将镜像推送到自建仓库的功能，需要则将脚本中的命令去注释打开即可。

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
kubectl create -f ${deployment_cfg}
```
# 项目结构

项目标准结构(此图已省略部分)
```
├─docs
│      DOCKER.md
├─k8s
│      deployment.yaml
├─gradle
│   └─wrapper
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
│  build.gradle
│  docker.sh
│  settings.gradle
│  yaml.sh
```