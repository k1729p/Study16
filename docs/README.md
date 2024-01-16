<!DOCTYPE html>
<HTML lang="en">
<META charset="UTF-8">
<BODY>
<a href="https://github.com/k1729p/Study16/tree/main/docs"><img alt="" src="images/ColorScheme.png" height="25" width="800"/></a>
<H2 id="contents">Study16 README Contents</H2>
<h3 id="top">Research the <a href="https://www.elastic.co/">Elasticsearch</a> with the <a href="https://www.elastic.co/kibana/">Kibana</a></h3>

<p><img alt="" src="images/MermaidFlowchart.png" height="230" width="500"/><br>
<img alt="" src="images/blackArrowUp.png">
<i>The flowchart with Elasticsearch and Kibana.</i></p>

<p>The guides:</p>
<ul>
<li><a href="https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/index.html">Elasticsearch Java API Client</a></li>
<li><a href="https://www.elastic.co/guide/en/kibana/8.5/console-kibana.html">Kibana</a></li>
</ul>

<p>The sections of this project:</p>
<ol>
<li><a href="#ONE">Elasticsearch and Kibana Launch</A></li>
<li><a href="#TWO">Elasticsearch Client Queries</A></li>
<li><a href="#THREE">Kibana Console Queries</A></li>
</ol>

<p>Java source code:<br>
<img alt="" src="images/aquaHR-500.png"><br>
<img alt="" src="images/aquaSquare.png"> <a href="https://github.com/k1729p/Study16/tree/main/src/main/java/kp">kp</a><br>
<img alt="" src="images/aquaHR-500.png"></p>

<P><img alt="" src="images/MermaidClassDiagram.png" height="290" width="190"/><br>
<img alt="" src="images/blackArrowUp.png">
<I>The model object 
<a href="https://github.com/k1729p/Study16/blob/main/src/main/java/kp/model/Box.java">'Box'</a> class diagram.</I></P>

<p>
<img alt="" src="images/yellowHR-500.png"><br>
<img alt="" src="images/yellowSquare.png">
    <a href="http://htmlpreview.github.io/?https://github.com/k1729p/Study16/blob/main/docs/apidocs/index.html">
	Java API Documentation</a><br>
<img alt="" src="images/yellowHR-500.png">
</p>

<a href="#top">Back to the top of the page</a>
<hr>
<H2 id="ONE">❶ Elasticsearch and Kibana Launch</H2>

<p>Action:<br>
<img alt="" src="images/orangeHR-500.png"><br>
<img alt="" src="images/orangeSquare.png"> 1. With the batch file 
<a href="https://github.com/k1729p/Study16/blob/main/0_batch/01%20Docker%20Elastic%20pull%20and%20run.bat">
'01 Docker Elastic pull and run.bat'</a> start the <b>Docker</b> container with <b>Elasticsearch</b><br>
<img alt="" src="images/orangeSquare.png"> 2. With the batch file 
<a href="https://github.com/k1729p/Study16/blob/main/0_batch/02%20Docker%20Kibana%20pull%20and%20run.bat">
'02 Docker Kibana pull and run.bat'</a> start the <b>Docker</b> container with <b>Kibana</b><br>
<img alt="" src="images/orangeSquare.png"> 3. Take the enrolment token for <b>Kibana</b> from the console log (in the <b>Docker</b> 'elasticsearch' container).<br>
<img alt="" src="images/orangeSquare.png"> 4. Take the password from the console log (in the <b>Docker</b> 'elasticsearch' container).<br>
<img alt="" src="images/orangeSquare.png"> 5. With this password and user 'elastic' log in to <b>Kibana</b> 
<a href="http://localhost:5601/app/home#/">'http://localhost:5601/app/home#/'</a>.<br>
<img alt="" src="images/orangeSquare.png"> 6. With this password replace the <i>'ELASTIC_PASSWORD'</i> value in the file 
<a href="https://github.com/k1729p/Study16/blob/main/0_batch/03%20MVN%20clean%20install%20run.bat">'03 MVN clean install run.bat'</a>.<br>
<img alt="" src="images/orangeSquare.png"> 7. With the command <i>'cat config/certs/http_ca.crt'</i> 
get the certificate content from the 'elasticsearch' container.<br>
<img alt="" src="images/orangeSquare.png"> 8. With that certificate replace the content of the certificate file 
<a href="https://github.com/k1729p/Study16/blob/main/src/main/resources/http_ca.crt">'resources\http_ca.crt'</a>.<br>
<img alt="" src="images/orangeHR-500.png"></p>

<P><img alt="" src="images/greenCircle.png">
1.1. The Docker containers.
</P>
<P>
<img alt="" src="images/ScreenshotDockerContainer.png" height="280" width="890"/><br>
<img alt="" src="images/blackArrowUp.png">
<I>The screenshot of the created Docker containers.</I>
</P>

<a href="#top">Back to the top of the page</a>
<hr>
<H2 id="TWO">❷ Elasticsearch Client Queries</H2>

