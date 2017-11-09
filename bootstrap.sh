#!/usr/bin/env bash

if [ -z ${WP6REPSRC+x} ]; then
  export WP6REPSRC=/vagrant;
fi


# install davfs2
yum -y install epel-release
yum repolist
yum -y install davfs2 httpd

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

# SELinux in SL7.3 setting, allow proxy from apache to other services and security context to dir
if hash setsebool 2>/dev/null; then
  sed -i -e "s|\SELINUX=.*$|SELINUX=permissive|g" /etc/selinux/config
  setenforce 0
  setsebool -P httpd_can_network_connect 1
  chcon -R --reference=/var/www $WP6SRC/www
  firewall-cmd --zone=public --add-port=80/tcp --permanent
  firewall-cmd --reload
fi

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

#checkout backend submodule
if [ ! -d $WP6REPSRC/backend ]; then
   yum install -y git
   git clone https://github.com/andreagia/spring-wp6 $WP6REPSRC/backend
fi

#!/usr/bin/env bash
# initial db setting
#install backend submodule dependencies
#mariadb is GNU/GPL fork of mysql
yum install -y java mariadb-server maven
service mariadb start

#TODO generate random password, store it locally and distribute as a environment variable
export DBCRED=and1217
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
echo populating data
mysql --user=root --password=${DBCRED} < $WP6REPSRC/backend/createDB.sql

