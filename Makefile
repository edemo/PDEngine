all: install

install: compile sonar shippable
	cp -rf engine/* target/* shippable

shippable:
	mkdir -p shippable

sonar: sonarconfig javabuild
	mvn sonar:sonar -Dsonar.organization=edemo
	./tools/pullanalize

sonarconfig:
	cp etc/m2/settings.xml ~/.m2

compile: zentaworkaround javabuild engine.compiled codedocs

codedocs: shippable/engine-testcases.xml shippable/engine-implementedBehaviours.xml shippable/engine-implementedBehaviours.html

shippable/engine-testcases.xml: engine.richescape shippable
	zenta-xslt-runner -xsl:generate_test_cases.xslt -s engine.richescape outputbase=shippable/engine-

shippable/engine-implementedBehaviours.xml: javadoc shippable
	zenta-xslt-runner -xsl:generate-behaviours.xslt -s target/test/javadoc.xml outputbase=shippable/engine-

javadoc:
	mkdir -p target/production target/test
	CLASSPATH=/usr/local/lib/xml-doclet.jar:~/.m2/repository/junit/junit/4.11/junit-4.11.jar:target/classes\
     javadoc -encoding utf-8 -doclet com.github.markusbernhardt.xmldoclet.XmlDoclet -sourcepath src/main/java -d target/production org.rulez.demokracia.pdengine
	CLASSPATH=/usr/local/lib/xml-doclet.jar:~/.m2/repository/junit/junit/4.11/junit-4.11.jar:target/classes\
     javadoc -encoding utf-8 -doclet com.github.markusbernhardt.xmldoclet.XmlDoclet -sourcepath src/test/java -d target/test org.rulez.demokracia.pdengine

include /usr/share/zenta-tools/model.rules

testenv:
	docker run --rm -p 5900:5900 -e PULL_REQUEST=false -e ORG_NAME=local -v $$(pwd):/pdengine -w /pdengine -it edemo/pdengine

javabuild: target/PDEngine-0.0.1-SNAPSHOT.jar

target/PDEngine-0.0.1-SNAPSHOT.jar:
	mvn build-helper:parse-version versions:set versions:commit -DnewVersion=\$${parsedVersion.majorVersion}.\$${parsedVersion.minorVersion}.\$${parsedVersion.incrementalVersion}-$$(tools/getbranch|sed 'sA/A_Ag').$$(git rev-parse --short HEAD)
	mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install org.pitest:pitest-maven:mutationCoverage

clean:
	git clean -fdx
	rm -rf zenta-tools

inputs/engine.issues.xml: shippable/engine-implementedBehaviours.xml shippable/engine-testcases.xml
	mkdir -p inputs
	getGithubIssues https://api.github.com "repo:edemo/PDEngine&per_page=100" >inputs/engine.issues.xml

zentaworkaround:
	mkdir -p ~/.zenta/.metadata/.plugins/org.eclipse.e4.workbench/
	cp workbench.xmi ~/.zenta/.metadata/.plugins/org.eclipse.e4.workbench/
	touch zentaworkaround

