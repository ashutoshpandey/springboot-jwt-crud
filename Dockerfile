# Use an OpenJDK base image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the target directory to the container
COPY target/springsecurity.jar .

# Expose the port the Spring Boot app will listen on
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "springsecurity.jar"]
