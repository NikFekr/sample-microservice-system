#!/bin/sh
fuser -k 9090/tcp


JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5000"
JAVA_OPTS="$JAVA_OPTS -Dserver.port=9090"
JAVA_OPTS="$JAVA_OPTS -Dpath=$(pwd)"
JAVA_OPTS="$JAVA_OPTS -Dlogging.config=./log4j2-spring.xml"


MACHINE_IP=172.31.10.23
KEYCLOAK_URL=http://$MACHINE_IP

JAVA_OPTS="$JAVA_OPTS -Dcom.modernisc.jasypt.algorithm=PBEWITHSHA256AND128BITAES-CBC-BC"
JAVA_OPTS="$JAVA_OPTS -Dcom.modernisc.jasypt.pass=test_password"
JAVA_OPTS="$JAVA_OPTS -DKEYCLOAK_URL=$KEYCLOAK_URL"

APP_OPTS=""
#APP_OPTS="$APP_OPTS --get-password=true"

BASE_DIR=./keys

#library (this app) SSL keys and truststore
JAVA_OPTS="$JAVA_OPTS -Djavax.net.ssl.keyStore=$BASE_DIR/library.p12"
JAVA_OPTS="$JAVA_OPTS -Djavax.net.ssl.keyStorePassword=ENC(oDFNWVra6ZTPYUNsIPEgwGYgg8qUveTZdeNfoUVvJz0=)"
JAVA_OPTS="$JAVA_OPTS -Djavax.net.ssl.trustStore=$BASE_DIR/library-truststore.p12"
JAVA_OPTS="$JAVA_OPTS -Djavax.net.ssl.trustStorePassword=ENC(oDFNWVra6ZTPYUNsIPEgwGYgg8qUveTZdeNfoUVvJz0=)"

java $JAVA_OPTS -jar *.jar $APP_OPTS 
