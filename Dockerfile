# This DockerFile builds the sources, runs tests and packages up a container that contains the Spring Boot Jar
# Building like this means we can drop the build really easily set up a deploy pipeline into Google Cloud Build/Cloud Run
FROM adoptopenjdk/openjdk8:alpine-slim as builder

# Copy local code to the container image.
WORKDIR /app
COPY build.gradle .
COPY gradlew .
COPY src ./src
COPY gradle ./gradle

# Build a release artifact.
RUN ./gradlew build


# Second stage to build a small image that just contains enough to run the built Spring Boot jar
FROM adoptopenjdk/openjdk8:alpine-slim

# Copy the jar to the production image from the builder stage.
COPY --from=builder /app/build/libs/app-*.jar /app.jar

# Run the Spring Boot service on container startup.
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]

