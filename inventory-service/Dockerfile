# Stage 1: Build
# Usiamo l'immagine Gradle per compilare il progetto partendo dalla root
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# Copiamo tutti i file del progetto (perché servono settings.gradle e i moduli comuni)
COPY --chown=gradle:gradle . .

# Buildiamo specificamente il modulo inventory-service
# -x test serve per velocizzare il build nella POC saltando i test
RUN ./gradlew :inventory-service:build -x test --no-daemon

# Stage 2: Run
# Usiamo un'immagine JRE leggera per l'esecuzione
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiamo il JAR generato dal modulo specifico nello stage di build
COPY --from=build /app/inventory-service/build/libs/*.jar app.jar

# Espone la porta (coerente con il docker-compose 8081)
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]