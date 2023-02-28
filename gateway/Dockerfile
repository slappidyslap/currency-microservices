FROM openjdk:17-alpine
ADD target/*.jar gateway.jar
ENTRYPOINT ["java","-jar","gateway.jar"]