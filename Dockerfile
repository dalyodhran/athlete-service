# =========================
# 1) Build stage
# =========================
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copy build files first for better Docker cache
COPY gradlew ./
COPY gradle gradle
COPY build.gradle settings.gradle ./
# If using Maven instead of Gradle, use:
# COPY pom.xml ./

# Make sure wrapper is executable
RUN chmod +x gradlew

# Download dependencies (will be cached)
RUN ./gradlew dependencies --no-daemon
# If using Maven:
# RUN mvn -B dependency:resolve

# Copy the rest of the source
COPY src src

# Build the Spring Boot fat jar
RUN ./gradlew bootJar --no-daemon
# If using Maven:
# RUN mvn -B package -DskipTests

# =========================
# 2) Runtime stage
# =========================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the fat jar from the build stage
# Adjust the path/jar name if needed
COPY --from=build /app/build/libs/*.jar app.jar
# For Maven:
# COPY --from=build /app/target/*.jar app.jar

# Expose internal port (Spring Boot default)
EXPOSE 8080

# JVM tuning flags are optional, tweak as you like
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "/app/app.jar"]
