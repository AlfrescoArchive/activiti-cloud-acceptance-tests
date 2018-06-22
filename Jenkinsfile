pipeline {
    agent {
      label "jenkins-maven"
    }
    environment {
      ORG               = 'ryandawsonuk'
      APP_NAME          = 'activiti-cloud-acceptance-tests'
    }
    stages {
      stage('CI Build and push snapshot') {
        when {
          branch 'PR-*'
        }
        environment {
          PREVIEW_VERSION = "0.0.0-SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER"
          PREVIEW_NAMESPACE = "$APP_NAME-$BRANCH_NAME".toLowerCase()
          HELM_RELEASE = "$PREVIEW_NAMESPACE".toLowerCase()
        }
        steps {
          container('maven') {
            sh "mvn versions:set -DnewVersion=$PREVIEW_VERSION"
            sh "mvn install"
          }
        }
      }
      stage('Build Release') {
        when {
          branch 'develop'
        }
        withEnv(["GOPATH=/ws","PATH=/ws/bin:${env.PATH}"]) {
            sh 'bash build.sh'
        }
        steps {
          container('maven') {
            // ensure we're not on a detached head
            sh "git checkout develop"
            sh "git config --global credential.helper store"
            sh "jx step validate --min-jx-version 1.1.73"
            sh "jx step git credentials"
          }
          container('maven') {
            sh '''
              export GATEWAY_HOST=gw.jx-staging.activiti.envalfresco.com
              export SSO_HOST=jx-staging-quickstart-http.jx-staging.activiti.envalfresco.com
              export REALM=activiti
              mvn -pl \'!modeling-acceptance-tests,!apps-acceptance-tests\' clean verify
              '''

          }
        }
      }
    }
    post {
        always {
            cleanWs()
        }
        failure {
            input """Pipeline failed.
We will keep the build pod around to help you diagnose any failures.

Select Proceed or Abort to terminate the build pod"""
        }
    }
  }
