export GITHUB_ORGANIZATION=edemo
export SONAR_ORG=edemo
export REPO_NAME=PDEngine
MODEL_BASENAME = engine
JAVA_TARGET = PDEngine-0.0.1-SNAPSHOT.jar
include /usr/local/toolchain/rules.java

maven-build:
	rm /dev/random; cp -a /dev/urandom /dev/random;mvn --debug org.jacoco:jacoco-maven-plugin:prepare-agent install org.pitest:pitest-maven:mutationCoverage site -Pintegration-test

