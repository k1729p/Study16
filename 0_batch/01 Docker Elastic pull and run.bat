@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-19
set M2_HOME=c:\tools\apache-maven-3.8.5
set ELASTICSEARCH_VERSION=8.5.2

docker container rm --force elasticsearch
docker container rm --force kibana

docker network create elastic

docker pull docker.elastic.co/elasticsearch/elasticsearch:%ELASTICSEARCH_VERSION%

docker run --name elasticsearch ^
 --net elastic ^
 -p 9200:9200 ^
 -p 9300:9300 ^
 -e "discovery.type=single-node" ^
 -t ^
 docker.elastic.co/elasticsearch/elasticsearch:%ELASTICSEARCH_VERSION%
pause
