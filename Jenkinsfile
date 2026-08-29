pipeline {
    agent any

    tools {
        maven 'Maven_3.9.x' // Must match your Jenkins Global Tool Configuration name
        jdk 'Java_17'
    }

    parameters {
        string(name: 'BROWSER', defaultValue: 'chrome-headless', description: 'Browser environment for test execution')
        string(name: 'SUITE', defaultValue: 'src/test/resources/features', description: 'Path to Cucumber feature files')
    }

    stages {
        stage('Checkout Code') {
            steps {
                cleanWs()
                checkout scm
            }
        }

        stage('Execute API & UI Tests') {
            steps {
                script {
                    // Runs RestAssured and Selenium via Cucumber Runner in parallel
                    sh "mvn clean test -Dbrowser=${params.BROWSER} -Dcucumber.features=${params.SUITE}"
                }
            }
        }
    }

    post {
        always {
            // Generates beautiful interactive reports even if tests fail
            allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
        }
        success {
            echo '🎉 All API and UI regression tests passed successfully!'
        }
        failure {
            echo '❌ Regression Suite Failed. Please check the Allure Report artifacts.'
            // Place your Slack/Email webhook notifications here
        }
    }
}
