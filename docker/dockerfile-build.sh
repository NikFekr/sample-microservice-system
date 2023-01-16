#!/bin/sh

docker build --network=host \
 --build-arg https_proxy=http://127.0.0.1:8118 -t 127.0.0.1:8082/oraclelinux_net_tools:8-slim .

#>>>>>for build port should be 8082 because all Dockerfiles set to read the image from 8082
#docker build --network=host \
#  -t 127.0.0.1:8082/oraclelinux_net_tools:8-slim .

#>>>>>for push build and push command should be in a same repository port
#docker build --network=host \
#  -t 127.0.0.1:8082/oraclelinux_net_tools:8-slim .
#
#docker push 127.0.0.1:8083/oraclelinux_net_tools:8-slim