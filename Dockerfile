FROM maven:3.8.5-openjdk-17-slim
ENV NASA_KEY = $NASA_KEY
ENV BASE_URL = $BASE_URL
MAINTAINER Luis Barrientos luisen.barrientos@gmail.com
RUN apt-get update
RUN apt-get install -y git
RUN git clone git@github.com:luisenb/SDET-Backend.git
ENTRYPOINT ["java", "-jar", "/app.jar"]
RUN mvn clean test