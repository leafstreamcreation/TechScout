# Use OpenJDK 21 base image
FROM maven:3.9.10-eclipse-temurin-21-alpine

WORKDIR /app

# Copy pom.xml
COPY pom.xml .

# Copy source code
COPY src ./src

RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
# Build the application
RUN mvn clean install -DskipTests

# Expose port
EXPOSE 8082

# Run the application
CMD ["java", "-jar", "target/voting-service-0.0.1-SNAPSHOT.jar"]
