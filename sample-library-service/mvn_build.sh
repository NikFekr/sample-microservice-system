#!/bin/sh

BASE_DIR=.

BUILD_OPTS="-Djavax.net.ssl.keyStore=$BASE_DIR/library-app/src/main/resources/keys/library.p12"
BUILD_OPTS="$BUILD_OPTS -Djavax.net.ssl.keyStorePassword=changeit"
BUILD_OPTS="$BUILD_OPTS -Djavax.net.ssl.trustStore=$BASE_DIR/library-app/src/main/resources/keys/library-truststore.p12"
BUILD_OPTS="$BUILD_OPTS -Djavax.net.ssl.trustStorePassword=changeit"

mvn clean install $BUILD_OPTS -Pprod -U
