#FROM openjdk:17-jdk
#WORKDIR /app
#ARG JAR_FILE=/build/libs/*-SNAPSHOT.jar
#COPY ${JAR_FILE} /app/app.jar
#COPY src/main/resources/env.properties /app/config/env.properties
#RUN ["/bin/bash", "-c", "apt-get update && apt-get upgrade -y"]
#RUN ["/bin/bash", "-c", "sudo apt install python3-pip"]
#RUN ["/bin/bash", "-c", "sudo pip3 install -U yt-dlp"]
#RUN ["/bin/bash", "-c", "sudo apt install ffmpeg"]
#ENTRYPOINT ["java", "-jar", "app.jar"]

FROM ubuntu:latest

# Install OpenJDK
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk && \
    apt-get clean

# Set the working directory
WORKDIR /app

# Copy the JAR file and resources
COPY build/libs/*.jar /app/app.jar
COPY src/main/resources/env.properties /app/config/env.properties

# Install required packages
RUN apt-get update && \
    apt-get install -y python3 python3-pip ffmpeg && \
    apt-get clean

# Install yt-dlp using pip3
RUN pip3 install -U yt-dlp

# Expose the port
EXPOSE 8080

# Start the Spring Boot application
CMD ["java", "-jar", "/app/app.jar"]