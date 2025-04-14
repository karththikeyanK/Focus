FROM maven:latest AS build
WORKDIR /app
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean package -DskipTests

FROM openjdk:18
WORKDIR /app
COPY ./target/focus-service.jar /app/focus-service.jar
COPY ./wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
EXPOSE 8080
CMD ["/wait-for-it.sh", "api-gateway:9090", "--", "java", "-jar", "focus-service.jar"]