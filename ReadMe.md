GraalVM Notes
=============

## Install GraalVM

```sh
cd ~/Downloads/
curl -LOJ https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-20.3.0/graalvm-ce-java8-linux-amd64-20.3.0.tar.gz
# curl -LOJ https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-20.3.0/graalvm-ce-java11-linux-amd64-20.3.0.tar.gz
cd /opt/
sudo mkdir graalvm
sudo chmod `whoami`:`whoami` graalvm
cd /opt/graalvm/
tar -xvzf ~/Downloads/graalvm-ce-java8-darwin-amd64-20.3.0.tar.gz

export GRAALVM_HOME=/opt/graalvm/graalvm-ce-java8-20.3.0/
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
tar -xvzf Pygments-2.7.2.tar.gz
cd Pygments-2.7.2/
graalpython setup.py install --user
rm -Rf Pygments-2.7.2/

gem install rouge
```
