#!/usr/bin/env bash

# controlproxy.sh [-a/-r] [directory] [randomproxy]
function help {
 echo "Usage: controlproxy.sh [-a/-r] [directory] [randomproxysuffix]
             -a [directory] [randomproxysuffix] adds a new proxy configuration
             -r [directory] [randomproxysuffix] removes existing proxy configuration"
}

function addproxy {
#adding proxy
#generate random base64 string if not specified
if [ -z $2 ]; then
  export PROXY=`openssl rand -base64 6 | tr -d '=' | tr '/+' '_-'`
  echo $PROXY
else
  export PROXY=$2
fi

#create apache configuration with proxy
#moddav to get from specified directory
APACHEFILE=/etc/httpd/conf.d/proxy-${PROXY}.conf

mkdir -p $1

echo "Alias '/files/${PROXY}' '$1/'
<Directory '$1'>
  DAV On
  Require all granted
  Options +Indexes
</Directory>" > $APACHEFILE
service httpd reload 1>/dev/null 2&>1
}

function removeproxy {
#generate random base64 string if not specified
if [ -z $2 ]; then
  echo proxy not specified;
  help
  exit
else
  export PROXY=$2
fi
  APACHEFILE=/etc/httpd/conf.d/proxy-${PROXY}.conf
  rm -rf ${APACHEFILE}
  service httpd reload
}

if [ $1 == '-a' ]; then
 addproxy $2 $3
 exit
fi

if [ $1 == '-r' ]; then
  removeproxy $2 $3
  exit
fi

help
