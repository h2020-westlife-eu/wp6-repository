cd /home/vagrant/wp6-repository
git pull
sudo bash <<EOF
service westlife-repository stop
service httpd stop
export WP6SRC=`pwd`
./bootstrap.sh
EOF
