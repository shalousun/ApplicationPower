项目说明

# 环境要求

构建：gradle 4+

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
# chmod 0755 docker.sh
# ./docker.sh
```
**注意：**  以上命令都是在需要构建的项目根目录运行

# docker.sh脚本说明

docker.sh目前针对gradle项目的构建处理已经相当其齐全，目前ApplicationPower中预定速成的镜像规则是：
$GROUP/$PROJECT_NAME:$APP_VERSION，其中$GROUP代码group当前使用的基础包名；$PROJECT_NAME是
ApplicationPower生成项目时指定的项目名；$APP_VERSION目前使用的版本都是项目初期的0.0.1-SNAPSHOT。
目前docker.sh已经能自动根据gradle的build.gradle和settings.gradle中的项目配置来修改正构建，因此推荐使用docker.sh替代直接的gradle命名。

docker.sh目前已经默认内置了将镜像推送到自建仓库的功能，需要则将脚本中的命令去注释打开即可。

# 项目结构

项目标准结构(此图已省略部分)
```
├─docs
│      DOCKER.md
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