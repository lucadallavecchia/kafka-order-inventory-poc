# Stage 1: Build
FROM gradle:8.5-jdk21 AS build
# IMPORTANTE: Copiamo tutto il progetto (root inclusa)
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Buildiamo solo il modulo specifico usando il path relativo alla root
RUN ./gradlew :order-service:build -x test --no-daemon

# Stage 2: Run
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Preleviamo il jar dal path del modulo
COPY --from=build /home/gradle/src/order-service/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]