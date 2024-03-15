# Back for MusiK

## 1. Prerequisites

You need to have docker installed on your machine as it is used for running the app and the database.

## 2. Launch the application

You will need to do these things in order:

1. Launch docker on your device

2. Build a jar of the application:

   ```
   ./mvnw install
   ```

3. Launch the docker compose file :

   This will create a data folder, in the root of the project, used for the database to save data between docker sessions.

   ```
   docker compose up
   ```

## 3. Update the application:

In order to update the application, do the first two steps of the "Launch application" part.

After that, you will have to do the following steps:

1. Stop docker compose:

   ```
   docker compose down
   ```

2. Remove the spring image to force docker to rebuild the image with the new jar :

   ```
   docker rmi docker-spring-boot-postgres:latest
   ```

3. Relaunch docker compose :

   ```
   docker compose up
   ```