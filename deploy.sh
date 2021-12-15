#!/bin/bash

cd /opt/psychologically-test-backend
git pull origin develop
./mvnw package -Pprod -DskipTests verify jib:dockerBuild
docker-compose -f src/main/docker/app.yml down && docker-compose -f src/main/docker/app.yml up -d
