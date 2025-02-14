FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY ./target/DockerApp.jar .

EXPOSE 8080

CMD ["java", "-jar", "DockerApp.jar"]