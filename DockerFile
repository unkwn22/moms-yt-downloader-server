FROM openjdk:17-jdk
WORKDIR /app
ARG JAR_FILE=/build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} /app/app.jar
COPY src/main/resources/env.properties /app/config/env.properties
ENTRYPOINT ["java", "-jar", "app.jar"]