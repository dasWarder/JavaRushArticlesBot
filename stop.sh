#!/bin/bash

#Check dock-compose stopped
docker-compose stop

#Ensure, that the old app couldn't be deployed again
mvn clean