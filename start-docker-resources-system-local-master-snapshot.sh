#!/bin/bash

set -e 

RUN_PATH="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $RUN_PATH

# Prepare folders
FOLDER_PLUGINS_JARS=$(pwd)/_plugins-jars
mkdir -p $FOLDER_PLUGINS_JARS

# Prepare file
FILE_IMPORT=$(pwd)/_exportFile.json

# Download plugins
USER_ID=$(id -u)
docker run -ti \
  --rm \
  --env PLUGINS_JARS=/plugins \
  --user $USER_ID \
  --volume $FOLDER_PLUGINS_JARS:/plugins \
  foilen-infra-system-app-test-docker:master-SNAPSHOT \
  download-latest-plugins \
  /plugins application dns domain machine unixuser webcertificate website

# Create release
./create-local-release-no-tests.sh
cp build/libs/foilen-infra-resource-urlredirection-master-SNAPSHOT.jar $FOLDER_PLUGINS_JARS

# Start resources
docker run -ti \
  --rm \
  --env HOSTFS=/hostfs/ \
  --env PLUGINS_JARS=/plugins \
  --volume $FILE_IMPORT:/exportFile.json \
  --volume $FOLDER_PLUGINS_JARS:/plugins \
  --volume /etc:/hostfs/etc \
  --volume /home:/hostfs/home \
  --volume /usr/bin/docker:/usr/bin/docker \
  --volume /usr/lib/x86_64-linux-gnu/libltdl.so.7.3.1:/usr/lib/x86_64-linux-gnu/libltdl.so.7 \
  --volume /var/run/docker.sock:/var/run/docker.sock \
  foilen-infra-system-app-test-docker:master-SNAPSHOT \
  start-resources /exportFile.json
