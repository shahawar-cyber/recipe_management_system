FROM openjdk:17-jdk-slim
COPY target/your-application.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
