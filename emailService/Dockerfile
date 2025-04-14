FROM maven:latest AS build
WORKDIR /app
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean package -DskipTests

FROM openjdk:18
WORKDIR /app
COPY ./target/emailservice.jar /app/emailservice.jar
COPY ./wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
EXPOSE 8091
CMD ["/wait-for-it.sh", "focus-service:8080", "--", "java", "-jar", "emailservice.jar"]
