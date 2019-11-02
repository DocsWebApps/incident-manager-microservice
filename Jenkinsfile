#!/usr/bin/env groovy

node {
    stage('checkout') {
        checkout scm
    }

    stage('clean') {
        sh "chmod +x mvnw"
        sh "./mvnw -s /opt/maven/mvn3/conf/settings.xml clean"
    }

    stage('backend tests') {
        try {
            sh "./mvnw -s /opt/maven/mvn3/conf/settings.xml test"
        } catch(err) {
            throw err
        } finally {
            junit '**/target/surefire-reports/TEST-*.xml'
        }
    }

    stage('packaging') {
        sh "./mvnw -s /opt/maven/mvn3/conf/settings.xml package -DskipTests"
        archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
    }

    stage('sonarqube quality analysis') {
        sh "cp /var/lib/jenkins/sonar_scripts/incident_manager_sonar_dev.bash ."
        sh "./incident_manager_sonar_dev.bash"
    }
}
