FROM openjdk:17-jdk
WORKDIR /app
ARG JAR_FILE=/build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} /app/app.jar
COPY src/main/resources/env.properties /app/config/env.properties
RUN sudo apt update && \
    sudo apt install python3-pip && \
    sudo rm -rf /var/lib/apt/lists/* \
RUN sudo pip3 install -U yt-dlp
RUN sudo apt install ffmpeg
ENTRYPOINT ["java", "-jar", "app.jar"]