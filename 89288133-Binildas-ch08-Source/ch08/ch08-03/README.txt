Microservices in Docker using PostgreSQL in Local and Rest Template (Full CRUD)
Browser => ProductWeb μS => ProductServer μS => PostgreSQLDB

Find IP Address:
----------------
(base) binildass-MBP:12 binil$ ipconfig getifaddr en0
192.168.29.96
(base) binildass-MBP:12 binil$ 

Allow Postgres to listen to all addresses, not just to localhost
----------------------------------------------------------------
In /Library/PostgreSQL/12/data/postgresql.conf in my mac
Change the following line: 
listen_addresses = 'localhost'
to
listen_addresses = '*'

File pg_hba.conf
----------------
In this file (inside /Library/PostgreSQL/12/data/ in my mac), add the following line

host	all	all	172.17.0.1/16	md5
host    all all 192.168.29.96/32  md5

Bring up PostgreSQL Server
--------------------------
binildass-MacBook-Pro:~ binil$ pg_ctl -D /Library/PostgreSQL/12/data start

binildass-MacBook-Pro:~ binil$ pg_ctl -D /Library/PostgreSQL/12/data stop

********************** Docker in Minikube ********************************
==========================================================================
Build & package Microservices, build images, and run containers
---------------------------------------------------------------
(base) binildass-MacBook-Pro:ch08-03 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch08/ch08-03
(base) binildass-MacBook-Pro:ch08-03 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch08-03 binil$ sh makeandrun.sh 
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
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  3.103 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.625 s]
[INFO] Ecom ............................................... SUCCESS [  0.041 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.928 s
[INFO] Finished at: 2023-05-17T20:32:28+05:30
[INFO] ------------------------------------------------------------------------
...
Successfully tagged ecom/product-server:latest
Successfully tagged ecom/product-web:latest
5913bac290f0035e679d449103a1559164991f7c484f41ba2e30bbe10c286d22
11bac3f7c0e17ef421cbf2392207977615a1a16fdd9109b6ed791698fa1e4aa8
0b5c7e62ebc268611ba9c725ad78a7f6afe96b9a007341190d807cbab0990593
(base) binildass-MacBook-Pro:ch08-03 binil$ 

List running containers
-----------------------
(base) binildass-MacBook-Pro:~ binil$ docker ps
CONTAINER ID   IMAGE                                      COMMAND                  CREATED          STATUS          PORTS                    NAMES
0b5c7e62ebc2   ecom/product-web                           "java -jar /ecom.jar"    21 minutes ago   Up 21 minutes   0.0.0.0:8080->8080/tcp   product-web
11bac3f7c0e1   ecom/product-server                        "java -jar /ecom.jar"    21 minutes ago   Up 21 minutes   0.0.0.0:8081->8081/tcp   product-server
...
(base) binildass-MacBook-Pro:~ binil$ 

View Product Server Logs
------------------------
(base) binildass-MacBook-Pro:01-ProductServer binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:01-ProductServer binil$ docker logs --follow 11bac3f7c0e1

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

...
Database is up to date, no changesets to execute
...
Hibernate: delete from product where productid=?
Hibernate: delete from product where productid=?
Hibernate: delete from product where productid=?
2023-05-17 15:02:49 DEBUG InitializationComponent.init:68 - Creating 2 new products in DB during initialization
Hibernate: insert into product (code, prodname, price, title) values (?, ?, ?, ?)
Hibernate: insert into product (code, prodname, price, title) values (?, ?, ?, ?)
2023-05-17 15:02:49 INFO  InitializationComponent.init:74 - End
2023-05-17 15:02:50 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroservice...

View Product Web Logs
---------------------
(base) binildass-MacBook-Pro:02-ProductWeb binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:02-ProductWeb binil$ docker logs --follow 0b5c7e62ebc2

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

...
2023-05-17 15:02:43 INFO  InitializationComponent.init:37 - Start
2023-05-17 15:02:43 DEBUG InitializationComponent.init:39 - Doing Nothing...
2023-05-17 15:02:43 INFO  InitializationComponent.init:41 - End
2023-05-17 15:02:44 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroservice...

Test Microservice
-----------------
(base) binildass-MacBook-Pro:~ binil$ minikube ip
192.168.64.6
(base) binildass-MacBook-Pro:~ binil$ 

http://192.168.64.6:8080/product.html

Clean the Environment
---------------------
(base) binildass-MacBook-Pro:ch08-03 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch08/ch08-03
(base) binildass-MacBook-Pro:ch08-03 binil$ sh clean.sh 
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
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  0.091 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.014 s]
[INFO] Ecom ............................................... SUCCESS [  0.036 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.310 s
[INFO] Finished at: 2023-05-17T20:57:46+05:30
[INFO] ------------------------------------------------------------------------
product-web
product-server
product-web
product-server
Untagged: ecom/product-web:latest
Untagged: ecom/product-server:latest
ecom-network
(base) binildass-MacBook-Pro:ch08-03 binil$ 



