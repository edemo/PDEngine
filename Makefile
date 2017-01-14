all: install

install: compile
	mkdir -p shippable
	cp -rf engine/* target/* engine-testcases.xml engine-testcases.txt shippable

compile: zentaworkaround javabuild engine.compiled engine-testcases.xml javadoc

engine-testcases.xml: engine.richescape
	zenta-xslt-runner -xsl:generate_test_cases.xslt -s engine.richescape outputbase=engine-

javadoc:
	mvn javadoc:javadoc
	mvn javadoc:test-javadoc

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

