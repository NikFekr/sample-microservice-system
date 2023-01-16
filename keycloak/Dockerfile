#FROM 127.0.0.1:8082/jboss/keycloak
#
#WORKDIR /opt/jboss/
#
#ENV DB_VENDOR mariadb
#
#COPY ../jdbc keycloak/modules/system/layers/base/com/oracle/jdbc/main/driver
#
#COPY patch-files patch-files
#
##COPY jboss-tools tools
#
#USER root
#RUN yum -y install patch \
# && patch -n tools/docker-entrypoint.sh patch-files/docker-entrypoint.sh.patch \
# && yum -y remove patch
#
#USER jboss