# Use a base image with Java 17 installed
FROM adoptopenjdk/openjdk17:alpine-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY target/your-application.jar /app/recipie_management_system-0.0.1-SNAPSHOT.jar.original

# Expose the port that the Spring Boot application will listen on
EXPOSE 8080

# Command to run the Spring Boot application when the container starts
CMD ["java", "-jar", "recipie_management_system-0.0.1-SNAPSHOT.jar.original"]
