FROM openjdk:17-alpine
ADD target/*.jar config-server.jar
ENTRYPOINT ["java","-jar","config-server.jar"]