<p>Action:<br>
<img alt="" src="images/orangeHR-500.png"><br>
<img alt="" src="images/orangeSquare.png"> 1. With the batch file 
<a href="https://github.com/k1729p/Study16/blob/main/0_batch/03%20MVN%20clean%20install%20run.bat">
'03 MVN clean install run.bat'</a> build and run the 
<a href="https://github.com/k1729p/Study16/blob/main/src/main/java/kp/Application.java">'Application'</a>.<br>
<img alt="" src="images/orangeHR-500.png"></p>


<p><img alt="" src="images/greenCircle.png"> 
2.1. The big file <a href="https://github.com/k1729p/Study15/blob/main/solr-requests/dataset.json">'<b>dataset.json</b>'</a> (about 800 KB)
was created in Solr project <a href="https://github.com/k1729p/Study15/">'Study15'</a>. 
In that creation process were scanned the files from the <a href="#PROJECTS">'<b>Projects</b>'</a>.
Those files were filtered with the <a href="#KEYWORDS">'<b>Keywords</b>'</a>.
</p>
<p> In the method 
<a href="https://github.com/k1729p/Study16/blob/main/src/main/java/kp/Application.java#L37">'Application::main'</a>
the application for the elastic search client:
</p>
<ul>
<li>recreates the index with the name 'kp_study'</li>
<li>adds the <a href="https://github.com/k1729p/Study16/blob/main/src/main/java/kp/model/Box.java">'Box'</a> 
objects from the file '<b>dataset.json</b>'</li>
<li>searches with the query</li>
</ul>

<p>
The method for recreating the index: 
<a href="https://github.com/k1729p/Study16/blob/main/src/main/java/kp/service/KpService.java#L102">'KpService::recreateIndex'</a>.<br>
The method for creating and adding the 
<a href="https://github.com/k1729p/Study16/blob/main/src/main/java/kp/model/Box.java">'Box'</a> objects: 
<a href="https://github.com/k1729p/Study16/blob/main/src/main/java/kp/service/KpService.java#L119">'KpService::addBoxesFromFile'</a>.<br>
The method for searching with the query: 
<a href="https://github.com/k1729p/Study16/blob/main/src/main/java/kp/service/KpService.java#L147">'KpService::searchWithQuery'</a>.
</p>

<p>
The <a href="images/ScreenshotApplication.png">screenshot</a> of the console log from the run of the 
<a href="https://github.com/k1729p/Study16/blob/main/src/main/java/kp/Application.java">'Application'</a>.
</p>

<a href="#top">Back to the top of the page</a>
<hr>
<H2 id="THREE">❸ Kibana Console Queries</H2>

<table style="border:solid">
<tbody>
<tr>
<td style="border:solid">First Query</td>
<td style="border:solid"><code>GET kp_study</code></td>
</tr><tr>
<td style="border:solid">Second Query</td>
<td style="border:solid"><code>GET kp_study/_search
{
&nbsp;"query" : {
&nbsp;&nbsp;"match" : {
&nbsp;&nbsp;&nbsp;"keyword" : "sonar" 
&nbsp;&nbsp;}
&nbsp;}
}</code></td>
</tr>
</tbody></table>

<p>Action:<br>
<img alt="" src="images/orangeHR-500.png"><br>
<img alt="" src="images/orangeSquare.png"> 1. With the URL 
<a href="http://localhost:5601/app/home#/">'http://localhost:5601/app/home#/'</a> log in to the <b>Kibana</b>.<br>
<img alt="" src="images/orangeSquare.png"> 2. From the left side menu select the item "Management" / "Dev Tools".<br>
<img alt="" src="images/orangeSquare.png"> 3. Run in the Kibana console the first query and the second query.<br>
<img alt="" src="images/orangeHR-500.png"></p>


<p><img alt="" src="images/greenCircle.png"> 
3.1. The results of the first query.<br>
<img alt="" src="images/ScreenshotKibanaFirstQuery.png" height="495" width="650"/><br>
<img alt="" src="images/blackArrowUp.png">
<i>The screenshot of the results fragment in the Kibana console.</i>
</p>

<p><img alt="" src="images/greenCircle.png"> 
3.2. The results of the second query.<br>
The <a href="images/ScreenshotKibanaSecondQuery.png">screenshot</a> of the results in the Kibana console.
</p>

<a href="#top">Back to the top of the page</a>
<hr>
<H2>Data Visualization in Kibana</H2>
<table style="border:solid">
<tbody>
<tr>
<td style="border:solid">Pie Chart</td>
<td style="border:solid"><a href="images/VisualizationKpPieChart.png">screenshot</a></td>
</tr><tr>
<td style="border:solid">Tree Map</td>
<td style="border:solid"><a href="images/VisualizationKpTreeMap.png ">screenshot</a></td>
</tr><tr>
<td style="border:solid">Bar Vertical Stacked</td>
<td style="border:solid"><a href="images/VisualizationKpBarVerticalStacked.png">screenshot</a></td>
</tr><tr>
<td style="border:solid">Bar Horizontal</td>
<td style="border:solid"><a href="images/VisualizationKpBarHorizontal.png">screenshot</a></td>
</tr>
</tbody></table>

