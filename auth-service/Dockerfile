FROM gradle:8.11.1-jdk21 AS build
WORKDIR /app
COPY . /app
RUN rm -rf /src
ENTRYPOINT ["./gradlew", "bootRun"]

EXPOSE 8081
