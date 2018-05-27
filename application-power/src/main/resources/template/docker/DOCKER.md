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
第二种方式：使用docker.sh
```
# chmod 0755 docker.sh
# ./docker.sh
```
**注意：**  以上命令都是在需要构建的项目根目录运行

# docker.sh脚本说明

docker.sh目前针对maven环境下的构建还比较粗略，目前ApplicationPower中预定速成的镜像规则是：
$GROUP/$PROJECT_NAME:$APP_VERSION，其中$GROUP代码group当前使用的基础包名；$PROJECT_NAME是
ApplicationPower生成项目时指定的项目名；$APP_VERSION目前使用的版本都是项目初期的0.0.1-SNAPSHOT。
如果想更改镜像规则请相应修改pom.xml，Dockerfile,docker.sh中的响应docker配置，否则可能导致构建的镜像
和启动的镜像名不统一。

docker.sh目前已经默认内置了将镜像推送到自建仓库的功能，需要则将脚本中的命令去注释打开即可。

当然随着ApplicationPower不断更新，后期会对脚本做深度优化到尽可能规范和减少配置修改，敬请期待！！！

# 项目结构

项目基础结构
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