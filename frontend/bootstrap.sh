#!/usr/bin/env bash
# bootstraps frontend compilation tools
yum -y install npm
npm install
#on mounted FS - e.g. shared /vagrant folder 'npm install' cause errors
#npm install --no-bin-links
#if error appears - maximum call stack size exceeded, repeat
#npm install --no-bin-links

#on standard FS - issue
#npm install
sudo npm install aurelia-cli -g


#au run --watch
