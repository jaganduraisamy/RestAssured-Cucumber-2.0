pipeline {
    agent any

    stages {
        stage ('Clean Stage') {

            steps {
                withMaven(maven : 'maven-3_6_2') {
                    sh 'mvn clean'
                }
            }
        }

        stage ('Testing Stage') {

            steps {
                withMaven(maven : 'maven-3_6_2') {
                    sh 'mvn test'
                }
            }
        }

        stage ('Cucumber Reports') {

            steps {
                cucumber buildStatus: "UNSTABLE",
                    fileIncludePattern: "**/cucumber-report.json",
                    jsonReportDirectory: 'target'

            }
        }
    }
}