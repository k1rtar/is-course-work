# Сборка
FROM gradle:8.11.1-jdk21 AS build
WORKDIR /app
COPY . /app

ENTRYPOINT ["./gradlew", "bootRun"]

EXPOSE 8080
