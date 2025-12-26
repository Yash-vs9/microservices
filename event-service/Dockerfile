# 1. Use an official Java runtime as a parent image
FROM eclipse-temurin:17-jdk-alpine

# 2. Set the working directory inside the container
WORKDIR /app

# 3. Copy the compiled jar file into the container
# We use a wildcard (*.jar) so you don't have to worry about version numbers
COPY target/*.jar app.jar

# 4. Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]