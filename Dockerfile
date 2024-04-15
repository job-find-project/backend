FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar job-backend.jar
ENTRYPOINT ["java","-jar","/job-backend.jar"]
