echo off
::----------------------------------------------------------------------
:: SpringBoot project start script.
:: @author Shalousun
:: see https://github.com/Shalousun/ApplicationPower
::----------------------------------------------------------------------
set num=0
setlocal enabledelayedexpansion

set APP_NAME=${appName}.jar

set CURRENT_PATH=%cd%

echo INFO: Working space is %CURRENT_PATH%

set SPRING_CONFIG_LOCATION=../config/application.yml

set LOG_IMPL=log4j2.xml

set log_dir=../logs

set lib_dir=../lib

set conf_dir=../config

::----------------------------------------------------------------------
:: Dynamically loading the properties configuration file.
::----------------------------------------------------------------------
for /R "%conf_dir%" %%s in (*.properties) do (
  set /a num+=1
  IF !num! equ 1 (
    set config_location=!config_location!%%s
  ) ELSE (
    set config_location=!config_location!,%%s
  )
)
:: replace \ to /
set final_config_location=!config_location:\=/!
echo INFO: loaded config files %final_config_location%
set CONFIG= -Dlogging.path=%log_dir% -Dlogging.config=%conf_dir%/%LOG_IMPL% -Dspring.config.location=%SPRING_CONFIG_LOCATION%,"%final_config_location%"
::----------------------------------------------------------------------
:: set jvm  -Xms -Xmx -Xss.
:: Usage:set JAVA_OPTS=-server -Xms512M -Xmx512M -Xss256K -Djava.awt.headless=true -Dfile.encoding=utf-8 -XX:PermSize=64M -XX:MaxPermSize=128m
::----------------------------------------------------------------------
set JVM_OPTS=-server -Xms512m -Xmx512m

set DEBUG_OPTS=
if ""%1"" == ""debug"" (
   set DEBUG_OPTS= -Xloggc:%log_dir%/gc.log -verbose:gc -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=%log_dir%
   goto debug
)

set JMX_OPTS=
if ""%1"" == ""jmx"" (
   set JMX_OPTS= -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9888 -Dcom.sun.management.jmxremote.ssl=FALSE -Dcom.sun.management.jmxremote.authenticate=FALSE
   goto jmx
)

echo.
echo INFO: Starting the %APP_NAME%
echo.
set stdout=%log_dir%/app-%date:~0,4%%date:~5,2%%date:~8,2%.log
echo INFO:log out %stdout%
java %JVM_OPTS% %DEBUG_OPTS% %JMX_OPTS% %CONFIG% -jar %lib_dir%/%APP_NAME%
goto end

:debug
echo "debug"
java %JVM_OPTS% %DEBUG_OPTS% %CONFIG% -jar %lib_dir%/%APP_NAME%
goto end

:jmx
java %JVM_OPTS% %JMX_OPTS% %CONFIG% -jar %lib_dir%/%APP_NAME%
goto end

:end
pause