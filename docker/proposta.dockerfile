FROM openjdk:11
MAINTAINER Gustavo Tanques
ARG JAR_FILE=target/*jar
COPY ${JAR_FILE} proposta.jar
ENTRYPOINT ["java", "-jar", "/proposta.jar"]