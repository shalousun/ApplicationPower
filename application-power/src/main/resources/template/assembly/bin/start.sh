#!/bin/bash
# Startup script for spring boot project
# @author shalousun
# see https://github.com/Shalousun/ApplicationPower

# include yaml_parse function
. "$(dirname "$0")"/yaml.sh

# the name of the project
SERVER_NAME='${appName}'
JAR_NAME='${jarName}'
cd $(dirname $0)
BIN_DIR=$(pwd)
chmod 0755 *.sh
cd ..
DEPLOY_DIR=$(pwd)
CONF_DIR=$DEPLOY_DIR/config
# log file
LOG_IMPL_FILE=${logConfig}
APPLICATION_FILE=application.yml
# ====================================use env ===========================================================
INIT_ENV=""
if [ "$1" = "--env" ]; then
    INIT_ENV="$2"
    if [ -n "$INIT_ENV" ]; then
       cp $CONF_DIR/$INIT_ENV/* $CONF_DIR
    fi
fi
# ====================================FIND SERVER PORT===================================================
SERVER_PORT=""
if [ -f "config/application.yml" ]
then
    # remove ^M in file
    sed -i "s/$(echo -e '\015')/\n/g" config/application.yml
    # read yml file
    eval $(YamlParse__parse "config/application.yml" "config_")
    SERVER_PORT=$config_server_port
else
    SERVER_PORT=$(sed '/server.port/!d;s/.*=//' config/application.properties | tr -d '\r')
fi
# ===================================OS specific support================================================
unameOut="$(uname -s)"
case "$unameOut" in
    Linux*)     machine=Linux;;
    Darwin*)    machine=Mac;;
    CYGWIN*)    machine=Cygwin;;
    MINGW*)     machine=MinGw;;
    *)          machine="UNKNOWN:$unameOut"
esac
echo "INFO: $machine platform"
# ===================================FIND SERVER PORT END================================================
PIDS=$(ps -ef | grep java | grep "$CONF_DIR" |awk '{print $2}')
if [ "$1" = "status" ]; then
    if [ -n "$PIDS" ]; then
        echo "The $SERVER_NAME is running...!"
        echo "PID: $PIDS"
        echo "Used port: $SERVER_PORT"
        exit 0
    else
        echo "The $SERVER_NAME is stopped"
        exit 0
    fi
fi

if [ -n "$PIDS" ]; then
    echo "ERROR: The $SERVER_NAME already started!"
    echo "PID: $PIDS"
    echo "Used port: $SERVER_PORT"
    exit 1
fi
# ============================port occupancy=============================================================
if [ -n "$SERVER_PORT" ]; then
    SERVER_PORT_COUNT=""
    if [ "$machine" == "Linux" ]; then
        SERVER_PORT_COUNT=$(netstat -tln | grep "\b$SERVER_PORT\b" | wc -l)
    elif [ "$machine" == "Mac" ]; then
        SERVER_PORT_COUNT=$(lsof -t -i :$SERVER_PORT)
    else
        SERVER_PORT_COUNT=$(netstat -tln | grep "\b$SERVER_PORT\b" | wc -l)
    fi

    if [ "$SERVER_PORT_COUNT" == "" ]; then
    	echo "starting"
    elif [ $SERVER_PORT_COUNT -gt 0 ]; then
        echo "ERROR: The $SERVER_NAME port $SERVER_PORT already used!"
        exit 1
    fi
fi
# ==============================Dynamically loading configuration file====================================
function getFilesUderDir(){
    for file in $1/*
    do
    if test -f $file
    then
        filename=$(basename "$file")
        extension="${extension}"
        if [ "xml" != "$extension" ]
        then
  	        arr=${arr_file}
        fi
    else
        echo "INFO: getFilesUderDir"
        # getFilesUderDir $file
    fi
    done
}
getFilesUderDir $CONF_DIR
FOUND_FILES=$(IFS=, ; echo "${arr}")
echo "INFO: loaded config files $FOUND_FILES"
# =================================init logging dir and config===========================================
LOGS_DIR=$DEPLOY_DIR/logs
if [ ! -d "$LOGS_DIR" ]; then
    mkdir $LOGS_DIR
fi
STDOUT_FILE=$LOGS_DIR/catalina.out

LOGGING_CONFIG=""
if [ -f "$CONF_DIR/$LOG_IMPL_FILE" ]
then
    LOGGING_CONFIG="-Dlogging.config=$CONF_DIR/$LOG_IMPL_FILE"
fi

CONFIG_FILES=" -Dlogging.file.path=$LOGS_DIR $LOGGING_CONFIG -Dspring.config.location=$FOUND_FILES "

# =================================set jvm params=======================================================
JAVA_DEFAULT_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "
JAVA_DEBUG_OPTS=""
if [ "$1" = "debug" ]; then
    JAVA_DEBUG_OPTS=" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n "
fi

JAVA_JMX_OPTS=""
if [ "$1" = "jmx" ]; then
    JAVA_JMX_OPTS=" -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false "
fi

JAVA_MEM_OPTS=""
BITS=$(java -version 2>&1 | grep -i 64-bit)
if [ -n "$BITS" ]; then
    JAVA_MEM_OPTS=" -server -Xmx1024m -Xms1024m -Xmn256m -XX:PermSize=128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
else
    JAVA_MEM_OPTS=" -server -Xms512m -Xmx512m -XX:PermSize=128m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
fi

# =====================get set custom jvm params ==============================================================

if [ -r "$BIN_DIR/setenv.sh" ]; then
  . "$BIN_DIR/setenv.sh"
fi
JAVA_OPTS_TEMP=
if [ -n "$JAVA_OPTS" ]
then
    # use custom jvm param in setenv.sh
    echo "INFO: use custom jvm params in setenv.sh"
    JAVA_OPTS_TEMP=$JAVA_OPTS
else
    # use default jvm param in this script
    echo "INFO: use default jvm params in start.sh"
    JAVA_OPTS_TEMP="$JAVA_DEFAULT_OPTS $JAVA_MEM_OPTS"
fi
echo -e "Starting the $SERVER_NAME ..."
nohup java $JAVA_OPTS_TEMP $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS -jar $DEPLOY_DIR/$JAR_NAME >> $STDOUT_FILE 2>&1 &

CHECK_COUNT=0
COUNT=0
while [ "$COUNT" -lt 1 ]; do
    echo -e ".\\c"
    sleep 1
    let CHECK_COUNT+=1;
    if [ "$CHECK_COUNT" -gt 20 ];then
        echo -e "\nERROR: The $SERVER_NAME start failed, Please open $STDOUT_FILE to view the error log"
        exit 1
    fi
    if [ -n "$SERVER_PORT" ]; then
        COUNT=$(netstat -an | grep "$SERVER_PORT" | wc -l)
    else
    	COUNT=$(ps -ef | grep java | grep "$DEPLOY_DIR" | awk '{print $2}' | wc -l)
    fi
    if [ "$COUNT" -gt 0 ]; then
        break
    fi
done

# ====================print finish info=================================================================================
echo "OK!"
PIDS=$(ps -ef | grep java | grep "$DEPLOY_DIR" | awk '{print $2}')
echo "Command line argument: $JAVA_OPTS_TEMP"
echo "PID: $PIDS"
echo "PORT: $SERVER_PORT"
echo "STDOUT: $STDOUT_FILE"
