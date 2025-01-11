# Сборка
FROM gradle:8.11.1-jdk21 AS build
WORKDIR /app
COPY task-service /app
COPY common /common
ENTRYPOINT ["./gradlew", "bootRun"]

EXPOSE 8083

