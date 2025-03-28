# Use an official Maven image to build the application
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Build the application using Maven
RUN mvn clean package -Pproduction

# Use a lightweight OpenJDK image for the final application
FROM openjdk:17-jdk-slim

# Set the working directory for the runtime container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/my-app-1.0-SNAPSHOT.jar /app/myapp.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app/myapp.jar"]

