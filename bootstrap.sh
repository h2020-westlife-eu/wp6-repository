#!/usr/bin/env bash

if [ -z ${WP6REPSRC+x} ]; then
  export WP6REPSRC=/vagrant;
fi


# install davfs2
yum -y install epel-release
yum repolist
yum -y install davfs2

# install and start web server
# httpd is present in cernvm
# yum -y install httpd
# prepare and restart apache, rewrite configuration
# copy all system config to etc

cp -R $WP6REPSRC/conf-template/* /
WP6SRCESC=$(echo $WP6REPSRC | sed 's_/_\\/_g')
sed -i -e "s/\/cvmfs\/west-life.egi.eu\/software\/repository\/latest\/frontend/${WP6SRCESC}\/frontend/g" /etc/httpd/conf.d/wp6-repository.conf
sed -i -e "s/\/cvmfs\/west-life.egi.eu\/software\/repository\/latest\/frontend/${WP6SRCESC}\/frontend/g" /etc/httpd/conf.d/wp6-aai.conf
#add +x permission on all html files which has include directive
chmod ugo+x `grep -rl $WP6REPSRC/frontend/ -e "<\!--\#include"`

systemctl enable httpd
# stop httpd if it is running by some other related software
systemctl stop httpd

systemctl start httpd

#create dirs
mkdir /home/vagrant/work /home/vagrant/.westlife /home/vagrant/logs

# add users to davfs2 group
usermod -a -G davfs2 vagrant
usermod -a -G davfs2 apache
usermod -g davfs2 vagrant

