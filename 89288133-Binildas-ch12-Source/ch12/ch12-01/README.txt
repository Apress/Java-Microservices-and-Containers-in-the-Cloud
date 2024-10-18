Build the Application
---------------------

(base) binildass-MacBook-Pro:ch12-01 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch12/ch12-01
(base) binildass-MacBook-Pro:ch12-01 binil$ sh make.sh 
[INFO] Scanning for projects...
[INFO] 
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.569 s
[INFO] Finished at: 2023-05-20T11:17:49+05:30
[INFO] ------------------------------------------------------------------------
(base) binildass-MacBook-Pro:ch12-01 binil$ 

Bring up the Application Serrver
--------------------------------

(base) binildass-MacBook-Pro:ch12-01 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch12/ch12-01
(base) binildass-MacBook-Pro:ch12-01 binil$ sh run.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.0.6)

2023-05-20 11:19:04 INFO  StartupInfoLogger.logStarting:51 - Starting Application v0.0.1-SNAPSHOT ...
2023-05-20 11:19:04 DEBUG StartupInfoLogger.logStarting:52 - Running with Spring Boot v3.0.6...
2023-05-20 11:19:04 INFO  SpringApplication.logStartupProfileInfo:632 - No active profile set...
2023-05-20 11:19:05 INFO  StartupInfoLogger.logStarted:57 - Started Application ...
2023-05-20 11:19:05 INFO  Application.main:50 - Started...
2023-05-20 11:19:10 INFO  Application.home:40 - Start
2023-05-20 11:19:10 DEBUG Application.home:42 - Inside hello.Application.home() : 1
2023-05-20 11:19:10 INFO  Application.home:43 - Returning...

Test the Application
---------------------

(base) binildass-MacBook-Pro:~ binil$ curl http://127.0.0.1:8080/
Hello Docker World : 1
(base) binildass-MacBook-Pro:~ binil$ 

Clean the projects
------------------
(base) binildass-MacBook-Pro:ch12-01 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch12/ch12-01
(base) binildass-MacBook-Pro:ch12-01 binil$ sh clean.sh 
[INFO] Scanning for projects...
[INFO] 
[INFO] ---------< com.acme.ecom.product:spring-boot-docker-k8s-helm >----------
[INFO] Building Spring Boot µS 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:3.2.0:clean (default-clean) @ spring-boot-docker-k8s-helm ---
[INFO] Deleting /Users/binil/binil/code/mac/mybooks/docker-04/Code/ch12/ch12-01/target
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.243 s
[INFO] Finished at: 2023-05-20T11:23:36+05:30
[INFO] ------------------------------------------------------------------------
(base) binildass-MacBook-Pro:ch12-01 binil$ 

