@echo off
:: API key created in Kibana:
set ELASTICSEARCH_API_KEY=LXJyV1I1d0JURHFBSGRqWUxLUE46RGZxMWlMWnNoLUVyX0l5UVZtTktvQQ==
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-25
set M2_HOME=c:\tools\apache-maven
pushd %cd%
cd ..
call %M2_HOME%\bin\mvn clean install exec:java -Dexec.args="%ELASTICSEARCH_API_KEY%"
pause
popd
