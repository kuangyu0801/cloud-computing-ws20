FROM ubuntu:18.04

RUN apt-get update && apt-get install --yes openjdk-8-jre-headless wget

RUN wget https://github.com/CC-Exercises/textprocessor-service/releases/download/v0.2.0/SimpleTextProcessor-0.2.0.jar 
RUN wget https://github.com/CC-Exercises/textprocessor-service/releases/download/v0.2.0/config.yml

ENV PORT 80
ENV ADMINPORT 8081

EXPOSE $PORT
EXPOSE $ADMINPORT

CMD ["java","-jar","SimpleTextProcessor-0.2.0.jar","server","config.yml"]