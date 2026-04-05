#FROM ubuntu:latest
#LABEL authors="ankit"
#
#ENTRYPOINT ["top", "-b"]
#FROM maven:3.9.6-eclipse-temurin-17 AS build
#WORKDIR /app
#COPY . .
#RUN mvn clean package -DskipTests
#
## Stage 2: Run
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/InternAssignment-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 10000

ENTRYPOINT ["java","-jar","app.jar"]


#FROM openjdk:21-jdk-slim
#
#WORKDIR /app
#
#COPY target/*.jar app.jar
#
#EXPOSE 10000
#
#ENTRYPOINT ["java", "-jar", "app.jar"]