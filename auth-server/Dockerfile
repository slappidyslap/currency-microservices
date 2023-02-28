FROM openjdk:17-alpine
ADD target/*.jar auth-server.jar
ENTRYPOINT ["java","-jar","auth-server.jar"]