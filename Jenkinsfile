node{

	stage('SCM checkout'){
	git credentialsId: 'git_creds', url: 'https://github.com/vijethrajk/spring-boot-mongo-demo.git'
	}
	
	stage('mvn package'){
	//def mvnHome = tool name: 'maven-3', type: 'maven'
	//	def mvnCmd= "${mvnHome}/bin/mvn"
		sh "mvn clean package"
	}
	
    stage('build docker image'){
		sh "docker build -t vijethrajk/spring-boot-mongo-demo:2.0.0 ."
	}
	
	 stage('push docker image'){
	     	withCredentials([string(credentialsId: 'docker-hub-creds', variable: 'dockerHubPwd')]) {
           sh "docker login -u vijethrajk -p ${dockerHubPwd}"
}
		sh "docker push  vijethrajk/spring-boot-mongo-demo:2.0.0"
	}


}