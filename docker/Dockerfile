FROM 127.0.0.1:8082/oraclelinux:8-slim as packager

ENV LANG en_US.UTF-8

ENV JAVA_PKG=./jdk11/jdk-11.0.11 \
	JAVA_HOME=/opt/jdk-11 \
    JAVA_VERSION=11.0.11

COPY $JAVA_PKG /opt/jdk-11

ENV	PATH $JAVA_HOME/bin:$PATH

RUN set -eux; \
	microdnf install -y \
	    telnet \
	    curl \
# JDK assumes freetype is available
		freetype fontconfig \
	; \
	microdnf clean all; \
