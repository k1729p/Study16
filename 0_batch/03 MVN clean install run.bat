@echo off
:: password taken from 'elasticsearch' Docker Container run console output:
set ELASTIC_PASSWORD=M24iJoTfWw=VTx8aUrp8
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-21
set M2_HOME=c:\\tools\\apache-maven-3.9.5
pushd %cd%
cd ..
call %M2_HOME%\bin\mvn clean install exec:java  -Dexec.args="%ELASTIC_PASSWORD%"
pause
popd