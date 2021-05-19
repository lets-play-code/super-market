pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh './gradlew clean test'
                sh "./gradlew sonarqube -Dsonar.projectKey=${this.BRANCH_NAME} -Dsonar.host.url=http://host.docker.internal:9000 -Dsonar.login=f2a90ee8056d203a53e88538fb8dfc2c595af521"
                script {
                    testJobs.runTests(this, testcases.supermarket())
                }
            }
        }
    }
}

