#!/usr/bin/env bash

if [ -z ${WP6SRC+x} ]; then
  export WP6SRC=/vagrant;
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

cp -R $WP6SRC/conf-template/* /
WP6SRCESC=$(echo $WP6SRC | sed 's_/_\\/_g')
sed -i -e "s/\/cvmfs\/west-life.egi.eu\/software\/repository\/latest\/www/${WP6SRCESC}\/repository/g" /etc/httpd/conf.d/000-default.conf


systemctl enable httpd
systemctl start httpd

#create dirs
mkdir /home/vagrant/work /home/vagrant/.westlife /home/vagrant/logs

# add users to davfs2 group
usermod -a -G davfs2 vagrant
usermod -a -G davfs2 apache
usermod -g davfs2 vagrant

