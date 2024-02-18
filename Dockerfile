FROM amazoncorretto:17-alpine-jdk

# Install Python and other necessary packages
RUN apk update && \
    apk add --no-cache \
    python3 \
    py3-pip \
    ffmpeg \
    chromaprint \
    && rm -rf /var/cache/apk/*

# Set environment variables for Python
ENV PYTHONUNBUFFERED=1 \
    PYTHONDONTWRITEBYTECODE=1 \
    PATH="/usr/bin/python3:${PATH}"

# Install the pyacoustid library
RUN apk add --upgrade py3-pyacoustid

# Copy the musics from the ressources folder to the container
COPY src/main/resources/musics /app/musics

# Copy the python script to the container
COPY src/main/resources/fingerprint.py /app/fingerprint.py

ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]