FROM maven:latest AS build
WORKDIR /app
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean package -DskipTests

FROM openjdk:18
WORKDIR /app
COPY ./target/gateway.jar /app/gateway.jar
COPY ./wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
EXPOSE 9090
CMD ["/wait-for-it.sh", "eureka-server:8761", "--", "java", "-jar", "gateway.jar"]