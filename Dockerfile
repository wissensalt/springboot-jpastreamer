FROM openjdk:11-jdk-slim

ADD target/*.jar sbjs.jar

ENTRYPOINT ["java", "-jar", "sbjs.jar"]