FROM ubuntu:xenial

RUN locale-gen en_US en_US.UTF-8 && \
    dpkg-reconfigure locales

RUN apt-get update

RUN apt-get -o Dpkg::Options::="--force-confold" --force-yes -fuy upgrade

RUN apt-get -y install software-properties-common
RUN apt-add-repository -y ppa:openjdk-r/ppa
RUN apt-key adv --keyserver keys.gnupg.net --recv B761AA278C7AB952
RUN echo deb http://repos.demokracia.rulez.org/apt/debian/ master main >/etc/apt/sources.list.d/repos.demokracia.rulez.org.list

RUN apt-get update
RUN apt-get -y install openjdk-8-jdk wget git xvfb unzip docbook-xsl make firefox vnc4server dblatex libwebkitgtk-3.0-0 libswt-webkit-gtk-3-jni python-yaml python-pip python-dateutil zip debhelper devscripts zenta zenta-tools maven

RUN wget "http://search.maven.org/remotecontent?filepath=net/sourceforge/saxon/saxon/9.1.0.8/saxon-9.1.0.8.jar" -O /usr/local/lib/saxon9.jar
RUN wget http://search.maven.org/remotecontent?filepath=com/github/markusbernhardt/xml-doclet/1.0.5/xml-doclet-1.0.5-jar-with-dependencies.jar -O /usr/local/lib/xml-doclet.jar

RUN pip install jira 

