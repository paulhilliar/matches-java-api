#
#FROM gradle:6.6.1-jre11 as builder
#
## Copy local code to the container image.
#WORKDIR /app
#COPY build.gradle .
#COPY gradlew .
#COPY src ./src
#COPY gradle ./gradle

## Build a release artifact.
#RUN gradlew build
#
#FROM adoptopenjdk/openjdk11:alpine-slim
#
## Copy the jar to the production image from the builder stage.
#COPY --from=builder /app/build/libs/papi-*.jar /papi.jar
#
## Run the web service on container startup.
#CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/papi.jar"]


FROM openjdk:11-jdk-alpine
ARG JAR_FILE=JAR_FILE_MUST_BE_SPECIFIED_AS_BUILD_ARG
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Djava.security.edg=file:/dev/./urandom","-jar","/app.jar"]