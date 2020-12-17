GraalVM Notes
=============


## Deploy

```sh
sudo yum -y upgrade
sudo yum -y install epel-release vim git logrotate openssh deltarpm yum-utils p7zip p7zip-plugins
sudo timedatectl set-timezone "Europe/Moscow"

sudo yum -y install postgresql-server postgresql-contrib
sudo postgresql-setup initdb
sudo systemctl start postgresql
sudo systemctl enable postgresql

sudo -i -u postgres
vim data/pg_hba.conf

createdb code
createuser --interactive # user, n, n, n.

psql
ALTER USER user WITH PASSWORD 'password';
\q

exit

sudo systemctl restart postgresql


sudo firewall-cmd --zone=public --permanent --add-service=http
sudo firewall-cmd --zone=public --permanent --add-service=https
sudo firewall-cmd --reload

sudo yum -y install nginx certbot python2-certbot-nginx

sudo setsebool -P httpd_can_network_connect 1

sudo reboot

sudo systemctl start nginx

sudo systemctl start nginx

sudo certbot certonly --nginx
echo "0 0,12 * * * root python -c 'import random; import time; time.sleep(random.random() * 3600)' && certbot renew -q" | sudo tee -a /etc/crontab > /dev/null


```

## Install GraalVM

```sh
curl -LOJ https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-20.3.0/graalvm-ce-java8-linux-amd64-20.3.0.tar.gz
# curl -LOJ https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-20.3.0/graalvm-ce-java11-linux-amd64-20.3.0.tar.gz
cd /opt/
sudo mkdir graalvm
sudo chown `whoami`:`whoami` graalvm
cd /opt/graalvm/
tar -xvzf ~/graalvm-ce-java8-linux-amd64-20.3.0.tar.gz
rm ~/graalvm-ce-java8-linux-amd64-20.3.0.tar.gz

export GRAALVM_HOME=/opt/graalvm/graalvm-ce-java8-20.3.0
export JAVA_HOME=$GRAALVM_HOME
export PATH=$GRAALVM_HOME/bin:$PATH
```

## Install Polyglot languages and libraries

```sh
gu install python
gu install ruby
# /opt/graalvm/graalvm-ce-java8-20.3.0/jre/languages/ruby/lib/truffle/post_install_hook.sh

graalpython -m ginstall install setuptools
curl -LOJ https://github.com/pygments/pygments/archive/2.7.2.tar.gz
# curl -LOJ https://files.pythonhosted.org/packages/5d/0e/ff13c055b014d634ed17e9e9345a312c28ec6a06448ba6d6ccfa77c3b5e8/Pygments-2.7.2.tar.gz
tar -xvzf pygments-2.7.2.tar.gz
cd pygments-2.7.2/
graalpython setup.py install --user
cd ..
rm -Rf pygments-2.7.2/ pygments-2.7.2.tar.gz

gem install rouge
```
