pipeline {
	agent any
	tools {
		maven 'Maven 3.8.6'
	}

	stages {
		stage('SonarQube Analysis') {
			steps {
				script {
					withSonarQubeEnv() {
							sh "mvn clean verify sonar:sonar -Dsonar.projectKey=currency-microservices"
					}
				}
			}
		}
	}
}