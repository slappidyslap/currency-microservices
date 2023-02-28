FROM openjdk:17-alpine
ADD target/*.jar eureka-server.jar
ENTRYPOINT ["java","-jar","eureka-server.jar"]