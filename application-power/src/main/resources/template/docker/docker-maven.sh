#!/bin/bash
# @author shalousun
# shell script to build docker images while use jenkins.

source /etc/profile

# include yaml_parse function
. "$(dirname "$0")"/yaml.sh

CUR_PATH=$(cd `dirname $0`;pwd)

DOCKER_REGISTRY=192.168.248.128

HARBOR_PROJECT=library

HARBOR_PASSWORD=Harbor12345

HARBOR_USER=admin

TIME_VERSION=$(date +%Y%m%d%H%M%S)

RESOURCES_DIR=$CUR_PATH/src/main/resources

APPLICATION_FILE=$RESOURCES_DIR/application.yml

POM=$CUR_PATH/pom.xml
# =======================use env ======================================
INIT_ENV=""
if [ "$1" = "--env" ]; then
    INIT_ENV="$2"
    if [ -n "$INIT_ENV" ]; then
       echo "INFO: $RESOURCES_DIR/$INIT_ENV/* $RESOURCES_DIR"
       cp $RESOURCES_DIR/$INIT_ENV/* $RESOURCES_DIR
    fi
fi

if [ -z "$INIT_ENV" ]; then
    echo "INFO: The environment is default"
else
    echo "INFO: The environment is $INIT_ENV"
fi
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

# ======================FIND GROUP AND APP VERSION=======================
APP_VERSION="0.0.1-SNAPSHOT"
GROUP="${basePackage}"
ARTIFACT_ID="${applicationName}"
# ======================FIND PROJECT NAME================================
PROJECT_NAME="${applicationNameLowerCase}"
# ======================Dynamically process project config===============
if [ "$(xmllint 2>&1 | grep xpath | wc -l)" = "1" ]
then
    echo "INFO: xmllint has installed,we will use it to process pom.xml faster."
    APP_VERSION_TEMP=$(sed < $POM '2 s/xmlns=".*"//g' | xmllint --xpath '/project/version/text()' - 2>/dev/null)
    if [ -n "$APP_VERSION_TEMP" ]; then
        APP_VERSION=$APP_VERSION_TEMP
    fi

    GROUP_TEMP=$(sed < $POM '2 s/xmlns=".*"//g' | xmllint --xpath '/project/groupId/text()' - 2>/dev/null)
    if [ -n "$GROUP_TEMP" ]; then
        GROUP=$GROUP_TEMP
    fi

    ARTIFACT_ID_TEMP=$(sed < $POM '2 s/xmlns=".*"//g' | xmllint --xpath '/project/artifactId/text()' - 2>/dev/null)
    if [ -n "$ARTIFACT_ID_TEMP" ]; then
        ARTIFACT_ID=$ARTIFACT_ID_TEMP
    fi

    FINAL_NAME=$(sed < $POM '2 s/xmlns=".*"//g' | xmllint --xpath '/project/build/finalName/text()' - 2>/dev/null)
    if [ -n "$FINAL_NAME" ]; then
        PROJECT_NAME=$FINAL_NAME
    else
        PROJECT_NAME=$ARTIFACT_ID-$APP_VERSION
    fi
else
    echo "INFO: Use maven process pom.xml,The process is slower, please wait patiently !"
    APP_VERSION=$(mvn help:evaluate -Dexpression=project.version | grep -e '^[^\[]')
    GROUP=$(mvn help:evaluate -Dexpression=project.groupId | grep -e '^[^\[]')
    ARTIFACT_ID=$(mvn help:evaluate -Dexpression=project.artifactId | grep -e '^[^\[]')
    FINAL_NAME=$(mvn help:evaluate -Dexpression=project.build.finalName | grep -e '^[^\[]')
    if [ -n "$FINAL_NAME" ]; then
        PROJECT_NAME=$FINAL_NAME
    else
        PROJECT_NAME=$ARTIFACT_ID-$APP_VERSION
    fi
fi
# ====================define IMAGE=======================================
# auto set images name
#========================================================================
#MYIMAGE=$GROUP/$PROJECT_NAME:$APP_VERSION
MYIMAGE=$PROJECT_NAME:$APP_VERSION
TAG_IMAGE=$PROJECT_NAME:v\${TIME_VERSION}
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
# ========================modify Dockerfile===============================
#  If the jar name is set error in Dockerfile,This script could auto fix
#=========================================================================
if ! grep "$PROJECT_NAME.jar" $CUR_PATH/src/main/docker/Dockerfile
then
    sed -i "s/^ADD \(.*\) app.jar/ADD $PROJECT_NAME.jar app.jar/g" $CUR_PATH/src/main/docker/Dockerfile
    JAR_NAME=$(sed -n "s/^ADD \(.*\) app.jar/\1/p" $CUR_PATH/src/main/docker/Dockerfile)
    echo "INFO: The build jar name is $JAR_NAME"
fi
# =========================build docker image=============================
cd $CUR_PATH
echo "INFO: use maven build docker image"
mvn clean package docker:build -DskipTests

# running container
# docker run -dp $SERVER_PORT:$SERVER_PORT -t ${MYIMAGE}
# echo "INFO: export port is $SERVER_PORT"


# ==========================push image to registry========================
# uncomment if you need push
docker login ${DOCKER_REGISTRY} -u $HARBOR_USER -p $HARBOR_PASSWORD
echo "INFO：Starting push image of \${TAG_IMAGE} to docker registry ${DOCKER_REGISTRY}"
docker tag ${MYIMAGE}  ${DOCKER_REGISTRY}/$HARBOR_PROJECT/\${TAG_IMAGE}
docker push ${DOCKER_REGISTRY}/$HARBOR_PROJECT/\${TAG_IMAGE}