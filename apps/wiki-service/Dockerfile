FROM gradle:8.5-jdk21 AS builder
WORKDIR /app
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY common ./common
COPY apps/wiki-service ./apps/wiki-service
RUN chmod +x gradlew
RUN ./gradlew :apps:wiki-service:clean :apps:wiki-service:build -x test --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/apps/wiki-service/build/libs/*.jar app.jar
EXPOSE 9030
ENTRYPOINT ["java", "-jar", "app.jar"]
