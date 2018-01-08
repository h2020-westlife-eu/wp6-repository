#!/usr/bin/env bash
export WP6REPSRC=`pwd`
source /home/vagrant/.westlife/repository.key
echo creating db
# create db for backend
mysql --user=root --password=${DBCRED} < $WP6REPSRC/backend/createDB.sql
echo populating data
mysql --user=root --password=${DBCRED} < $WP6REPSRC/backend/populatetestDB.sql
