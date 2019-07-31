FROM ubuntu:bionic

RUN apt-get update
RUN apt-get -y upgrade

RUN apt-get -y install software-properties-common apt-transport-https
RUN apt-add-repository -y ppa:openjdk-r/ppa

RUN until apt-key adv --keyserver keyserver.ubuntu.com --recv EF678DA6D5B1436D3972DFD317BE949418BE5D6B; do echo retrying; done
RUN echo deb http://repos.demokracia.rulez.org/apt/debian/ master main >/etc/apt/sources.list.d/repos.demokracia.rulez.org.list

RUN apt-get update
RUN export DEBIAN_FRONTEND=noninteractive;apt-get -y install openjdk-11-jdk wget git xvfb unzip docbook-xsl make firefox vnc4server\
    dblatex libwebkitgtk-3.0-0 libswt-webkit-gtk-3-jni python-yaml python-pip python-dateutil\
    zip debhelper devscripts zenta zenta-tools maven haveged vim sudo less rsync curl jq

RUN pip install jira 
RUN git clone --branch standalone-analysis-java11 https://github.com/magwas/mutation-analysis-plugin.git; \
	cd mutation-analysis-plugin;\
	mvn install;\
    cp ~/.m2/repository/ch/devcon5/sonar/mutation-analysis-plugin/1.3-SNAPSHOT/mutation-analysis-plugin-1.3-SNAPSHOT.jar /usr/local/lib/;\
    cd .. ; rm -rf mutation-analysis-plugin
RUN git clone --branch feature/compile_with_java_11 https://github.com/magwas/xml-doclet.git;\
    cd xml-doclet/; mvn install;\
    cd .. ; rm -rf xml-doclet

RUN git clone --branch docker/java11 https://github.com/edemo/PDEngine.git;\
    rm /dev/random; cp -a /dev/urandom /dev/random;\
    cd PDEngine; export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64;\
    mvn clean install;\
    cd .. ; rm -rf PDEngine
RUN sed 's/.ALL:ALL./(ALL) NOPASSWD:/' -i /etc/sudoers
RUN wget -q "http://ftp.halifax.rwth-aachen.de/eclipse//technology/epp/downloads/release/2019-03/R/eclipse-jee-2019-03-R-linux-gtk-x86_64.tar.gz" -O /tmp/eclipse.tar.gz;\
    cd /opt ; tar xzf /tmp/eclipse.tar.gz;\
    rm /tmp/eclipse.tar.gz
