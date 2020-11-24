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
```
