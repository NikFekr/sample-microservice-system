#!/bin/sh

###################### B #####################
#sudo yum remove docker docker-common docker-selinux docker-engine
#sudo yum remove docker-ce docker-ce-cli containerd.io
#sudo rm -rf /var/lib/docker /etc/docker
#sudo rm /etc/apparmor.d/docker
#sudo groupdel docker
#sudo rm -rf /var/run/docker.sock
#sudo rm -rf /etc/docker
#sudo rm -rf /run/docker
#sudo rm -rf /var/lib/dockershim
#sudo rm -rf /var/lib/docker
#sudo rm -rf /usr/libexec/docker

#sudo dnf -y install /path/to/package.rpm
#sudo systemctl start docker
#sudo systemctl start docker

#sudo yum install -y \
#  docker-ce-selinux-17.03.3.ce-1.el7.noarch.rpm \
#  docker-scan-plugin-0.8.0-3.el7.x86_64.rpm \
#  containerd.io-1.4.6-3.1.el7.x86_64.rpm \
#  docker-ce-cli-20.10.7-3.el7.x86_64.rpm \
#  docker-ce-20.10.7-3.el7.x86_64.rpm \
#  docker-ce-rootless-extras-20.10.7-3.el7.x86_64.rpm
###############################################

###################### A ###############################
#groupadd sudo
#
##visodo/
#vim /etc/sudoers
##put this line in sudoers if not exist
#%sudo   ALL=(ALL)       ALL
#
#usermod -aG sudo abis

###################### B #####################
#tar xzvf /path/to/<FILE>.tar.gz
#sudo cp docker/* /usr/bin/
#sudo dockerd &
#
##in order to defining service for docker
#sudo cp docker.service /lib/systemd/system/
#
#sudo groupadd docker
#sudo usermod -aG docker $USER
#
#sudo systemctl start docker.service
#sudo systemctl status docker.service
#docker load -i $image_dir/hellow-world.tar
#docker run hello-world:latest
###################### B #####################


#########################################################