<hr>
<H2 id="PROJECTS">Projects</H2>
<table style="border:solid">
<tbody>
<tr>
<td style="border:solid">Miscellany</td>
<td style="border:solid">&nbsp;</td>
<td style="border:solid">&nbsp;</td>
<td style="border:solid">&nbsp;</td>
<td style="border:solid">&nbsp;</td>
</tr><tr>
<td style="border:solid">Study01</td>
<td style="border:solid">Study02</td>
<td style="border:solid">Study03</td>
<td style="border:solid">Study04</td>
<td style="border:solid">Study05</td>
</tr><tr>
<td style="border:solid">Study06</td>
<td style="border:solid">Study07</td>
<td style="border:solid">Study08</td>
<td style="border:solid">Study09</td>
<td style="border:solid">Study10</td>
</tr><tr>
<td style="border:solid">Study11</td>
<td style="border:solid">Study12</td>
<td style="border:solid">Study13</td>
<td style="border:solid">Study14</td>
<td style="border:solid">&nbsp;</td>
</tr><tr>
<td style="border:solid">&nbsp;</td>
<td style="border:solid">Study17</td>
<td style="border:solid">Study18</td>
<td style="border:solid">Study19</td>
<td style="border:solid">Study20</td>
</tr>
</tbody></table>
<hr>
<H2 id="KEYWORDS">Keywords</H2>
<table style="border:solid">
<tbody>
<tr>
<td style="border:solid">@component</td>
<td style="border:solid">@controller</td>
<td style="border:solid">@document</td>
<td style="border:solid">@ejb</td>
<td style="border:solid">@entity</td>
<td style="border:solid">@getmapping</td>
<td style="border:solid">@inject</td>
<td style="border:solid">@messagedriven</td>
</tr><tr>
<td style="border:solid">@mockbean</td>
<td style="border:solid">@namedquery</td>
<td style="border:solid">@postmapping</td>
<td style="border:solid">@restcontroller</td>
<td style="border:solid">@springbootapplication</td>
<td style="border:solid">@springboottest</td>
<td style="border:solid">@stateless</td>
<td style="border:solid">@test</td>
</tr><tr>
<td style="border:solid">@transactional</td>
<td style="border:solid">@webmvctest</td>
<td style="border:solid">activemq</td>
<td style="border:solid">angularjs</td>
<td style="border:solid">asynchronous</td>
<td style="border:solid">bayes</td>
<td style="border:solid">camunda</td>
<td style="border:solid">cdi</td>
</tr><tr>
<td style="border:solid">checksum</td>
<td style="border:solid">circuitbreaker</td>
<td style="border:solid">collator</td>
<td style="border:solid">comparable</td>
<td style="border:solid">comparator</td>
<td style="border:solid">completablefuture</td>
<td style="border:solid">credentials</td>
<td style="border:solid">criteriaquery</td>
</tr><tr>
<td style="border:solid">cryptography</td>
<td style="border:solid">curl</td>
<td style="border:solid">cyclicbarrier</td>
<td style="border:solid">database</td>
<td style="border:solid">decrypt</td>
<td style="border:solid">docker</td>
<td style="border:solid">ellipticcurvecryptography</td>
<td style="border:solid">encrypt</td>
</tr><tr>
<td style="border:solid">entitymanager</td>
<td style="border:solid">entitymodel</td>
<td style="border:solid">flux</td>
<td style="border:solid">hateoas</td>
<td style="border:solid">jaxb</td>
<td style="border:solid">jboss</td>
<td style="border:solid">jpa</td>
<td style="border:solid">jquery</td>
</tr><tr>
<td style="border:solid">jsf</td>
<td style="border:solid">kafka</td>
<td style="border:solid">mapstruct</td>
<td style="border:solid">mockito</td>
<td style="border:solid">mockmvc</td>
<td style="border:solid">mongodb</td>
<td style="border:solid">mono</td>
<td style="border:solid">openapi</td>
</tr><tr>
<td style="border:solid">orchestration</td>
<td style="border:solid">patriciatrie</td>
<td style="border:solid">postgresql</td>
<td style="border:solid">reactive</td>
<td style="border:solid">reactivemongorepository</td>
<td style="border:solid">redis</td>
<td style="border:solid">restful</td>
<td style="border:solid">resttemplate</td>
</tr><tr>
<td style="border:solid">securerandom</td>
<td style="border:solid">soap</td>
<td style="border:solid">sonar</td>
<td style="border:solid">sonarqube</td>
<td style="border:solid">spotlight</td>
<td style="border:solid">spring</td>
<td style="border:solid">sslsocket</td>
<td style="border:solid">swagger</td>
</tr><tr>
<td style="border:solid">teeing</td>
<td style="border:solid">testresttemplate</td>
<td style="border:solid">thymeleaf</td>
<td style="border:solid">webclient</td>
<td style="border:solid">webflux</td>
<td style="border:solid">weld</td>
<td style="border:solid">wildfly</td>
<td style="border:solid">zoneddatetime</td>
</tr>
</tbody></table>

<a href="#top">Back to the top of the page</a>
<hr>
</BODY>
</HTML>