all: install

install: compile shippable
	cp -rf engine/* target/* shippable

shippable:
	mkdir -p shippable

compile: zentaworkaround javabuild engine.compiled codedocs

codedocs: shippable/engine-testcases.xml shippable/engine-implementedBehaviours.xml shippable/engine-implementedBehaviours.html

shippable/engine-testcases.xml: engine.richescape shippable
	zenta-xslt-runner -xsl:generate_test_cases.xslt -s engine.richescape outputbase=shippable/engine-

shippable/engine-implementedBehaviours.xml: javadoc shippable
	zenta-xslt-runner -xsl:generate-behaviours.xslt -s target/test/javadoc.xml outputbase=shippable/engine-

javadoc:
	mkdir -p target/production target/test
	CLASSPATH=/usr/local/lib/xml-doclet.jar\
     javadoc -doclet com.github.markusbernhardt.xmldoclet.XmlDoclet -sourcepath src/main/java -d target/production org.rulez.demokracia.PDEngine
	CLASSPATH=/usr/local/lib/xml-doclet.jar\
     javadoc -doclet com.github.markusbernhardt.xmldoclet.XmlDoclet -sourcepath src/test/java -d target/test org.rulez.demokracia.PDEngine

include /usr/share/zenta-tools/model.rules

testenv:
	docker run --rm -p 5900:5900 -v $$(pwd):/pdengine -w /pdengine -it pdengine

javabuild: target/PDEngine-0.0.1-SNAPSHOT.jar

target/PDEngine-0.0.1-SNAPSHOT.jar:
	mvn clean install

clean:
	git clean -fdx
	rm -rf zenta-tools

inputs/engine.issues.xml:
	mkdir -p inputs
	getGithubIssues https://api.github.com label:auto_inconsistency+repo:edemo/PDEngine >inputs/engine.issues.xml

zentaworkaround:
	mkdir -p ~/.zenta/.metadata/.plugins/org.eclipse.e4.workbench/
	cp workbench.xmi ~/.zenta/.metadata/.plugins/org.eclipse.e4.workbench/
	touch zentaworkaround

