Sample using mounted files for MongoDB
Browser => ProductWeb μS => ProductServer μS => MongoDB μS =mount=> File

Build & package Microservices, build images, and run containers
---------------------------------------------------------------
(base) binildass-MacBook-Pro:ch08-05 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch08-05 binil$ sh makeandrun.sh 
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
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  2.710 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.731 s]
[INFO] Ecom ............................................... SUCCESS [  0.032 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.641 s
[INFO] Finished at: 2023-05-17T22:27:39+05:30
[INFO] ------------------------------------------------------------------------
...
Successfully tagged ecom/product-web:latest
Successfully tagged ecom/product-server:latest
(base) binildass-MacBook-Pro:ch08-05 binil$ 


View Data Folder created
------------------------
(base) binildass-MacBook-Pro:~ binil$ minikube ssh
                         _             _            
            _         _ ( )           ( )           
  ___ ___  (_)  ___  (_)| |/')  _   _ | |_      __  
/' _ ` _ `\| |/' _ `\| || , <  ( ) ( )| '_`\  /'__`\
| ( ) ( ) || || ( ) || || |\`\ | (_) || |_) )(  ___/
(_) (_) (_)(_)(_) (_)(_)(_) (_)`\___/'(_,__/'`\____)

$ ls /Users/binil/dockerbook/ch08-05/mongodata
WiredTiger			       diagnostic.data
WiredTiger.lock			       index-1--9202702318305535927.wt
WiredTiger.turtle		       index-11--9202702318305535927.wt
WiredTiger.wt			       index-3--9202702318305535927.wt
WiredTigerLAS.wt		       index-5--9202702318305535927.wt
_mdb_catalog.wt			       index-6--9202702318305535927.wt
collection-0--9202702318305535927.wt   index-9--9202702318305535927.wt
collection-10--9202702318305535927.wt  journal
collection-2--9202702318305535927.wt   mongod.lock
collection-4--9202702318305535927.wt   sizeStorer.wt
collection-8--9202702318305535927.wt   storage.bson
$ 

List running containers
-----------------------
(base) binildass-MacBook-Pro:~ binil$ docker ps
CONTAINER ID   IMAGE                                      COMMAND                  CREATED         STATUS         PORTS                      NAMES
3c9548abac1b   ecom/product-web                           "java -jar /ecom.jar"    3 minutes ago   Up 3 minutes   0.0.0.0:8080->8080/tcp     product-web
2fcaac424751   ecom/product-server                        "java -jar /ecom.jar"    3 minutes ago   Up 3 minutes   0.0.0.0:8081->8081/tcp     product-server
fa9a5f1a394a   mongo:4.2.24                               "docker-entrypoint.s…"   3 minutes ago   Up 3 minutes   0.0.0.0:27017->27017/tcp   mongo
...
(base) binildass-MacBook-Pro:~ binil$

View Product Server Logs
------------------------
(base) binildass-MacBook-Pro:01-ProductServer binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:01-ProductServer binil$ docker logs --follow 2fcaac424751

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2023-05-17 16:57:56 INFO  StartupInfoLogger.logStarting:51 - Starting EcomProductMicroservice...
...
2023-05-17 16:57:56 INFO  InitializationComponent.init:42 - Start
2023-05-17 16:57:56 DEBUG InitializationComponent.init:46 - Deleting all products ...
2023-05-17 16:57:56 DEBUG InitializationComponent.init:63 - Creating 2 new products ...
Hibernate: insert into product (code, prodname, price, title) values (?, ?, ?, ?)
Hibernate: insert into product (code, prodname, price, title) values (?, ?, ?, ?)
2023-05-17 16:57:56 INFO  InitializationComponent.init:68 - End
2023-05-17 16:57:57 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroservice...

View Product Web Logs
---------------------
(base) binildass-MacBook-Pro:01-ProductServer binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:01-ProductServer binil$ docker logs --follow 3c9548abac1b

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

...
2023-05-17 16:57:57 INFO  InitializationComponent.init:37 - Start
2023-05-17 16:57:57 DEBUG InitializationComponent.init:39 - Doing Nothing...
2023-05-17 16:57:57 INFO  InitializationComponent.init:41 - End
2023-05-17 16:57:58 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroservice...
...

Test Microservice
-----------------
(base) binildass-MacBook-Pro:~ binil$ minikube ip
192.168.64.6
(base) binildass-MacBook-Pro:~ binil$ 

http://192.168.64.6:8080/product.html

Data is loaded in the screen

Shout down the mongo container
--------------------------------------
List all containers to identify mongo container id

sudo docker ps
sudo docker rm -f (mongo container id)

(base) binildass-MacBook-Pro:~ binil$ docker rm -f fa9a5f1a394a
fa9a5f1a394a
(base) binildass-MacBook-Pro:~ binil$ 

(base) binildass-MacBook-Pro:~ binil$ docker ps
CONTAINER ID   IMAGE                                      COMMAND                  CREATED          STATUS          PORTS                    NAMES
3c9548abac1b   ecom/product-web                           "java -jar /ecom.jar"    18 minutes ago   Up 18 minutes   0.0.0.0:8080->8080/tcp   product-web
2fcaac424751   ecom/product-server                        "java -jar /ecom.jar"    18 minutes ago   Up 18 minutes   0.0.0.0:8081->8081/tcp   product-server
...

Start new mongo container with same volume attached
-----------------------------------------

docker run -d -it -p 27017:27017 --name mongo  --net=ecom-network -v /Users/binil/dockerbook/ch08-05/mongodata:/data/db  mongo:4.2.24

(base) binildass-MacBook-Pro:~ binil$ docker run -d -it -p 27017:27017 --name mongo  --net=ecom-network -v /Users/binil/dockerbook/ch08-05/mongodata:/data/db  mongo:4.2.24
372b037ca07cac8e09e77483631304f4d2d8efb3ef7297683c59087d7b6986aa
(base) binildass-MacBook-Pro:~ binil$ 

List running containers
-----------------------
(base) binildass-MacBook-Pro:~ binil$ docker ps
CONTAINER ID   IMAGE                                      COMMAND                  CREATED          STATUS          PORTS                      NAMES
372b037ca07c   mongo:4.2.24                               "docker-entrypoint.s…"   6 seconds ago    Up 5 seconds    0.0.0.0:27017->27017/tcp   mongo
3c9548abac1b   ecom/product-web                           "java -jar /ecom.jar"    21 minutes ago   Up 21 minutes   0.0.0.0:8080->8080/tcp     product-web
2fcaac424751   ecom/product-server                        "java -jar /ecom.jar"    21 minutes ago   Up 21 minutes   0.0.0.0:8081->8081/tcp     product-server                 mongo
...
(base) binildass-MacBook-Pro:~ binil$

Test the Client
---------------
Open below link in a new browser window

http://192.168.64.6:8080/product.html

Data has been retained in the screen.

Clean the Environment
---------------------
(base) binildass-MacBook-Pro:ch08-05 binil$ sh clean.sh 
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
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  0.107 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.010 s]
[INFO] Ecom ............................................... SUCCESS [  0.039 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.376 s
[INFO] Finished at: 2023-05-17T22:50:32+05:30
[INFO] ------------------------------------------------------------------------
product-web
product-server
mongo
product-web
product-server
mongo
Untagged: ecom/product-web:latest
Untagged: ecom/product-server:latest
ecom-network
(base) binildass-MacBook-Pro:ch08-05 binil$ 





