# Стадия сборки (Gradle)
FROM gradle:8.11.1-jdk21 AS build
WORKDIR /app
COPY . /app
RUN gradle bootJar

# Стадия рантайма (минимальный JRE)
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/api-gateway.jar"]
