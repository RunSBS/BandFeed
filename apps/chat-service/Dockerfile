FROM gradle:8.5-jdk21 AS builder
WORKDIR /app
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY common ./common
COPY apps/chat-service ./apps/chat-service
RUN chmod +x gradlew
RUN ./gradlew :apps:chat-service:clean :apps:chat-service:build -x test --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/apps/chat-service/build/libs/*.jar app.jar
EXPOSE 9040
ENTRYPOINT ["java", "-jar", "app.jar"]
