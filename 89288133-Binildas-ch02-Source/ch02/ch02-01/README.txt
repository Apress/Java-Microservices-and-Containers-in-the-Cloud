Microservices using MongoDB and Rest Template (Full CRUD)
Browser => ProductWeb μS => ProductServer μS => MongoDB

Start MongoDB
-------------
(base) binildass-MBP:bin binil$ pwd
/Users/binil/Applns/mongodb/mongodb-macos-x86_64-4.2.8/bin
(base) binildass-MBP:bin binil$ mongod --dbpath /usr/local/var/mongodb --logpath /usr/local/var/log/mongodb/mongo.log

Build and Run Product Server
----------------------------

(base) binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch02/ch02-01/01-ProductServer
binildass-MacBook-Pro:01-ProductServer binil$ sh make.sh 
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
[INFO] Total time:  2.136 s
[INFO] Finished at: 2023-12-20T08:18:18+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:01-ProductServer binil$ 

binildass-MacBook-Pro:01-ProductServer binil$ sh run.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2023-12-20 08:21:18 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProduct...
2023-12-20 08:21:18 DEBUG StartupInfoLogger.logStarting:51 - Running with Spring Boot v3.2.0, ...
2023-12-20 08:21:18 INFO  SpringApplication.logStartupProfileInfo:653 - No active ...
2023-12-20 08:21:19 INFO  InitializationComponent.init:45 - Start
2023-12-20 08:21:19 INFO  InitializationComponent.init:67 - End
2023-12-20 08:21:20 INFO  StartupInfoLogger.logStarted:56 - Started EcomProduct...

Build and Run Product Web
-------------------------

(base) binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch02/ch02-01/02-ProductWeb
binildass-MacBook-Pro:02-ProductWeb binil$ sh make.sh 
[INFO] Scanning for projects...
[INFO] 
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.903 s
[INFO] Finished at: 2023-12-20T08:22:52+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:02-ProductWeb binil$ 

binildass-MacBook-Pro:02-ProductWeb binil$ sh run.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2023-12-20 08:23:46 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProduct...
2023-12-20 08:23:46 DEBUG StartupInfoLogger.logStarting:51 - Running with Spring Boot v3.2.0, ...
2023-12-20 08:23:46 INFO  SpringApplication.logStartupProfileInfo:653 - No active ...
2023-12-20 08:23:47 INFO  InitializationComponent.init:37 - Start
2023-12-20 08:23:47 DEBUG InitializationComponent.init:39 - Doing Nothing...
2023-12-20 08:23:47 INFO  InitializationComponent.init:41 - End
2023-12-20 08:23:47 INFO  StartupInfoLogger.logStarted:56 - Started EcomProduct...

Test the Client
---------------
Open below link in Chrome
http://localhost:8080/product.html

If you need to build both Servers together
------------------------------------------
(base) binildass-MacBook-Pro:ch02-01 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch02/ch02-01
binildass-MacBook-Pro:ch02-01 binil$ sh make.sh 
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO] 
[INFO] Ecom-Product-Server-Microservice                                   [jar]
[INFO] Ecom-Product-Web-Microservice                                      [jar]
[INFO] Ecom                                                               [pom]
[INFO] 
[INFO] -------< com.acme.ecom.product:Ecom-Product-Server-Microservice >-------
[INFO] Building Ecom-Product-Server-Microservice 0.0.1-SNAPSHOT           [1/3]
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
...
[INFO] 
[INFO] --------< com.acme.ecom.product:Ecom-Product-Web-Microservice >---------
[INFO] Building Ecom-Product-Web-Microservice 0.0.1-SNAPSHOT              [2/3]
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
...
[INFO] 
[INFO] ----------< com.acme.ecom.product:Ecom-Product-Microservice >-----------
[INFO] Building Ecom 0.0.1-SNAPSHOT                                       [3/3]
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ Ecom-Product-Microservice ---
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for Ecom 0.0.1-SNAPSHOT:
[INFO] 
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  1.834 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.425 s]
[INFO] Ecom ............................................... SUCCESS [  0.040 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.491 s
[INFO] Finished at: 2023-12-20T08:26:08+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:ch02-01 binil$ 

Clean the projects
------------------
(base) binildass-MacBook-Pro:ch02-01 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch02/ch02-01
binildass-MacBook-Pro:ch02-01 binil$ sh clean.sh 
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO] 
[INFO] Ecom-Product-Server-Microservice                                   [jar]
[INFO] Ecom-Product-Web-Microservice                                      [jar]
[INFO] Ecom                                                               [pom]
[INFO] 
...
[INFO] 
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  0.079 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.010 s]
[INFO] Ecom ............................................... SUCCESS [  0.024 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.302 s
[INFO] Finished at: 2023-12-20T08:28:04+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:ch02-01 binil$ 

