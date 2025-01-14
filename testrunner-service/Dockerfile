# Сборка
FROM gradle:8.11.1-jdk21 AS build
WORKDIR /app
COPY testrunner-service /app
COPY common /common
RUN apt-get update && apt-get install -y python3 python3-pip g++
ENTRYPOINT ["./gradlew", "bootRun"]

EXPOSE 8090

