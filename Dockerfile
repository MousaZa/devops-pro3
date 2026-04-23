# Use a base image that matches the Java version you just used locally.
# You were using Java 25, so we will use an Eclipse Temurin Java 25 image.
FROM eclipse-temurin:25-jre

# Create a volume for temporary files (Spring Boot uses this for embedded Tomcat)
VOLUME /tmp

# Tell Docker to look in your build/libs folder for the JAR
ARG JAR_FILE=build/libs/*.jar

# Copy that JAR into the container and rename it to app.jar
COPY ${JAR_FILE} app.jar

# Expose the standard Spring Boot port
EXPOSE 8080

# The command that runs when the container starts
ENTRYPOINT ["java","-jar","/app.jar"]