FROM ubuntu:bionic

RUN apt-get update
RUN apt-get -y upgrade

RUN apt-get -y install software-properties-common apt-transport-https
RUN apt-add-repository -y ppa:openjdk-r/ppa
RUN until apt-key adv --keyserver keys.gnupg.net --recv 43DB103F31060848; do echo retrying; done
RUN echo deb http://repos.demokracia.rulez.org/apt/debian/ master main >/etc/apt/sources.list.d/repos.demokracia.rulez.org.list

RUN apt-get update
RUN export DEBIAN_FRONTEND=noninteractive;apt-get -y install openjdk-11-jdk wget git xvfb unzip docbook-xsl make firefox vnc4server\
    dblatex libwebkitgtk-3.0-0 libswt-webkit-gtk-3-jni python-yaml python-pip python-dateutil\
    zip debhelper devscripts zenta zenta-tools maven haveged vim sudo less

#RUN wget "http://search.maven.org/remotecontent?filepath=net/sourceforge/saxon/saxon/9.1.0.8/saxon-9.1.0.8.jar" -O /usr/local/lib/saxon9.jar
#RUN wget http://search.maven.org/remotecontent?filepath=com/github/markusbernhardt/xml-doclet/1.0.5/xml-doclet-1.0.5-jar-with-dependencies.jar -O /usr/local/lib/xml-doclet.jar
RUN pip install jira 
RUN git clone --branch standalone-analysis-java11 https://github.com/magwas/mutation-analysis-plugin.git; \
	cd mutation-analysis-plugin;\
	mvn install;\
    cp ~/.m2/repository/ch/devcon5/sonar/mutation-analysis-plugin/1.3-SNAPSHOT/mutation-analysis-plugin-1.3-SNAPSHOT.jar /usr/local/lib/;\
    cd .. ; rm -rf mutation-analysis-plugin
RUN git clone --branch feature/compile_with_java_11 https://github.com/magwas/xml-doclet.git;\
    cd xml-doclet/; mvn install;\
    cd .. ; rm -rf xml-doclet

RUN git clone --branch feature/java10_and_formatting-afterDocker https://github.com/magwas/PDEngine.git;\
    rm /dev/random; cp -a /dev/urandom /dev/random;\
    cd PDEngine; export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64;\
    make maven;\
    cd .. ; rm -rf PDEngine
RUN sed 's/.ALL:ALL./(ALL) NOPASSWD:/' -i /etc/sudoers
RUN wget "http://ftp.halifax.rwth-aachen.de/eclipse//technology/epp/downloads/release/2019-03/R/eclipse-jee-2019-03-R-linux-gtk-x86_64.tar.gz" -O /tmp/eclipse.tar.gz;\
    cd /opt ; tar xzf /tmp/eclipse.tar.gz;\
    rm /tmp/eclipse.tar.gz
ENTRYPOINT ["/build/tools/entrypoint"]
CMD ["/bin/bash"]
