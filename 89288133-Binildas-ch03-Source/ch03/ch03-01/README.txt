
Microservices using PostgreSQL and Rest Template (Full CRUD)
Browser => ProductWeb μS => ProductServer μS => PostgreSQLDB

Bring up PostgreSQL Server
--------------------------
binildass-MacBook-Pro:~ binil$ pg_ctl -D /Library/PostgreSQL/12/data start

binildass-MacBook-Pro:~ binil$ pg_ctl -D /Library/PostgreSQL/12/data stop

Build the Product Server
------------------------
binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch03/ch03-01/01-ProductServer
binildass-MacBook-Pro:01-ProductServer binil$
binildass-MacBook-Pro:01-ProductServer binil$ sh make.sh 
[INFO] Scanning for projects...
[INFO] 
[INFO] -------< com.acme.ecom.product:Ecom-Product-Server-Microservice >-------
…
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.026 s
[INFO] Finished at: 2024-01-03T12:36:02+05:30
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

2024-01-03 12:37:17 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProductMicroserviceApplication v0.0.1-SNAPSHOT using Java 17.0.7 with PID 5237 (/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch03/ch03-01/01-ProductServer/target/Ecom-Product-Server-Microservice-0.0.1-SNAPSHOT.jar started by binil in /Users/binil/binil/code/mac/mybooks/docker-04/Code/ch03/ch03-01/01-ProductServer)
2024-01-03 12:37:17 DEBUG StartupInfoLogger.logStarting:51 - Running with Spring Boot v3.2.0, Spring v6.1.1
2024-01-03 12:37:17 INFO  SpringApplication.logStartupProfileInfo:653 - No active profile set, falling back to 1 default profile: "default"
2024-01-03 12:37:20 INFO  InitializationComponent.init:47 - Start...
2024-01-03 12:37:20 DEBUG InitializationComponent.init:51 - Deleting all existing data on start...
Hibernate: select pco1_0.categoryid,pco1_0.description,pco1_0.imgurl,pco1_0.name,pco1_0.title from productcategory pco1_0
Hibernate: select po1_0.productid,po1_0.category,po1_0.code,po1_0.prodname,po1_0.price,po1_0.title from product po1_0
2024-01-03 12:37:20 DEBUG InitializationComponent.init:56 - Creating initial data on start...
Hibernate: insert into productcategory (description,imgurl,name,title) values (?,?,?,?)
Hibernate: insert into productcategory (description,imgurl,name,title) values (?,?,?,?)
Hibernate: insert into product (category,code,prodname,price,title) values (?,?,?,?,?)
Hibernate: insert into product (category,code,prodname,price,title) values (?,?,?,?,?)
Hibernate: insert into product (category,code,prodname,price,title) values (?,?,?,?,?)
Hibernate: insert into product (category,code,prodname,price,title) values (?,?,?,?,?)
2024-01-03 12:37:20 INFO  InitializationComponent.init:105 - End
2024-01-03 12:37:21 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductMicroservice...
...

http://localhost:8081/

Build the Product Web
---------------------

binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch03/ch03-01/02-ProductWeb
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
[INFO] Total time:  2.070 s
[INFO] Finished at: 2024-01-03T12:42:06+05:30
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

2024-01-03 12:43:58 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProduct...
...
2024-01-03 12:43:59 INFO  InitializationComponent.init:36 - Start
2024-01-03 12:43:59 DEBUG InitializationComponent.init:38 - Doing Nothing...
2024-01-03 12:43:59 INFO  InitializationComponent.init:40 - End
2024-01-03 12:43:59 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductMicroservice...
...

Test the Client
---------------
Open below link in Chrome

http://localhost:8080/product.html
