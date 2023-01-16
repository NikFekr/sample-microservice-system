#!/bin/bash

read -rsp "Please enter password:" encrypt

lineNum=$(awk '/JAVA_OPTS=/{ print NR; exit }' .env)
echo $'\n JAVA_OPTS line number: ' $lineNum

if [ $encrypt != null ]; then
  sed -i $lineNum's/.*/JAVA_OPTS= -Djasypt.encryptor.password='$encrypt'/' .env
fi

echo '---> JAVA_OPTS is this now: '
sed -n $lineNum'p' .env

#---> stop and remove containers
docker stop config-service || true && docker rm config-service || true
docker stop eureka-service || true && docker rm eureka-service || true
docker stop gateway-service || true && docker rm gateway-service || true
docker stop keycloak-service || true && docker rm keycloak-service || true
docker stop misc-service-gateway-service || true && docker rm misc-service-gateway-service || true

docker network create spring-cloud-network || true
docker-compose run -d --name misc-service-gateway-service misc-service-gateway-server

if [ $encrypt != null ]; then
  sed -i $lineNum's/.*/JAVA_OPTS=/' .env
fi

echo '---> JAVA_OPTS is this now: '
sed -n $lineNum'p' .env
