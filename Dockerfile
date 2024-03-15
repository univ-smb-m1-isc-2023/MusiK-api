FROM amazoncorretto:17-alpine-jdk

# Install Python and other necessary packages
RUN apk update && \
    apk add --no-cache \
    ffmpeg \
    chromaprint \
    && rm -rf /var/cache/apk/*

# COPY .env /

# Create a volume for music files
VOLUME /app/musics

ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]