@echo off
set ELASTICSEARCH_VERSION=9.3.0

docker container rm --force elasticsearch
docker container rm --force kibana

docker network create elastic

docker pull docker.elastic.co/elasticsearch/elasticsearch:%ELASTICSEARCH_VERSION%

docker run --name elasticsearch ^
 --net elastic ^
 --publish 9200:9200 ^
 --publish 9300:9300 ^
 --env "discovery.type=single-node" ^
 --tty ^
 docker.elastic.co/elasticsearch/elasticsearch:%ELASTICSEARCH_VERSION%
pause