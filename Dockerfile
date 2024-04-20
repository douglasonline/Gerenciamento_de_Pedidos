FROM openjdk:21-ea-23-jdk-slim

WORKDIR /app

COPY target/order_management-0.0.1-SNAPSHOT.jar /app/order_management-0.0.1-SNAPSHOT.jar

RUN apt-get update && apt-get install -y postgresql-client

ENV DB_URL jdbc:postgresql://postgres-service:5432/order_management
ENV DB_USERNAME postgres
ENV DB_PASSWORD 1234

EXPOSE 8080

CMD ["java", "-jar", "order_management-0.0.1-SNAPSHOT.jar"]