FROM gradle:8.5-jdk21 AS builder
WORKDIR /app
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY common ./common
COPY apps/band-service ./apps/band-service
RUN chmod +x gradlew
RUN ./gradlew :apps:band-service:clean :apps:band-service:build -x test --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/apps/band-service/build/libs/*.jar app.jar
EXPOSE 9020
ENTRYPOINT ["java", "-jar", "app.jar"]
