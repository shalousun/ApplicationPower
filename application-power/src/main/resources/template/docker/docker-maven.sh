#!/bin/bash
# @author shalousun
# shell script to build docker images while use jenkins.

source /etc/profile

# include yaml_parse function
. "$(dirname "$0")"/yaml.sh

CUR_PATH=$(cd `dirname $0`;pwd)

DOCKER_REGISTRY=192.168.248.128:8086

RESOURCES_DIR=$CUR_PATH/src/main/resources

APPLICATION_FILE=$RESOURCES_DIR/application.yml

# ======================FIND SERVER PORT ==============================
SERVER_PORT="8080"
if [ -f "$APPLICATION_FILE" ]
then
    # remove ^M in file
    sed -i "s/$(echo -e '\015')/\n/g" $APPLICATION_FILE
    # read yml file
    eval $(YamlParse__parse "$APPLICATION_FILE" "config_")
    SERVER_PORT=$config_server_port
else
    if [ -f "$RESOURCES_DIR/application.properties" ]
    then
        SERVER_PORT=$(sed '/server.port/!d;s/.*=//' $RESOURCES_DIR/application.properties | tr -d '\r')
    fi
fi

echo "INFO: Server PORT $SERVER_PORT"

# ======================FIND GROUP AND APP VERSION======================
APP_VERSION="0.0.1-SNAPSHOT"
GROUP="${basePackage}"
# ======================FIND PROJECT NAME===============================
PROJECT_NAME="${applicationName}"
# ====================define IMAGE=======================================
# auto set images name
#========================================================================
#MYIMAGE=$GROUP/$PROJECT_NAME:$APP_VERSION
MYIMAGE=$GROUP/$PROJECT_NAME:$APP_VERSION
echo "INFO: The image name is $MYIMAGE"

# =========================stop container================================

CONTAINER_ID=$(docker ps|grep ${MYIMAGE}| awk '{print $1}')
if [ $CONTAINER_ID ]
then
    docker kill $CONTAINER_ID
    echo "INFO：The container about of image ${MYIMAGE} Stopped."
else
    echo "ERROR: The container about of image ${MYIMAGE} Could't be Stopped."
fi

# =========================remove container===============================
if [ $CONTAINER_ID ]
then
    docker rm $CONTAINER_ID
    echo "Remove container about of image ${MYIMAGE} success"
else
    echo "ERROR: Can't find container about of image ${MYIMAGE}"
fi

# =========================remove old images==============================
IMAGEID=$(docker images | grep ${MYIMAGE} | awk '{print $3}')
if [ $IMAGEID ]
then
    docker images | grep ${MYIMAGE} | awk '{print $3}' | xargs docker rmi -f
else
    echo "ERROR: Can't find image ${MYIMAGE}"
fi
# =========================build docker image=============================
echo "INFO: use maven build docker image"
mvn clean package docker:build -DskipTests

# running container
docker run -dp $SERVER_PORT:$SERVER_PORT -t ${MYIMAGE}
echo "INFO: export port is $SERVER_PORT"


# ==========================push image to registry========================
# uncomment if you need push
# docker login ${DOCKER_REGISTRY} -u admin -p admin123
echo "INFO：Starting push image of ${MYIMAGE} to docker registry ${DOCKER_REGISTRY}"
# docker tag ${MYIMAGE}  ${DOCKER_REGISTRY}/${MYIMAGE}
# docker push ${DOCKER_REGISTRY}/${MYIMAGE}