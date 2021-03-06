#!/usr/bin/env bash
# Bootstrap script preparing environment for H2020 West-Life Repository Instance - D6.2
# using binaries from cvmfs
# expecting that the system is clean, minimal, tested on Scientific Linux 7.x and CernVM 4.x

# WP6REPSRC is directory where sources of D6.2 are available, if not set,
if [ -z ${WP6REPSRC+x} ]; then
  export WP6REPSRC=/cvmfs/west-life.eg.eu/software/repository/latest;
fi

########################################################################
# Basic system preparation
########################################################################

# davfs2 - allows mounting remote WEBDAV storages into file system
# httpd - apache server
yum -y install epel-release
yum repolist
yum -y install davfs2 httpd

# prepare all configuration
cp -R $WP6REPSRC/conf/* /
# replace path to frontend in configuration to current path
#export WP6SRCESC=$(echo $WP6REPSRC | sed 's_/_\\/_g')
#sed -i -e "s/\/cvmfs\/west-life.egi.eu\/software\/repository\/latest\/frontend/${WP6SRCESC}\/frontend/g" /etc/httpd/conf.d/wp6-repository.conf
#add +x permission on all html files which has include directive
# some html pages includes header with menu, footer etc shared among pages
#chmod ugo+x `grep -rl $WP6REPSRC/frontend/ -e "<\!--\#include"`

# SELinux in SL7.3 setting, allow proxy from apache to other services and security context to dir
if hash setsebool 2>/dev/null; then
  sed -i -e "s|\SELINUX=.*$|SELINUX=permissive|g" /etc/selinux/config
  setenforce 0
  setsebool -P httpd_can_network_connect 1
  chcon -R --reference=/var/www $WP6SRC/www
  firewall-cmd --zone=public --add-port=80/tcp --permanent
  firewall-cmd --reload
fi
#set RX permision on WP6REPSRC and all parents, so apache can read and access WP6REPSRC
#f=${WP6REPSRC}
#while [[ $f != "/" ]]; do chmod g+rx $f; f=$(dirname $f); done;

# enabling Apache server
systemctl enable httpd
# stop httpd if it is running by some other related software
systemctl stop httpd
systemctl start httpd

#create dirs
mkdir /home/vagrant/work /home/vagrant/.westlife

# add users to davfs2 group
usermod -a -G davfs2 vagrant
usermod -a -G davfs2 apache
usermod -g davfs2 vagrant

########################################################################
# SSO preparation
########################################################################

# installs SAML2 and integrates with Westlife AAI
# default values of SP_IDENTIFICAION and SP_ENDPOINT, please edit them for production system
SP_IDENTIFICATION=http://local.west-life.eu
SP_ENDPOINT=http://localhost:8080/mellon

# skip broken on cernvm4
yum -y install wget mod_auth_mellon --skip-broken
if [ ! -f /vagrant/sp_key.pem ]; then
# generate the configuration, note that sp-metadata.xml needs to be sent to idp-metadata provider
  echo "Generating mellon configuration"
  wget https://raw.githubusercontent.com/UNINETT/mod_auth_mellon/master/mellon_create_metadata.sh
  chmod +x mellon_create_metadata.sh
  ./mellon_create_metadata.sh $SP_IDENTIFICATION $SP_ENDPOINT
  # move to /vagrant file - so next boot, provision will be same
  mv http_local.west_life.eu.key /vagrant/sp_key.pem
  mv http_local.west_life.eu.cert /vagrant/sp_cert.pem
  mv http_local.west_life.eu.xml /vagrant/sp-metadata.xml
  #get west-life idp metadata
  wget https://auth.west-life.eu/proxy/saml2/idp/metadata.php
  mv metadata.php //vagrant/idp-metadata.xml
fi

# copy the mellon configuration to /etc/httpd/mellon
echo "Copying mellon configuration from /vagrant";
mkdir -p /etc/httpd/mellon
cp /vagrant/sp_key.pem /vagrant/sp_cert.pem /vagrant/sp-metadata.xml /vagrant/idp-metadata.xml /etc/httpd/mellon
chmod 600 /etc/httpd/mellon/sp_key.pem
echo "Check http://localhost:8080/mellon/metadata"
echo "If not yet registered, send the metadata file: sp-metadata.xml to West-life AAI provider westlife-aai@ics.muni.cz."

#install simplesamlphp
#yum -y install php
#wget https://simplesamlphp.org/download?latest -O simplesamlphp.tar.gz
#tar xzf simplesamlphp.tar.gz
#SSP=`find . -maxdepth 1 -type d -name 'simplesamlphp*'`
#mv $SSP simplesamlphp

service httpd restart

########################################################################
# Backend preparation
########################################################################

# Install and configure database - mariadb is GNU/GPL fork of mysql
yum install -y java mariadb-server maven
systemctl enable mariadb.service
service mariadb start

# generate random password, store it locally and distribute as a environment variable
export DBCRED=`openssl rand -base64 32`
echo DBCRED=${DBCRED} > /home/vagrant/.westlife/repository.key

echo setting db
mysql --user=root <<EOF
use mysql
update user set password=PASSWORD("${DBCRED}") where User="root";
DELETE FROM mysql.user WHERE User='';
DELETE FROM mysql.user WHERE User='root' AND Host NOT IN ('localhost', '127.0.0.1', '::1');
DROP DATABASE IF EXISTS test;
DELETE FROM mysql.db WHERE Db='test' OR Db='test\\_%';
FLUSH PRIVILEGES;
EOF

echo creating db
# create db for backend
mysql --user=root --password=${DBCRED} < $WP6REPSRC/backend/createDB.sql

#populate demo data
echo populating data
mysql --user=root --password=${DBCRED} < $WP6REPSRC/backend/populatetestDB.sql
#backend app gets frontend location from environment variable REP_LOCATION
#sed -i -e "s/^\(WorkingDirectory\s*=\s*\).*$/\1${WP6SRCESC}\/backend/g" /etc/systemd/system/westlife-repository.service
#sed -i -e "s/^\(Environment=REP_LOCATION=\s*\).*$/\1${WP6SRCESC}/g" /etc/systemd/system/westlife-repository.service
systemctl enable westlife-repository.service
service westlife-repository start
