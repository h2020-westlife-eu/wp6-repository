#!/usr/bin/env bash
export WP6REPSRC=`pwd`
export DBCRED=and1217
echo creating db
# create db for backend
echo populating data
mysql --user=root --password=${DBCRED} < $WP6REPSRC/backend/createDB.sql
#TODO link frontend directory to webapp doesn't work on shared folders, needs to be change in source code
#ln -s $WP6REPSRC/frontend $WP6REPSRC/backend/src/main/webapp/frontend
# change appropriate conf of repository service
#sed -i -e "s/^\(WorkingDirectory\s*=\s*\).*$/\1\${WP6SRCESC}/backend/g" /etc/systemd/system/westlife-repository.service
#sed -i -e "s/^\(Environment=REP_LOCATION\s*=\s*\).*$/\1\${WP6SRCESC}/g" /etc/systemd/system/westlife-repository.service
#service westlife-repository start

