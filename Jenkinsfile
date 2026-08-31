pipeline {
    agent any

    tools {
        // Automatically provisions configured global Maven installation
        maven 'Maven 3.9' 
        jdk 'Java 17'
    }

    options {
        timeout(time: 1, unit: 'HOURS') 
        ansiColor('xterm')
        disableConcurrentBuilds()
    }

    stages {
        stage('Checkout Code Base') {
            steps {
                echo 'Pulling fresh changes from the repository...'
                checkout scm
            }
        }

        stage('Dependency Check & Environment Setup') {
            steps {
                echo 'Validating build profile environment...'
                sh 'mvn --version'
                sh 'java -version'
            }
        }

        stage('Execute Core Hybrid Automation Suite') {
            steps {
                echo 'Triggering Headless Web UI and API Regression tests concurrently...'
                // Running tests using your configured pom.xml and testng.xml file paths
                sh 'mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml'
            }
        }
    }

    post {
        always {
            echo 'Archiving test execution metrics...'
            // Stores your HTML reporting assets so stakeholders can audit build stability
            archiveArtifacts artifacts: 'target/cucumber-reports/**/*, target/surefire-reports/**/*', allowEmptyArchive: true
            
            echo 'Publishing test results into Jenkins dashboard...'
            junit testResults: 'target/surefire-reports/*.xml', allowEmptyArchive: true
        }
        success {
            echo '✅ Deployment Integrity Intact: All automation assertions passed successfully!'
        }
        failure {
            echo '❌ Pipeline Alert: Automated checks caught breaking regressions. Notifying engineering teams...'
        }
    }
}
