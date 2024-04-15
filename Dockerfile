FROM maven:3.2.4-openjdk-17 AS BUILD
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/job-backend-0.0.1-SNAPSHOT.jar job-backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "job-backend.jar"]