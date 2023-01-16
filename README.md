# Microservice Sample

Sample microservice project

---> symmetric key
default password = testPass


-----> asymmetric key
$> keytool -genkeypair -alias config-server-key -keyalg RSA -keysize 4096 -sigalg SHA512withRSA -dname 'CN=Config Server,OU=Spring Cloud,O=Test' -keypass my-k34-s3cr3t -keystore config-server.jks -storepass my-k34-s3cr3t

After that, we're adding the created keystore to our server's bootstrap.properties and re-run it:

encrypt.keyStore.location=classpath:/config-server.jks
encrypt.keyStore.password=my-k34-s3cr3t
encrypt.keyStore.alias=config-server-key
encrypt.keyStore.secret=my-k34-s3cr3t

------> dockerize
https://www.baeldung.com/dockerizing-spring-boot-application
https://www.baeldung.com/docker-compose
***

###Release Notes:

####Ingredients:
```
- The whole "configs" directory
- .env file
- keystore and truststore in "modernisc-service-gateway/service-gateway-app/src/main/resources/keys" directory
- Images file in tar format that is outcome of "build-service-images.sh" 
- Suitable jdbc for destination database located in keycloak/jdbc with correct directory that is used in keycloak "docker-compose.yml" file
- JSON data file for importing in keycloak located in keycloak/patch-files
- Keycloak "docker-compose.yml" file
- "docker-compose.yml" file that located in docker directory
- these sh files: 
> reset.sh
> stop-and-load-images.sh
```

- Install docker if it's not installed by running "run.sh" in Docker-installations directory after shipping Docker-installations folder to host machine
- Inorder to make base image run "dockerfile-build.sh" which downloads image from docker site and needs proxy
- After testing each service for building images run "build-services-images.sh"
- For deploying services, images should load in host machine and for that run "stop-and-load-images.sh"
- Check database details in Keycloak "docker-compose.yml" file 
- Copy whole "configs" directory to destination host
- For starting services after setting .env file and setting properties and ports and checking "docker-compose.yml" file run "reset.sh"
- After that microservices start go to Machine-ip:8080 for importing keycloak data and copy client credential and set it in "modernisc-service-gateway.yml" file  

### Cautions
- command for checking logs of docker container
> $ docker logs CONTAINER_ID