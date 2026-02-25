# Build stage
FROM eclipse-temurin:25-jdk-alpine AS build
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew build --no-daemon -x test

# Package stage
FROM eclipse-temurin:25-jdk-alpine
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
