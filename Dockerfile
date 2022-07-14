# Build stage
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Package stage
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /home/app/target/*.jar /app/simple-weblogic-service.jar

# Execution stage
RUN useradd -ms /bin/bash service_user
USER service_user
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "simple-weblogic-service.jar"]