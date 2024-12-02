# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files to the container
COPY . .

# Build the application, clean old files and skip tests
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17.0.1-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage to the runtime stage
COPY --from=build /app/target/HerbalJeevan-0.0.1-SNAPSHOT.jar HerbalJeevan.jar

# Expose the application's port
EXPOSE 8080

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "HerbalJeevan.jar"]
