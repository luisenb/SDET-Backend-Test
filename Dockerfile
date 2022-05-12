FROM maven:3.8.5-openjdk-17-slim
ARG SSH_PRIVATE_KEY
ARG BASE_URL
ARG NASA_KEY
MAINTAINER Luis Barrientos luisen.barrientos@gmail.com
RUN apt-get update
RUN apt-get install -y git
# add credentials on build
RUN mkdir /root/.ssh/
RUN echo "${SSH_PRIVATE_KEY}" > /root/.ssh/id_rsa

RUN git clone git@github.com:luisenb/SDET-Backend.git
ENTRYPOINT ["java", "-jar", "/app.jar"]
RUN mvn clean test