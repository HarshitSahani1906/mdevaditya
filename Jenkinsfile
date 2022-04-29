pipeline {
    agent any
    stages {
        stage ('Compile Stage') {

            steps {
                    bat 'mvn clean compile'
            }
        }
        stage ('Testing Stage') {

            steps {
              catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
 				wrap([$class: 'BuildUser']) { 
                    bat "mvn test -DBROWSER=$BROWSER -DBUILD_USER=$BUILD_USER"
                 }
               }
            }
        post {
        always {
          // publish html
          publishHTML target: [
              allowMissing: false,
              alwaysLinkToLastBuild: true,
              keepAll: true,
              reportDir: 'reports/',
              reportFiles: 'index.html',
              reportName: 'Docs Loadtest Dashboard'
             ]}
            }
        }
    }
    post {
        always {
            step([
				$class: 'Publisher', reportFilenamePattern: '**/testng-results.xml'
				])

        	step([
                $class: 'S3BucketPublisher',
                entries: [[
                    sourceFile: 'reports/index.html',
                    bucket: 'emcollab-jenkins-artifact-bucket',
                    selectedRegion: 'ap-south-1',
                    noUploadOnFailure: false,
                    managedArtifacts: true,
                    flatten: true,
                    showDirectlyInBrowser: true,
                    keepForever: true,
                    ]],
                    profileName: 'emcollab-profile',
                    dontWaitForConcurrentBuildCompletion: false, 
          ])

			    step([
                $class: 'S3BucketPublisher',
                entries: [[
                    sourceFile: 'logs/prints.log',
                    bucket: 'emcollab-jenkins-artifact-bucket',
                    selectedRegion: 'ap-south-1',
                    noUploadOnFailure: false,
                    managedArtifacts: true,
                    flatten: true,
                    showDirectlyInBrowser: true,
                    keepForever: true,
                    ]],
                    profileName: 'emcollab-profile',
                    dontWaitForConcurrentBuildCompletion: false, 
          ])

				mail to: 'harshit@eleve.co.in,kishan@eleve.co.in,ayushi@eleve.co.in',
        subject: "Job $JOB_NAME " ,
				body: "Job - \"${env.JOB_NAME}\" build : ${env.BUILD_NUMBER}\n\nView the log at : ${env.BUILD_URL}\n\nHTML Report: https://emcollab-jenkins-artifact-bucket.s3.ap-south-1.amazonaws.com/jobs/${env.JOB_NAME}/${env.BUILD_NUMBER}/index.html \n\nTestSuite Logs: https://emcollab-jenkins-artifact-bucket.s3.ap-south-1.amazonaws.com/jobs/${env.JOB_NAME}/${env.BUILD_NUMBER}/prints.log"
          }
      }
}