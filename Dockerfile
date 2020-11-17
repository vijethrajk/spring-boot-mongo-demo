FROM openjdk:8
EXPOSE 8080
ADD target/spring-boot-mongo-demo-*.jar spring-boot-mongo-demo.jar
ENTRYPOINT ["java", "-jar", "/spring-boot-mongo-demo.jar"]