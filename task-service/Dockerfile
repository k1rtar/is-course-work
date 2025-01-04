# Сборка
FROM gradle:8.11.1-jdk21 AS build
WORKDIR /app
COPY . /app
RUN gradle bootJar

# Запуск
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/task-service-0.0.1-SNAPSHOT.jar task-service.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/task-service.jar"]
