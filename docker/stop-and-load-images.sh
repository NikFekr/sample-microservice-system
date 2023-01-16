#!/bin/sh

#---> stop and remove containers
docker stop config-service || true && docker rm config-service || true
docker stop eureka-service || true && docker rm eureka-service || true
docker stop gateway-service || true && docker rm gateway-service || true
docker stop keycloak-service || true && docker rm keycloak-service || true
docker stop misc-service-gateway-service || true && docker rm misc-service-gateway-service || true

#---> remove images
docker rmi 127.0.0.1:8083/microservice-docker-modernisc-service-gateway:0.0.1-SNAPSHOT || true
docker rmi 127.0.0.1:8083/microservice-docker-gateway-service:0.0.1-SNAPSHOT || true
docker rmi 127.0.0.1:8083/microservice-docker-service-discovery:0.0.1-SNAPSHOT || true
docker rmi 127.0.0.1:8083/microservice-docker-configuration-service:0.0.1-SNAPSHOT || true
docker rmi 127.0.0.1:8082/jboss/keycloak:latest|| true

#---> folowing images should be in the same directory of this script

#image_dir=.

#docker load -i $image_dir/config-service.tar
#docker load -i $image_dir/discovery-service.tar
#docker load -i $image_dir/gateway-service.tar
#docker load -i $image_dir/misc-service-gateway-service.tar
#docker load -i $image_dir/keycloak.tar