RUN /opt/eclipse/eclipse -application org.eclipse.equinox.p2.director\
	-repository http://download.eclipse.org/cft/1.2.0\
	-repository http://download.eclipse.org/cft/1.2.0/org.eclipse.cft-1.2.0.v201805291812\
	-repository http://download.eclipse.org/eclipse/updates/4.11\
	-repository http://download.eclipse.org/eclipse/updates/4.11/R-4.11-201903070500\
	-repository http://download.eclipse.org/mpc/releases/1.7.7\
	-repository http://download.eclipse.org/releases/2019-03\
	-repository http://download.eclipse.org/releases/2019-03/201903201000\
	-repository http://download.eclipse.org/releases/latest\
	-repository http://download.eclipse.org/usssdk/drops/release/1.2.0\
	-repository http://download.eclipse.org/usssdk/updates/release/latest\
	-repository http://download.eclipse.org/webtools/repository/2019-06\
	-repository http://download.eclipse.org/webtools/repository/latest\
	-repository https://download.eclipse.org/technology/epp/packages/2019-03\
	-repository https://download.eclipse.org/webtools/downloads/drops/R3.14.0/R-3.14.0-20190612230649/repository\
	-repository https://download.springsource.com/release/TOOLS/sts4-language-servers/e4.8\
	-repository https://download.springsource.com/release/TOOLS/sts4/update/4.3.1.RELEASE/e4.11\
	-repository https://download.springsource.com/release/TOOLS/sts4/update/e4.11\
	-repository https://dl.bintray.com/pmd/pmd-eclipse-plugin/releases/4.2.0.v20190331-1136\
 	-installIUs net.sourceforge.pmd.eclipse.feature.group\
	-installIUs com.fasterxml.jackson.core.jackson-annotations\
	-installIUs com.fasterxml.jackson.core.jackson-core\
	-installIUs com.fasterxml.jackson.core.jackson-databind\
	-installIUs com.hierynomus.sshj\
	-installIUs com.jcraft.jzlib\
	-installIUs io.projectreactor.reactor-core\
	-installIUs javassist\
	-installIUs javax.ws.rs\
	-installIUs org.aopalliance\
	-installIUs org.dadacoalition.yedit\
	-installIUs org.eclipse.lsp4j.jsonrpc\
	-installIUs org.eclipse.lsp4j\
	-installIUs org.eclipse.tm4e.core\
	-installIUs org.eclipse.tm4e.registry\
	-installIUs org.eclipse.tm4e.ui\
	-installIUs org.eclipse.xtext.xbase.lib\
	-installIUs org.glassfish.hk2.api\
	-installIUs org.glassfish.hk2.locator\
	-installIUs org.glassfish.hk2.osgi-resource-locator\
	-installIUs org.glassfish.hk2.utils\
	-installIUs org.glassfish.jersey.bundles.repackaged.jersey-guava\
	-installIUs org.glassfish.jersey.core.jersey-client\
	-installIUs org.glassfish.jersey.core.jersey-common\
	-installIUs org.jcodings\
	-installIUs org.joni\
	-installIUs org.json\
	-installIUs org.reactivestreams.reactive-streams\
	-installIUs org.springframework.ide.eclipse.beans.ui.live\
	-installIUs org.springframework.ide.eclipse.boot.dash\
	-installIUs org.springframework.ide.eclipse.boot.launch\
	-installIUs org.springframework.ide.eclipse.boot.refactoring\
	-installIUs org.springframework.ide.eclipse.boot.restart\
	-installIUs org.springframework.ide.eclipse.boot.templates\
	-installIUs org.springframework.ide.eclipse.boot.validation\
	-installIUs org.springframework.ide.eclipse.boot.wizard\
	-installIUs org.springframework.ide.eclipse.boot\
	-installIUs org.springframework.ide.eclipse.buildship20\
	-installIUs org.springframework.ide.eclipse.buildship30\
	-installIUs org.springframework.ide.eclipse.editor.support\
	-installIUs org.springframework.ide.eclipse.imports\
	-installIUs org.springframework.ide.eclipse.xml.namespaces\
	-installIUs org.springframework.tooling.boot.ls\
	-installIUs org.springframework.tooling.bosh.ls\
	-installIUs org.springframework.tooling.cloudfoundry.manifest.ls\
	-installIUs org.springframework.tooling.concourse.ls\
	-installIUs org.springframework.tooling.jdt.ls.commons\
	-installIUs org.springframework.tooling.ls.eclipse.commons\
	-installIUs org.springframework.tooling.ls.eclipse.gotosymbol\
	-installIUs org.springsource.ide.eclipse.commons.cloudfoundry.client.v2\
	-installIUs org.springsource.ide.eclipse.commons.core\
	-installIUs org.springsource.ide.eclipse.commons.frameworks.core\
	-installIUs org.springsource.ide.eclipse.commons.frameworks.ui\
	-installIUs org.springsource.ide.eclipse.commons.livexp\
	-installIUs org.springsource.ide.eclipse.commons.quicksearch\
	-installIUs org.springsource.ide.eclipse.commons.ui\
	-installIUs org.yaml.snakeyaml\
	-installIUs org.springframework.tooling.boot.ls\
	-installIUs org.springframework.ide.eclipse.xml.namespaces\
	-installIUs org.springframework.tooling.bosh.ls\
	-installIUs org.springframework.tooling.cloudfoundry.manifest.ls\
	-installIUs org.springframework.tooling.concourse.ls\
	-installIUs org.springsource.ide.eclipse.commons.quicksearch\
	-installIUs org.springframework.ide.eclipse.boot.dash\
	-noSplash
#	-installIUs org.eclipse.lsp4e\
#	-installIUs org.springsource.ide.eclipse.commons.jdk\
RUN wget -q https://vorboss.dl.sourceforge.net/project/pydev/pydev/PyDev%207.2.1/PyDev%207.2.1.zip -O /tmp/pydev.zip;\
    unzip /tmp/pydev.zip -d /opt/eclipse;\
    rm /tmp/pydev.zip
RUN wget -q https://projectlombok.org/downloads/lombok.jar -O /usr/local/lib/lombok.jar;\
    java -jar /usr/local/lib/lombok.jar install /opt/eclipse
ENTRYPOINT ["/build/tools/entrypoint"]
CMD ["/bin/bash"]
