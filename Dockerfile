# Use Java 11 base image as Render uses Java 11 in Singapore region
FROM eclipse-temurin:11-jdk-alpine

# Create app directory
WORKDIR /app

# Copy jar from target folder
COPY target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
