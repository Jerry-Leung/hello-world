# ---- Stage 1: Build ----
# Use a JDK image with Gradle to compile and package the app into a jar.
FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace

# Copy the Gradle wrapper and build config first (better layer caching:
# dependencies are only re-downloaded when these files change).
COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle
RUN chmod +x gradlew && ./gradlew --no-daemon dependencies || true

# Copy the source and build the bootable jar (skip tests for faster image builds).
COPY src ./src
RUN ./gradlew --no-daemon clean bootJar -x test

# ---- Stage 2: Runtime ----
# Use a smaller JRE-only image for the final artifact.
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app

# Run as a non-root user for better security.
RUN useradd --system --uid 1001 spring
USER spring

# Copy only the built jar from the build stage.
COPY --from=build /workspace/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
