all: install

install: compile sonar shippable
	cp -rf engine/* target/* shippable

shippable:
	mkdir -p shippable

sonar: sonarconfig buildreports
	./tools/pullanalize

sonarconfig:
	cp etc/m2/settings.xml ~/.m2

compile: zentaworkaround javabuild engine.compiled codedocs

codedocs: shippable/engine-testcases.xml shippable/engine-implementedBehaviours.xml shippable/engine-implementedBehaviours.html shippable/bugpriorities.xml

shippable/engine-testcases.xml: engine.richescape shippable
	zenta-xslt-runner -xsl:generate_test_cases.xslt -s engine.richescape outputbase=shippable/engine-

shippable/engine-implementedBehaviours.xml: buildreports shippable
	zenta-xslt-runner -xsl:generate-behaviours.xslt -s target/test/javadoc.xml outputbase=shippable/engine-

CONSISTENCY_INPUTS=shippable/engine-testcases.xml shippable/engine-implementedBehaviours.xml

include /usr/share/zenta-tools/model.rules

engine.consistencycheck: engine.rich engine.check $(CONSISTENCY_INPUTS)
	zenta-xslt-runner -xsl:xslt/consistencycheck.xslt -s:$(basename $@).check -o:$@ >$(basename $@).consistency.stderr 2>&1
	sed 's/\//:/' <$(basename $@).consistency.stderr |sort --field-separator=':' --key=2

testenv:
	./tools/testenv

javabuild: maven buildreports
	touch javabuild

maven: target/PDEngine-0.0.1-SNAPSHOT.jar javadoc


javadoc:
	mkdir -p target/production target/test
	JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64 mvn javadoc:javadoc javadoc:test-javadoc site

target/PDEngine-0.0.1-SNAPSHOT.jar: maven-prepare keystore maven-build

maven-prepare:
	mvn build-helper:parse-version versions:set versions:commit -DnewVersion=\$${parsedVersion.majorVersion}.\$${parsedVersion.minorVersion}.\$${parsedVersion.incrementalVersion}-$$(tools/getbranch|sed 'sA/A_Ag').$$(git rev-parse --short HEAD)
	mvn clean 

maven-build:
	mvn org.jacoco:jacoco-maven-plugin:prepare-agent install org.pitest:pitest-maven:mutationCoverage site -Pintegration-test

buildreports: maven
	zenta-xslt-runner -xsl:cpd2pmd.xslt -s:target/pmd.xml -o target/pmd_full.xml
	ls -l ~/.m2/repository/org/slf4j/slf4j-api/1.7.24/slf4j-api-1.7.24.jar ~/.m2/repository/org/slf4j/slf4j-simple/1.7.24/slf4j-simple-1.7.24.jar
	java -cp ~/.m2/repository/org/slf4j/slf4j-api/1.7.24/slf4j-api-1.7.24.jar:~/.m2/repository/org/slf4j/slf4j-simple/1.7.24/slf4j-simple-1.7.24.jar:/usr/local/lib/mutation-analysis-plugin-1.3-SNAPSHOT.jar ch.devcon5.sonar.plugins.mutationanalysis.StandaloneAnalysis

clean:
	git clean -fdx
	rm -rf zenta-tools xml-doclet

inputs/engine.issues.xml: shippable/engine-implementedBehaviours.xml shippable/engine-testcases.xml
	mkdir -p inputs
	tools/getGithubIssues edemo PDEngine >inputs/engine.issues.xml

zentaworkaround:
	mkdir -p ~/.zenta/.metadata/.plugins/org.eclipse.e4.workbench/
	cp workbench.xmi ~/.zenta/.metadata/.plugins/org.eclipse.e4.workbench/
	touch zentaworkaround

shippable/bugpriorities.xml: engine.consistencycheck inputs/engine.issues.xml engine.richescape shippable
	zenta-xslt-runner -xsl:issue-priorities.xslt -s:engine.consistencycheck -o:shippable/bugpriorities.xml issuesfile=inputs/engine.issues.xml modelfile=engine.richescape missingissuefile=shippable/missing.xml

keystore:
	./tools/generate_keystore

