FROM openjdk:17-jdk
WORKDIR /app
ARG JAR_FILE=/build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} /app/app.jar
COPY src/main/resources/env.properties /app/config/env.properties
RUN apt update
RUN apt install python3-pip
RUN rm -rf /var/lib/apt/lists/*
RUN pip3 install -U yt-dlp
RUN apt install ffmpeg
ENTRYPOINT ["java", "-jar", "app.jar"]