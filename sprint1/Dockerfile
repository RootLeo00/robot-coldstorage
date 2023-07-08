## ----------------------------------------------------------
## Dockerfile for robot-coldstorage
## ----------------------------------------------------------

FROM openjdk:12.0.2
EXPOSE 8020
## ADD extracts the tar
ADD ./build/distributions/robot-coldstorage-1.0.tar /
WORKDIR /robot-coldstorage-1.0/bin
COPY ./*.pl ./
COPY ./*.txt ./

## RUN apt-get update -y
## RUN apt-get install -y wiringpi
## RUN sudo apt-get install -y python

CMD ["bash", "robot-coldstorage"]

## 1) gradlew build -> distTar
## 2) docker build -t basicrobot23:2.0 .
## OCCORRE APRIRE UDP PER PERMETTERE COAP
## docker run -it --rm --name basicrobot23 -p8020:8020/tcp -p8020:8020/udp --privileged basicrobot23:2.0  /bin/bash
## MODIFICA DEL FILE DI CONFIGURAZIONE
# docker cp basicrobotConfig.json 89193dba02a2:/unibo.basicrobot23-2.0/bin/basicrobotConfig.json
# docker cp stepTimeConfig.json cab3aa29f8eb:/unibo.basicrobot23-2.0/bin/basicrobotConfig.json
# docker tag basicrobot23:2.0 natbodocker/basicrobot23:2.0

#type docker_password.txt | docker login --username natbodocker --password-stdin
#docker login --username natbodocker --password xyz
#docker push natbodocker/basicrobot23:2.0