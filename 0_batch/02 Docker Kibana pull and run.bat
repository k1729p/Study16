@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-21
set M2_HOME=c:\\tools\\apache-maven-3.9.5
set KIBANA_VERSION=8.11.4

docker pull docker.elastic.co/kibana/kibana:%KIBANA_VERSION%

docker run ^
 --name kibana ^
 --net elastic ^
 --publish 5601:5601 ^
 docker.elastic.co/kibana/kibana:%KIBANA_VERSION%
pause