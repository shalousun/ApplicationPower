ApplicationPower在生成SpringBoot项目主体框架代码的时候，如果选择maven作为项目构建会自动集成assembly打包功能，
并提供完善的启动脚本，包括window和linux环境，默认的结构如下：
```
├─bin
│      dump.sh
│      server.sh
│      setenv.sh
│      start.bat
│      start.sh
│      stop.sh
│      yaml.sh   
├─config
│      application.yml
│      log4j2.xml
├─docs
│      DEPLOY.md
├─lib
│      springboot-xxx.jar  
└─logs
```
start.sh脚本中提供了目录隔离式的多环境启动功能，例如下面的结构
```
├─bin
│      dump.sh
│      server.sh
│      setenv.sh
│      start.bat
│      start.sh
│      stop.sh
│      yaml.sh   
├─config
│   dev
│      application.yml
│      log4j2.xml
│   pro
│      application.yml
│      log4j2.xml    
├─docs
│      DEPLOY.md
├─lib
│      springboot-xxx.jar  
└─logs
```
上面这种结构可以使用在启动时可以指定启动环境
```
sh start.sh --env dev
```
# 打包变更
从上面的打包后的解压结构来看，这种结构并不是官方天然支持的配置文件外置结构，官方支持的标准结构如下
```
├─bin
│      dump.sh
│      server.sh
│      setenv.sh
│      start.bat
│      start.sh
│      stop.sh
│      yaml.sh   
├─config
│      application.yml
│      log4j2.xml   
├─docs
│      DEPLOY.md
│ springboot-xxx.jar  
└─logs
```
上面的这种结构Spring Boot打包的jar放在根路径，能够自动加载config中的文件，不像jar在lib的情况，
需要在启动的时候指定配置文件路径。

如果项目启动期间想要jar放到根据目录，则是需要修改assembly.xml中的打包配置
```
<fileSet>
    <directory>target</directory>
    <outputDirectory>/</outputDirectory>
    <includes>
        <include>*.jar</include>
    </includes>
</fileSet>
```
原始的脚本启动命令
```aidl
nohup java $JAVA_OPTS_TEMP $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS $CONFIG_FILES -jar $DEPLOY_DIR/lib/$JAR_NAME >> $STDOUT_FILE 2>&1 &
```
修改为如下
```aidl
nohup java $JAVA_OPTS_TEMP $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS -jar $DEPLOY_DIR/$JAR_NAME >> $STDOUT_FILE 2>&1 &
```
