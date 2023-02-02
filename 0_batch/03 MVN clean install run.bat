@echo off
:: password taken from 'elasticsearch' Docker Container run console output:
set ELASTIC_PASSWORD=erXg9VFE7n3u7r6PxSu9
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-19
set M2_HOME=c:\tools\apache-maven-3.8.5
pushd %cd%
cd ..
call %M2_HOME%\bin\mvn clean install exec:java  -Dexec.args="%ELASTIC_PASSWORD%"
pause
popd