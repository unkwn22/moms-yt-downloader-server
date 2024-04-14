FROM openjdk:17-jdk
WORKDIR /app
ARG JAR_FILE=/build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} /app/app.jar
COPY src/main/resources/env.properties /app/config/env.properties
RUN ["/bin/bash", "-c", "sudo apt update"]
RUN ["/bin/bash", "-c", "sudo apt install python3-pip"]
RUN ["/bin/bash", "-c", "sudo pip3 install -U yt-dlp"]
RUN ["/bin/bash", "-c", "sudo apt install ffmpeg"]
ENTRYPOINT ["java", "-jar", "app.jar"]