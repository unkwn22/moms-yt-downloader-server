FROM openjdk:17-jdk
WORKDIR /app
ARG JAR_FILE=/build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} /app/app.jar
COPY src/main/resources/env.properties /app/config/env.properties
RUN apk update && \
    apk add --no-cache \
    python3 \
    py3-pip \
    ffmpeg

RUN pip3 install -U yt-dlp
ENTRYPOINT ["java", "-jar", "app.jar"]