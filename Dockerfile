FROM openjdk:21-jdk

COPY ./build/libs/mood_backend-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java","-Dspring.profiles.active=prod", "-jar", "/app.jar"]