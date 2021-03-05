FROM ubuntu:18.04

RUN apt-get update && apt-get install --yes openjdk-8-jre-headless wget mysql-client

RUN wget https://github.com/CC-Exercises/notebookapp/releases/download/v0.3.0/notebookapp-0.3.0.jar

COPY config.yml .

ENV PORT 80
ENV ADMINPORT 8081

EXPOSE $PORT
EXPOSE $ADMINPORT

ENV DBMODE tmp
ENV TPMODE local

COPY startup.sh .
RUN chmod +x startup.sh

CMD ["bash","startup.sh"]