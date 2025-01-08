FROM gradle:8.11.1-jdk21 AS build
WORKDIR /app
COPY /auth-service /app
COPY common /common

RUN rm -rf /src
ENTRYPOINT ["./gradlew", "bootRun"]

EXPOSE 8081
