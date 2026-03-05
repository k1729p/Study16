@echo off
set KIBANA_VERSION=9.3.0

docker pull docker.elastic.co/kibana/kibana:%KIBANA_VERSION%

docker run ^
 --name kibana ^
 --net elastic ^
 --publish 5601:5601 ^
 docker.elastic.co/kibana/kibana:%KIBANA_VERSION%
pause