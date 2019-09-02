export GITHUB_ORGANIZATION=edemo
export SONAR_ORG=edemo
export REPO_NAME=PDEngine
MODEL_BASENAME = engine
JAVA_TARGET = PDEngine-0.0.1-SNAPSHOT.jar
BEFORE_MAVEN_BUILD = entropy
BEFORE_SONAR = publish_tests

include /usr/local/toolchain/rules.java

entropy:
	rm /dev/random; cp -a /dev/urandom /dev/random

publish_tests:
	mvn org.jacoco:jacoco-maven-plugin:report
	cp -r target/site/jacoco shippable/codecoverage
	mkdir -p shippable/testresults
	mv target/surefire-reports/*.xml shippable/testresults
