# Stage 1: Build the JAR file
FROM maven:3.8.4-openjdk-17 AS builder
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight runtime image
FROM openjdk:17
EXPOSE 8080
ENV JAR_FILE=target/*.jar
COPY --from=builder ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
