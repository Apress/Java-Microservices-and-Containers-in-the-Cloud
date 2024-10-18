Microservices using MongoDB and Rest Template (Full CRUD)
Browser => ProductWeb μS => ProductServer μS => MongoDB

Start MongoDB
-------------
(base) binildass-MBP:bin binil$ pwd
/Users/binil/Applns/mongodb/mongodb-macos-x86_64-4.2.8/bin
(base) binildass-MBP:bin binil$ mongod --dbpath /usr/local/var/mongodb --logpath /usr/local/var/log/mongodb/mongo.log

Build Product Server
--------------------

(base) binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch02/ch02-02/01-ProductServer
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
[INFO] Total time:  1.959 s
[INFO] Finished at: 2023-12-20T09:26:12+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:01-ProductServer binil$ 

Run Product Server
------------------

(base) binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch02/ch02-02/01-ProductServer
binildass-MacBook-Pro:01-ProductServer binil$ sh run.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2023-12-20 09:27:59 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProduct...
2023-12-20 09:27:59 DEBUG StartupInfoLogger.logStarting:51 - Running with Spring Boot ...
2023-12-20 09:27:59 INFO  SpringApplication.logStartupProfileInfo:653 - No active ...
2023-12-20 09:28:00 INFO  InitializationComponent.init:45 - Start
2023-12-20 09:28:01 INFO  InitializationComponent.init:67 - End
2023-12-20 09:28:01 INFO  StartupInfoLogger.logStarted:56 - Started EcomProduct...

Build Product Web
-----------------

(base) binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch02/ch02-02/02-ProductWeb
(base) binildass-MacBook-Pro:02-ProductWeb binil$ sh make.sh 
[INFO] Scanning for projects...
[INFO] 
[INFO] --------< com.acme.ecom.product:Ecom-Product-Web-Microservice >---------
[INFO] Building Ecom-Product-Web-Microservice 0.0.1-SNAPSHOT
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.035 s
[INFO] Finished at: 2023-12-20T09:26:18+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:02-ProductWeb binil$ 

Run Product Web
---------------

(base) binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch02/ch02-02/02-ProductWeb
binildass-MacBook-Pro:02-ProductWeb binil$ sh run.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2023-12-20 09:31:58 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProduct...
2023-12-20 09:31:58 DEBUG StartupInfoLogger.logStarting:51 - Running with Spring Boot ...
2023-12-20 09:31:58 INFO  SpringApplication.logStartupProfileInfo:653 - No active ...
2023-12-20 09:32:00 INFO  InitializationComponent.init:37 - Start
2023-12-20 09:32:00 DEBUG InitializationComponent.init:39 - Doing Nothing...
2023-12-20 09:32:00 INFO  InitializationComponent.init:41 - End
2023-12-20 09:32:00 INFO  StartupInfoLogger.logStarted:56 - Started EcomProduct...

Test the Client
---------------

Open below link in Chrome
http://localhost:8080/product.html

If you need to build both Servers together
------------------------------------------

binildass-MacBook-Pro:ch02-02 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch02/ch02-02
binildass-MacBook-Pro:ch02-02 binil$ sh make.sh 
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
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  1.813 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.540 s]
[INFO] Ecom ............................................... SUCCESS [  0.022 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.569 s
[INFO] Finished at: 2023-12-20T09:40:52+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:ch02-02 binil$ 

Clean the projects
------------------

(base) binildass-MacBook-Pro:ch02-02 binil$ sh clean.sh 
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
...
[INFO] 
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  0.084 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.012 s]
[INFO] Ecom ............................................... SUCCESS [  0.024 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.304 s
[INFO] Finished at: 2023-12-20T09:41:53+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:ch02-02 binil$ 


