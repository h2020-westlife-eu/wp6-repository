#!/usr/bin/env bash
# bootstraps frontend compilation tools
yum -y install npm
npm install
npm install aurelia-cli -g

#au run --watch
