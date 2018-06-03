项目说明

# 环境要求

构建：maven 3+

系统：linux /windows

容器：docker

jdk版本：jdk 1.8

# 构建镜像

第一种方式：使用maven命令
```
# mvn clean package docker:build -DskipTests
```
第二种方式【推荐】：使用docker.sh
```
# chmod 0755 docker.sh
# ./docker.sh
```
**注意：**  以上命令都是在需要构建的项目根目录运行

# docker.sh脚本说明

docker.sh目前针对maven环境下的构建处理已经比较完善，能够自动根据pom.xml中的配置自动构建镜像名称，
即便你修改pom.xml中的项目配置，也可以不理Dockerfile中的jar名称，docker自动在构建前修正Dockerfile，从目前来看docker.sh
的容错度比较高的，并且该脚本无需做修改就可移植到其他maven项目。

目前ApplicationPower中约定速成的镜像规则是：$GROUP/$PROJECT_NAME:$APP_VERSION，其中$GROUP代码group当前使用的基础包名；$PROJECT_NAME是
ApplicationPower生成项目时指定的项目名；$APP_VERSION目前使用的版本都是项目初期的0.0.1-SNAPSHOT。

docker.sh目前已经默认内置了将镜像推送到自建仓库的功能，需要则将脚本中的命令去注释打开即可。

# 项目结构

项目标准结构(此图已省略部分)
```
├─docs
│      DOCKER.md
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