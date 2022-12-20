pipeline {
	agent any
	stages {
		stage ('Build Backend') {
			steps {
				sh 'mvn clean package -DskipTests=true'
			}
		}
		stage ('Unit Tests') {
			steps {
				sh 'mvn test'
			}
		}
		stage ('Deploy Backend') {
			steps {
				deploy adapters: [tomcat8(credentialsId: 'tomcat_login2', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
			}
		}
		stage ('Deploy Frontend') {
			steps {
				dir ('Frontend') {
					git 'https://github.com/hayanngs/tasks-frontend'
					sh 'mvn clean package'
					deploy adapters: [tomcat8(credentialsId: 'tomcat_login2', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks', war: 'target/tasks.war'
				}
			}
		}
	}
}
