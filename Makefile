export GITHUB_ORGANIZATION=edemo
export SONAR_ORG=$(GITHUB_ORGANIZATION)
export REPO_NAME=PDEngine
MODEL_BASENAME = engine
JAVA_TARGET = PDEngine-0.0.1-SNAPSHOT.jar
include /usr/local/toolchain/rules.java

