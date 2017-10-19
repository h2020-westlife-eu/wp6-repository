#!/usr/bin/env bash
# installs SAML2 and integrates with Westlife AAI

yum -y install wget mod_auth_mellon 

if [ ! -f /vagrant/sp_key.pem ]; then
# generate the configuration, note that sp-metadata.xml needs to be sent to idp-metadata provider
  echo "Generating mellon configuration"
  wget https://raw.githubusercontent.com/UNINETT/mod_auth_mellon/master/mellon_create_metadata.sh
  chmod +x mellon_create_metadata.sh
  ./mellon_create_metadata.sh http://local.west-life.eu http://localhost:8080/mellon
  # move to /vagrant file - so next boot, provision will be same
  mv http_local.west_life.eu.key /vagrant/sp_key.pem
  mv http_local.west_life.eu.cert /vagrant/sp_cert.pem
  mv http_local.west_life.eu.xml /vagrant/sp-metadata.xml
  #get west-life idp metadata
  wget https://auth.west-life.eu/proxy/saml2/idp/metadata.php
  mv metadata.php /vagrant/idp-metadata.xml
fi

# copy the mellon configuration to /etc/httpd/mellon
echo "Copying mellon configuration from /vagrant";
mkdir -p /etc/httpd/mellon
cp /vagrant/sp_key.pem /vagrant/sp_cert.pem /vagrant/sp-metadata.xml /vagrant/idp-metadata.xml /etc/httpd/mellon
chmod 600 /etc/httpd/mellon/sp_key.pem

#install simplesamlphp
yum -y install php
wget https://simplesamlphp.org/download?latest -O simplesamlphp.tar.gz
tar xzf simplesamlphp.tar.gz
SSP=`find . -maxdepth 1 -type d -name 'simplesamlphp*'`
mv $SSP simplesamlphp



service httpd restart
