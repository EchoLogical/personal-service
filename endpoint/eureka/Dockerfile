# Dockerfile for Eureka server
# Build context: project root; ensure the JAR is built (mvn -pl endpoint/eureka -am package)

# Use a small, secure JRE base image
FROM eclipse-temurin:17-jre

# Arguments and environment
ARG JAR_FILE=eureka-1.0.jar
ENV JAVA_OPTS=""
ENV SPRING_PROFILES_ACTIVE=default

# Working directory inside the image
WORKDIR /app

# Copy the Spring Boot fat jar
COPY ${JAR_FILE} /app/app.jar

# Expose Eureka server port
EXPOSE 8761

# Run the application
# You can pass extra JVM args with JAVA_OPTS and switch Spring profile via SPRING_PROFILES_ACTIVE
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar /app/app.jar"]
