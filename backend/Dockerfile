#FROM maven:3.6-jdk8 as builder
#
#COPY . /builddir
#WORKDIR /builddir
##WORKDIR src/main/resources
##RUN rm application.properties
##RUN cp deploy.properties application.properties
##WORKDIR ../../..
#RUN mvn bootJar

FROM openjdk:8-jre-slim
EXPOSE 8080
COPY target/chat-0.1.jar /app/chat.jar
WORKDIR /app
CMD ["java", "-jar", "chat.jar"]