
docker run --rm -it -v $(pwd)/ProductWeb:/ProductWebDockerHost -v ~/.m2:/root/.m2 -w /ProductWebDockerHost maven mvn package && docker-compose up

(base) binildass-MBP:ch07-01 binil$ docker run --rm -it -v $(pwd)/ProductWeb:/ProductWebDockerHost -v ~/.m2:/root/.m2 -w /ProductWebDockerHost maven mvn package && docker-compose up
[INFO] Scanning for projects...
...
[INFO] Packaging webapp
[INFO] Assembling webapp [ProductWeb] in [/ProductWebDockerHost/target/ProductWeb]
[INFO] Processing war project
[INFO] Copying webapp resources [/ProductWebDockerHost/src/main/webapp]
[INFO] Webapp assembled in [91 msecs]
[INFO] Building war: /ProductWebDockerHost/target/ProductWeb.war
[INFO] WEB-INF/web.xml already added, skipping
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.949 s
[INFO] Finished at: 2020-12-05T11:58:22Z
[INFO] ------------------------------------------------------------------------
Starting ch07-01_web_1 ... done
Attaching to ch07-01_web_1
...
web_1  | 05-Dec-2020 11:58:25.210 INFO [main] org.apache.catalina.startup.HostConfig.deployWAR Deploying web application archive [/usr/local/tomcat/webapps/ROOT.war]
...
web_1  | 05-Dec-2020 11:58:25.782 INFO [main] org.apache.catalina.startup.Catalina.start Server startup in [686] milliseconds

==========================================================================

http://localhost:8080/

or

(base) binildass-MBP:ch07-01 binil$ curl http://localhost:8080/
<html>
<body>
<h2>Hello World!</h2>
</body>
</html>
(base) binildass-MBP:ch07-01 binil$
