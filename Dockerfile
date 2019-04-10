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

RUN wget "http://search.maven.org/remotecontent?filepath=net/sourceforge/saxon/saxon/9.1.0.8/saxon-9.1.0.8.jar" -O /usr/local/lib/saxon9.jar
RUN wget http://search.maven.org/remotecontent?filepath=com/github/markusbernhardt/xml-doclet/1.0.5/xml-doclet-1.0.5-jar-with-dependencies.jar -O /usr/local/lib/xml-doclet.jar
RUN pip install jira 
#RUN git clone https://github.com/devcon5io/mutation-analysis-plugin.git; \
#	cd mutation-analysis-plugin;\
#	git checkout standalone-analysis;\
#	mvn install
RUN git clone --branch feature/compile_with_java_11 https://github.com/magwas/xml-doclet.git
RUN cd xml-doclet/; mvn install

RUN git clone --branch feature/java10_and_formatting https://github.com/magwas/PDEngine.git
RUN rm /dev/random; cp -a /dev/urandom /dev/random; ls -l /dev/random /dev/urandom;Xvnc4 -SecurityTypes none :0 & export DISPLAY=:0;cd PDEngine; make
RUN rm -rf PDEngine mutation-analysis-plugin
RUN sed 's/.ALL:ALL./(ALL) NOPASSWD:/' -i /etc/sudoers
RUN wget "https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/2019-03/R/eclipse-jee-2019-03-R-linux-gtk-x86_64.tar.gz&mirror_id=1045" -O /tmp/eclipse.tar.gz
RUN tar xzf /tmp/eclipse.tar.gz
RUN mv eclipse /opt
ENTRYPOINT ["/pdengine/tools/entrypoint"]
CMD ["/bin/bash"]
