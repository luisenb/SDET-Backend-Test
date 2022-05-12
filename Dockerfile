FROM alpine as build
ARG SSH_PRIVATE_KEY
ARG BASE_URL
ARG NASA_KEY
ARG MAVEN_VERSION=3.6.3
ARG USER_HOME_DIR="/root"
ARG BASE_URL_MAVEN=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

ENV SSH_PRIVATE_KEY=${SSH_PRIVATE_KEY}
ENV BASE_URL=${BASE_URL}
ENV NASA_KEY=${NASA_KEY}

MAINTAINER Luis Barrientos luisen.barrientos@gmail.com

RUN apk --update --no-cache add openjdk8 curl

RUN mkdir -p /usr/share/maven /usr/share/maven/ref
RUN curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL_MAVEN}/apache-maven-${MAVEN_VERSION}-bin.tar.gz
RUN tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1
RUN rm -f /tmp/apache-maven.tar.gz
RUN ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

RUN  apk add git
RUN apk add openssh

# add credentials on build
RUN mkdir -p /root/.ssh
RUN echo "${SSH_PRIVATE_KEY}" > /root/.ssh/id_rsa
RUN chmod 600 /root/.ssh/id_rsa
#Add Github to known hosts
RUN touch /root/.ssh/known_hosts
RUN ssh-keyscan github.com >> /root/.ssh/known_hosts
RUN git clone git@github.com:luisenb/SDET-Backend.git