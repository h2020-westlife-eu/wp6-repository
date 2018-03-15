#!/usr/bin/env bash
export WP6REPSRC=`pwd`
# prepare all configuration
cp -R $WP6REPSRC/conf-template/* /
# replace path to frontend in configuration to current path
export WP6SRCESC=$(echo $WP6REPSRC | sed 's_/_\\/_g')
sed -i -e "s/\/cvmfs\/west-life.egi.eu\/software\/repository\/latest\/frontend/${WP6SRCESC}\/frontend/g" /etc/httpd/conf.d/wp6-repository.conf
for file in $(ls /etc/httpd/conf.d/proxy* ); do sed -i -e "s/\/cvmfs\/west-life.egi.eu\/software\/repository\/latest/${WP6SRCESC}/g" "${file}";done
service httpd restart
