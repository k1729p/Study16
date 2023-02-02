@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-19
set M2_HOME=c:\tools\apache-maven-3.8.5
set KIBANA_VERSION=8.5.2

docker pull docker.elastic.co/kibana/kibana:%KIBANA_VERSION%

docker run ^
 --name kibana ^
 --net elastic ^
 -p 5601:5601 ^
 docker.elastic.co/kibana/kibana:%KIBANA_VERSION%
pause