#!/usr/bin/env bash
# Bootstrap script preparing environment for H2020 West-Life Repository Instance - D6.2
# expecting that the system is clean, minimal, tested on Scientific Linux 7.x and CernVM 4.x

# WP6REPSRC is directory where sources of D6.2 are available, set default value
if [ -z ${WP6REPSRC+x} ]; then
  if [ -f /vagrant/bootstrap.sh ]; then
    export WP6REPSRC=/vagrant
  fi
  if [ -f ./bootstrap.sh ]; then
    export WP6REPSRC=`pwd`
  fi
fi
if [ -z ${WP6REPSRC+x} ]; then
  echo 'please set WP6REPSRC environment variable'
  exit 1
fi
# copy sp_keys if they exists in /vagrant location
cp /vagrant/sp_cert.pem /vagrant/sp_key.pem /vagrant/idp-metadata.xml /vagrant/sp-metadata.xml ${WP6REPSRC}
# copy conf files from /vagrant location if exists
cp /vagrant/*.conf ${WP6REPSRC}/conf-template/etc/httpd/conf.d/


########################################################################
# Basic system preparation
########################################################################

# davfs2 - allows mounting remote WEBDAV storages into file system
# httpd - apache server
yum -y install epel-release
yum repolist
yum -y install davfs2 httpd
# php only for ARIA demo
yum -y install php php-common

# prepare all configuration
cp -R $WP6REPSRC/conf-template/* /
# replace path to frontend in configuration to current path
export WP6SRCESC=$(echo $WP6REPSRC | sed 's_/_\\/_g')
sed -i -e "s/\/cvmfs\/west-life.egi.eu\/software\/repository\/latest\/frontend/${WP6SRCESC}\/frontend/g" /etc/httpd/conf.d/wp6-repository.conf
for file in $(ls /etc/httpd/conf.d/proxy* ); do sed -i -e "s/\/cvmfs\/west-life.egi.eu\/software\/repository\/latest/${WP6SRCESC}/g" "${file}";done
#add +x permission on all html files which has include directive
# some html pages includes header with menu, footer etc shared among pages
#no html include needed anymore
#chmod ugo+x `grep -rl $WP6REPSRC/frontend/ -e "<\!--\#include"`
# syslog of westlife services logs are at /var/log/westlife/
service rsyslog restart

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
f=${WP6REPSRC}
while [[ $f != "/" ]]; do chmod g+rx $f; f=$(dirname $f); done;

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
# copy existing configuration

# generate the configuration if not exists, note that sp-metadata.xml needs to be sent to idp-metadata provider
if [ ! -f ${WP6REPSRC}/sp_key.pem ]; then
  echo "Generating mellon configuration"
  wget https://raw.githubusercontent.com/UNINETT/mod_auth_mellon/master/mellon_create_metadata.sh
  chmod +x mellon_create_metadata.sh
  ./mellon_create_metadata.sh $SP_IDENTIFICATION $SP_ENDPOINT
  # move to /vagrant file - so next boot, provision will be same
  mv http_local.west_life.eu.key ${WP6REPSRC}/sp_key.pem
  mv http_local.west_life.eu.cert ${WP6REPSRC}/sp_cert.pem
  mv http_local.west_life.eu.xml ${WP6REPSRC}/sp-metadata.xml
  #get west-life idp metadata
  wget https://auth.west-life.eu/proxy/saml2/idp/metadata.php
  mv metadata.php /${WP6REPSRC}/idp-metadata.xml
fi
# else the configuration exists (e.g. in /vagrant), it is reused

# copy the mellon configuration to /etc/httpd/mellon
echo "Copying mellon configuration from /vagrant";
mkdir -p /etc/httpd/mellon
cp ${WP6REPSRC}/sp_key.pem ${WP6REPSRC}/sp_cert.pem ${WP6REPSRC}/sp-metadata.xml ${WP6REPSRC}/idp-metadata.xml /etc/httpd/mellon
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

# checkout backend submodule if it is not yet downloaded,checked
if [ ! -d ${WP6REPSRC}/backend ]; then
   yum install -y git
   git clone https://github.com/andreagia/spring-wp6 $WP6REPSRC/backend
fi

# Install and configure database - mariadb is GNU/GPL fork of mysql
yum install -y java mariadb-server maven
systemctl enable mariadb.service
service mariadb start

# generate random password, store it locally and distribute as a environment variable
if [ ! -f /etc/westlife/repository.key ]; then
  export DBCRED=`openssl rand -base64 32`
  mkdir -p /etc/westlife/repository.key
  echo DBCRED=${DBCRED} > /etc/westlife/repository.key
  chown vagrant:vagrant /etc/westlife/repository.key
  chmod 600 /etc/westlife/repository.key

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
else
  source /etc/westlife/repository.key
fi

echo creating db
# create db for backend
mysql --user=root --password=${DBCRED} < $WP6REPSRC/backend/createDB.sql

#populate demo data
echo populating data
mysql --user=root --password=${DBCRED} < $WP6REPSRC/backend/populatetestDB.sql
#backend app gets frontend location from environment variable REP_LOCATION
sed -i -e "s/^\(WorkingDirectory\s*=\s*\).*$/\1${WP6SRCESC}\/backend/g" /etc/systemd/system/westlife-repository.service
sed -i -e "s/^\(Environment=REP_LOCATION=\s*\).*$/\1${WP6SRCESC}/g" /etc/systemd/system/westlife-repository.service
systemctl enable westlife-repository
systemctl enable mariadb
service westlife-repository start