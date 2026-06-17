FROM gradle:8.5-jdk21 AS builder
WORKDIR /app
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY apps/config-server ./apps/config-server
RUN chmod +x gradlew
RUN ./gradlew :apps:config-server:clean :apps:config-server:build -x test --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/apps/config-server/build/libs/*.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]
