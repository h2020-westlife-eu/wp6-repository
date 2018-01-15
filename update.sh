#!/bin/sh
echo asdf
cd /home/vagrant/wp6-repository
UPSTREAM=$(git rev-parse --abbrev-ref --symbolic-full-name @{u})
git fetch
LOCAL=$(git rev-parse HEAD)
echo $LOCAL
REMOTE=$(git rev-parse origin/dev-tomas)
echo $REMOTE
BASE=$(git merge-base HEAD "$UPSTREAM")
echo $BASE

if [ $LOCAL = $REMOTE ]; then
    echo "Up-to-date"
elif [ $LOCAL = $BASE ]; then
    echo "Need to pull"
git pull
sudo bash <<EOF
service westlife-repository stop
service httpd stop
export WP6SRC=`pwd`
./bootstrap.sh
EOF
elif [ $REMOTE = $BASE ]; then
    echo "Need to push"
else
    echo "Diverged"
fi
