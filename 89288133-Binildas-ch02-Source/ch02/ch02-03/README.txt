HATEOAS Microservices using MongoDB and Rest Template (Full CRUD)
Browser => ProductWeb μS => ProductServer μS => MongoDB

Start MongoDB
-------------
mongod --dbpath /usr/local/var/mongodb --logpath /usr/local/var/log/mongodb/mongo.log

Build the Product Server
------------------------

(base) binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch02/ch02-03/01-ProductServer
(base) binildass-MacBook-Pro:01-ProductServer binil$ sh make.sh 
[INFO] Scanning for projects...
[INFO] 
[INFO] -------< com.acme.ecom.product:Ecom-Product-Server-Microservice >-------
[INFO] Building Ecom-Product-Server-Microservice 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.868 s
[INFO] Finished at: 2023-12-20T10:32:48+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:01-ProductServer binil$

Run the Product Server
----------------------

binildass-MacBook-Pro:01-ProductServer binil$ sh run.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2023-12-20 10:33:43 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProduct...
2023-12-20 10:33:43 DEBUG StartupInfoLogger.logStarting:51 - Running with Spring ...
2023-12-20 10:33:43 INFO  SpringApplication.logStartupProfileInfo:653 - No active ...
2023-12-20 10:33:45 INFO  InitializationComponent.init:42 - Start
2023-12-20 10:33:45 INFO  InitializationComponent.init:62 - End
2023-12-20 10:33:45 INFO  StartupInfoLogger.logStarted:56 - Started EcomProduct...

Build the Product Web
---------------------

(base) binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch02/ch02-03/02-ProductWeb
(base) binildass-MacBook-Pro:02-ProductWeb binil$ sh make.sh 
[INFO] Scanning for projects...
[INFO] 
[INFO] --------< com.acme.ecom.product:Ecom-Product-Web-Microservice >---------
[INFO] Building Ecom-Product-Web-Microservice 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.953 s
[INFO] Finished at: 2023-12-20T10:36:01+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:02-ProductWeb binil$ 

Run the Product Web
---------------------

binildass-MacBook-Pro:02-ProductWeb binil$ sh run.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2023-12-20 10:36:28 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProduct...
2023-12-20 10:36:28 DEBUG StartupInfoLogger.logStarting:51 - Running with Spring ...
2023-12-20 10:36:28 INFO  SpringApplication.logStartupProfileInfo:653 - No active ...
2023-12-20 10:36:29 INFO  InitializationComponent.init:37 - Start
2023-12-20 10:36:29 DEBUG InitializationComponent.init:39 - Doing Nothing...
2023-12-20 10:36:29 INFO  InitializationComponent.init:41 - End
2023-12-20 10:36:29 INFO  StartupInfoLogger.logStarted:56 - Started EcomProduct...

Test the Client
---------------
Open below link in Chrome

http://localhost:8080/product.html

HAL Browser
http://localhost:8081

