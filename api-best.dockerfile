FROM maven:3.8.1-openjdk-11
LABEL maintainer="Gabriel Gonoring Borges"
COPY ./best-developers-backend /opt
WORKDIR /opt
RUN mvn clean package
ENTRYPOINT java -jar target/*.jar