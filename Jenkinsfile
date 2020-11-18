node{
        def SHORT_COMMIT_ID = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
        def COMMIT_ID = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()

        def NAME   = "vijethrajk/spring-boot-mongo-demo"
        def BUILD   = "${env.BUILD_ID}"
        def IMG   = "${NAME}:${COMMIT_ID}"
        def LATEST = "${NAME}:latest"

	stage('SCM checkout'){
	    git credentialsId: 'git_creds', url: 'https://github.com/vijethrajk/spring-boot-mongo-demo.git'
	}
	
	stage('mvn package'){
		//def mvnHome = tool name: 'maven-3', type: 'maven'
		//def mvnCmd= "${mvnHome}/bin/mvn"
		sh "mvn clean package"
	}
	
	stage('Test script') {
        echo "current build number: ${currentBuild.number}"
        echo "Env buildId : ${env.BUILD_ID}"
        echo "Git commit short ID: ${SHORT_COMMIT_ID}"
        echo "Git commit  ID: ${COMMIT_ID}"
    }
        
    stage('Build docker image'){
		sh "docker build -t ${IMG} ."
	}
	
	stage('Push to docker repository'){
	     withCredentials([string(credentialsId: 'docker-hub-creds', variable: 'dockerHubPwd')]) {
           sh "docker login -u vijethrajk -p ${dockerHubPwd}"
		 }
		 
		 sh "docker push  ${IMG}"
	}
	
}