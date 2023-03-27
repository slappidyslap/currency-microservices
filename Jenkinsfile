pipeline {
	agent any
	stages {
		stage('Test') {
			steps {
				echo 'Hello World'
			}
		}
		stage('SonarQube Analysis') {
			steps {
				script {
					def mvn = tool 'Default Maven';
					withSonarQubeEnv() {
							sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=currency-microservices"
					}
				}
			}
		}
	}
}