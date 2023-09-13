# Stage 1: Build the JAR file
FROM maven:3.9.4-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight runtime image
FROM openjdk:17
EXPOSE 8080
ENV JAR_FILE=target/your-jar-file.jar
COPY --from=builder /app/${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
