# Сборка
FROM gradle:8.11.1-jdk21 AS build
WORKDIR /app
COPY . /app
RUN gradle bootJar

# Запуск
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/auth-service-0.0.1-SNAPSHOT.jar auth-service.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/auth-service.jar"]
