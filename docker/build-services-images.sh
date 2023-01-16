#!/bin/sh

DEST_HOST_IP=172.31.38.80
DEST_HOST_USER=abis
DESTINATION_HOST_DIR=/MISC/release/microservices

cd ..
cd configuration-service || exit
mvn clean install -Pprod -DskipTests -U
mvn dockerfile:build

cd ..
cd service-discovery || exit
mvn clean install -Pprod -DskipTests -U
mvn dockerfile:build

cd ..
cd gateway || exit
mvn clean install -Pprod -DskipTests -U
mvn dockerfile:build

cd ..
cd modernisc-service-gateway || exit
sh ./mvn_build.sh
cd service-gateway-app || exit
mvn dockerfile:build

echo -------------------------- [ saving image config-service ] --------------------------
rm -f /home/$USER/Desktop/microservices/config-service.tar
docker save -o /home/$USER/Desktop/microservices/config-service.tar 127.0.0.1:8083/microservice-docker-configuration-service:0.0.1-SNAPSHOT

echo -------------------------- [ saving image discovery-service ] --------------------------
rm -f /home/$USER/Desktop/microservices/discovery-service.tar
docker save -o /home/$USER/Desktop/microservices/discovery-service.tar 127.0.0.1:8083/microservice-docker-service-discovery:0.0.1-SNAPSHOT

echo -------------------------- [ saving image gateway-service ] --------------------------
rm -f /home/$USER/Desktop/microservices/gateway-service.tar
docker save -o /home/$USER/Desktop/microservices/gateway-service.tar 127.0.0.1:8083/microservice-docker-gateway-service:0.0.1-SNAPSHOT

echo -------------------------- [ saving image modernisc-service-gateway-service ] --------------------------
rm -f /home/$USER/Desktop/microservices/misc-service-gateway-service.tar
docker save -o /home/$USER/Desktop/microservices/misc-service-gateway-service.tar 127.0.0.1:8083/microservice-docker-modernisc-service-gateway:0.0.1-SNAPSHOT

echo -------------------------- [ saving image keycloak ] --------------------------
rm -f /home/$USER/Desktop/microservices/keycloak.tar
docker save -o /home/$USER/Desktop/microservices/keycloak.tar 127.0.0.1:8082/jboss/keycloak:latest

scp /home/$USER/Desktop/microservices/* $DEST_HOST_USER@$DEST_HOST_IP:$DESTINATION_HOST_DIR
