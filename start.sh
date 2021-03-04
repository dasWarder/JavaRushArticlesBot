#!/bin/bash

# Pull new changes
git pull


# Prepare jar
mvn clean
mvn package

# Ensure, that doc-compose was stopped
docker-compose stop

#Add env_vars
export BOT_NAME=$1
export BOT_TOKEN=$2

#start new deployment
docker -compose up --build -d