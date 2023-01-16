#!/bin/sh

sudo yum install -y --cacheonly --disablerepo=* fuse3-libs-3.6.1-4.el7.x86_64.rpm \
      slirp4netns-0.4.3-4.el7_8.x86_64.rpm \
      fuse-overlayfs-0.7.2-6.el7_8.x86_64.rpm \
      container-selinux-2.119.2-1.911c772.el7_8.noarch.rpm \
      docker-ce-selinux-17.03.3.ce-1.el7.noarch.rpm \
      docker-ce-cli-20.10.7-3.el7.x86_64.rpm \
      docker-scan-plugin-0.8.0-3.el7.x86_64.rpm \
      containerd.io-1.4.6-3.1.el7.x86_64.rpm \
      docker-ce-20.10.7-3.el7.x86_64.rpm \
      docker-ce-rootless-extras-20.10.7-3.el7.x86_64.rpm

sudo groupadd docker || true
sudo usermod -aG docker $USER

sudo systemctl start docker.service
sudo systemctl status docker.service

sudo docker load -i hellow-world.tar
sudo docker run 127.0.0.1:8082/hello-world:latest

sudo cp docker-compose /usr/local/bin/
sudo chmod +x /usr/local/bin/docker-compose

printf "\n***** docker compose version *****\n"
docker-compose --version

printf "\n****logging out from user $USER..."
sleep 5

pkill -KILL -u $USER
