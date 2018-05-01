echo off
::----------------------------------------------------------------------
:: SpringBoot project start script.
:: @author Shalousun
:: see https://github.com/Shalousun/ApplicationPower
::----------------------------------------------------------------------

set APP_NAME=${appName}.jar

::----------------------------------------------------------------------
:: set jvm  -Xms、-Xmx、-Xss.
:: Usage:set JAVA_OPTS=-server -Xms512M -Xmx512M -Xss256K -Djava.awt.headless=true -Dfile.encoding=utf-8 -XX:PermSize=64M -XX:MaxPermSize=128m
::----------------------------------------------------------------------
set JVM_OPTS=-server -Xms512m -Xmx512m

set CONFIG= -Dlogging.path=../logs -Dlogging.config=../config/log4j2.xml -Dspring.config.location=../config/application.yml

set DEBUG_OPTS=
if ""%1"" == ""debug"" (
   set DEBUG_OPTS= -Xloggc:../logs/gc.log -verbose:gc -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../logs
   goto debug
)

set JMX_OPTS=
if ""%1"" == ""jmx"" (
   set JMX_OPTS= -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9888 -Dcom.sun.management.jmxremote.ssl=FALSE -Dcom.sun.management.jmxremote.authenticate=FALSE
   goto jmx
)

echo "Starting the %APP_NAME%"
java %JVM_OPTS% %DEBUG_OPTS% %JMX_OPTS% %CONFIG% -jar ../lib/%APP_NAME%
goto end

:debug
echo "debug"
java %JVM_OPTS% %DEBUG_OPTS% %CONFIG% -jar ../lib/%APP_NAME%
goto end

:jmx
java %JVM_OPTS% %JMX_OPTS% %CONFIG% -jar ../lib/%APP_NAME%
goto end

:end
pause