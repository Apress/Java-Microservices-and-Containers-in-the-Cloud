Microservices in Docker using PostgreSQL in Docker and Rest Template (Full CRUD)
Browser => ProductWeb μS => ProductServer μS => PostgreSQLDB

********************** Docker in Minikube ********************************
==========================================================================

Build & package Microservices, build images, and run containers
---------------------------------------------------------------
(base) binildass-MacBook-Pro:ch08-04 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch08/ch08-04
(base) binildass-MacBook-Pro:ch08-04 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch08-04 binil$ sh makeandrun.sh 
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
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  2.928 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.678 s]
[INFO] Ecom ............................................... SUCCESS [  0.018 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.823 s
[INFO] Finished at: 2023-05-17T21:53:57+05:30
[INFO] ------------------------------------------------------------------------
...
Successfully tagged ecom/product-server:latest
Successfully tagged ecom/product-web:latest
(base) binildass-MacBook-Pro:ch08-04 binil$ 

List running containers
-----------------------
(base) binildass-MacBook-Pro:~ binil$ docker ps
CONTAINER ID   IMAGE                                      COMMAND                  CREATED              STATUS              PORTS                    NAMES
91a02d64b246   ecom/product-web                           "java -jar /ecom.jar"    About a minute ago   Up About a minute   0.0.0.0:8080->8080/tcp   product-web
82b9b71a3083   ecom/product-server                        "java -jar /ecom.jar"    About a minute ago   Up About a minute   0.0.0.0:8081->8081/tcp   product-server
bfd3516c9a75   postgres:15.3-alpine3.18   
...
(base) binildass-MacBook-Pro:~ binil$

View Product Server Logs
------------------------
(base) binildass-MacBook-Pro:01-ProductServer binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:01-ProductServer binil$ docker logs --follow 82b9b71a3083

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2023-05-17 16:24:07 INFO  StartupInfoLogger.logStarting:51 - Starting EcomProductMicroservice...
...
2023-05-17 16:24:20 INFO  InitializationComponent.init:42 - Start
2023-05-17 16:24:20 DEBUG InitializationComponent.init:46 - Deleting all products ...
2023-05-17 16:24:20 DEBUG InitializationComponent.init:63 - Creating 2 new products ...
Hibernate: insert into product (code, prodname, price, title) values (?, ?, ?, ?)
Hibernate: insert into product (code, prodname, price, title) values (?, ?, ?, ?)
2023-05-17 16:24:20 INFO  InitializationComponent.init:68 - End
2023-05-17 16:24:21 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroservice...


View Product Web Logs
---------------------
(base) binildass-MacBook-Pro:02-ProductWeb binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:02-ProductWeb binil$ docker logs --follow 91a02d64b246

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2023-05-17 16:24:07 INFO  StartupInfoLogger.logStarting:51 - Starting EcomProductMicroservice...
...
2023-05-17 16:24:13 INFO  InitializationComponent.init:36 - Start
2023-05-17 16:24:13 DEBUG InitializationComponent.init:38 - Doing Nothing...
2023-05-17 16:24:13 INFO  InitializationComponent.init:40 - End
2023-05-17 16:24:15 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroservice...

Test Microservice
-----------------
(base) binildass-MacBook-Pro:~ binil$ minikube ip
192.168.64.6
(base) binildass-MacBook-Pro:~ binil$ 

http://192.168.64.6:8080/product.html

Interact with PostgreSQL Docker Container using psql
----------------------------------------------------

(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ docker exec -it postgres-docker bash
bash-5.1# psql -U postgres
psql (9.6.24)
Type "help" for help.

postgres=# \list
                                 List of databases
   Name    |  Owner   | Encoding |  Collate   |   Ctype    |   Access privileges   
-----------+----------+----------+------------+------------+-----------------------
 postgres  | postgres | UTF8     | en_US.utf8 | en_US.utf8 | 
 productdb | postgres | UTF8     | en_US.utf8 | en_US.utf8 | 
 template0 | postgres | UTF8     | en_US.utf8 | en_US.utf8 | =c/postgres          +
           |          |          |            |            | postgres=CTc/postgres
 template1 | postgres | UTF8     | en_US.utf8 | en_US.utf8 | =c/postgres          +
           |          |          |            |            | postgres=CTc/postgres
(4 rows)

postgres=# \conninfo
You are connected to database "postgres" as user "postgres" via socket in "/var/run/postgresql" at port "5432".
postgres=# \c productdb
You are now connected to database "productdb" as user "postgres".
productdb=# \conninfo
You are connected to database "productdb" as user "postgres" via socket in "/var/run/postgresql" at port "5432".
productdb=# select * from product;
 productid |  prodname   |     code      |                   title                    | price 
-----------+-------------+---------------+--------------------------------------------+-------
         1 | Kamsung D3  | KAMSUNG-TRIOS | Kamsung Trios 12 inch , black , 12 px .... | 12000
         2 | Lokia Pomia | LOKIA-POMIA   | Lokia 12 inch , white , 14px ....          |  9000
(2 rows)

productdb=# select * from product;
 productid |    prodname    |     code      |                   title                    | price 
-----------+----------------+---------------+--------------------------------------------+-------
         1 | Kamsung D3     | KAMSUNG-TRIOS | Kamsung Trios 12 inch , black , 12 px .... | 12000
         2 | Lokia Pomia    | LOKIA-POMIA   | Lokia 12 inch , white , 14px ....          |  9000
         3 | Fourth Product | FOURTH        | Fourth Product for Testing                 |    44
(3 rows)

productdb=# \q
bash-5.1# exit
exit
(base) binildass-MacBook-Pro:~ binil$ 


Clean the Environment
---------------------
(base) binildass-MacBook-Pro:ch08-04 binil$ sh clean.sh 
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
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  0.099 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.011 s]
[INFO] Ecom ............................................... SUCCESS [  0.026 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.338 s
[INFO] Finished at: 2023-05-17T21:59:06+05:30
[INFO] ------------------------------------------------------------------------
product-web
product-server
postgres-docker
product-web
product-server
postgres-docker
Untagged: ecom/product-web:latest
Untagged: ecom/product-server:latest
ecom-network
(base) binildass-MacBook-Pro:ch08-04 binil$ 


