all: install

install: compile sonar shippable
	cp -rf engine/* target/* shippable

shippable:
	mkdir -p shippable

sonar: sonarconfig javabuild
	mvn sonar:sonar -Dsonar.organization=edemo -Dsonar.externalIssuesReportPaths=issuesReport.json
	./tools/pullanalize

sonarconfig:
	cp etc/m2/settings.xml ~/.m2

compile: zentaworkaround javabuild engine.compiled codedocs

codedocs: shippable/engine-testcases.xml shippable/engine-implementedBehaviours.xml shippable/engine-implementedBehaviours.html shippable/bugpriorities.xml

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

CONSISTENCY_INPUTS=shippable/engine-testcases.xml shippable/engine-implementedBehaviours.xml

include /usr/share/zenta-tools/model.rules

engine.consistencycheck: engine.rich engine.check $(CONSISTENCY_INPUTS)
	zenta-xslt-runner -xsl:xslt/consistencycheck.xslt -s:$(basename $@).check -o:$@ >$(basename $@).consistency.stderr 2>&1
	sed 's/\//:/' <$(basename $@).consistency.stderr |sort --field-separator=':' --key=2

testenv:
	./tools/testenv

javabuild: target/PDEngine-0.0.1-SNAPSHOT.jar

target/PDEngine-0.0.1-SNAPSHOT.jar:
	mvn build-helper:parse-version versions:set versions:commit -DnewVersion=\$${parsedVersion.majorVersion}.\$${parsedVersion.minorVersion}.\$${parsedVersion.incrementalVersion}-$$(tools/getbranch|sed 'sA/A_Ag').$$(git rev-parse --short HEAD)
	mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install org.pitest:pitest-maven:mutationCoverage
	java -jar /usr/local/lib/mutation-analysis-plugin-1.3-SNAPSHOT.jar

clean:
	git clean -fdx
	rm -rf zenta-tools

inputs/engine.issues.xml: shippable/engine-implementedBehaviours.xml shippable/engine-testcases.xml
	mkdir -p inputs
	tools/getGithubIssues edemo PDEngine f279765590d25bedfc9f08f7fc39a8c39c891711 >inputs/engine.issues.xml

zentaworkaround:
	mkdir -p ~/.zenta/.metadata/.plugins/org.eclipse.e4.workbench/
	cp workbench.xmi ~/.zenta/.metadata/.plugins/org.eclipse.e4.workbench/
	touch zentaworkaround

shippable/bugpriorities.xml: engine.consistencycheck inputs/engine.issues.xml engine.richescape shippable
	zenta-xslt-runner -xsl:issue-priorities.xslt -s:engine.consistencycheck -o:shippable/bugpriorities.xml issuesfile=inputs/engine.issues.xml modelfile=engine.richescape missingissuefile=shippable/missing.xml

