
Microservices using MongoDB and Rest Template (Full CRUD)
Browser => ProductWeb μS => ProductServer μS => MongoDB

Start MongoDB
-------------
mongod --dbpath /usr/local/var/mongodb --logpath /usr/local/var/log/mongodb/mongo.log

Build the Product Server
------------------------
binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch03/ch03-02/01-ProductServer
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
[INFO] Total time:  3.653 s
[INFO] Finished at: 2024-01-03T13:11:52+05:30
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

2024-01-03 13:13:59 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProductMicroservic...
...
2024-01-03 13:14:01 INFO  InitializationComponent.init:47 - Start...
2024-01-03 13:14:01 DEBUG InitializationComponent.init:51 - Deleting all existing data on start...
2024-01-03 13:14:01 DEBUG InitializationComponent.init:56 - Creating initial data on start...
2024-01-03 13:14:01 INFO  InitializationComponent.init:105 - End
2024-01-03 13:14:01 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductMicroservice...
...

Build the Product Web
---------------------

binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch03/ch03-02/02-ProductWeb
binildass-MacBook-Pro:02-ProductWeb binil$ sh make.sh 
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
[INFO] Total time:  2.342 s
[INFO] Finished at: 2024-01-03T13:15:46+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:02-ProductWeb binil$


Run the Product Web
--------------------

binildass-MacBook-Pro:02-ProductWeb binil$ sh run.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-01-03 13:17:09 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProductMicroservice...
...
2024-01-03 13:17:10 INFO  InitializationComponent.init:37 - Start
2024-01-03 13:17:10 DEBUG InitializationComponent.init:39 - Doing Nothing...
2024-01-03 13:17:10 INFO  InitializationComponent.init:41 - End
2024-01-03 13:17:10 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductMicroservice...
...

Test the Client
---------------
Open below link in Chrome

http://localhost:8080/product.html
