pipeline {
    agent any
    tools {
        maven 'Maven 3.3.9'
    }
    stages {
        stage ('Build Application') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'cookbook_db_password', passwordVariable: 'password', usernameVariable: 'username')]) {
                    sh 'mvn verify -DDB_PASSWORD=${password}'
                }
            }
        }

        stage("Deploy Application"){
            steps{
                sh 'rm /var/www/services/cookbook/*'
                sh 'tar -C /var/www/services/cookbook -zxvf target/cookbook*.tar.gz'
                sh 'sudo /var/www/services/cookbook/installer.sh'
            }
        }

        stage('Clean workspace') {
            steps {
                cleanWs()
            }
        }
    }
}
