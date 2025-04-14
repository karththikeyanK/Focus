FROM maven:latest AS build
WORKDIR /app
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean package -DskipTests

FROM openjdk:18
WORKDIR /app
COPY ./target/eureka-server.jar /app/eureka-server.jar
EXPOSE 8761
CMD ["java", "-jar", "/app/eureka-server.jar"]
