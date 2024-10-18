Sample using Links
Browser => ProductWeb μS =link=> ProductServer μS =link=> MongoDB μS

Build & Package Microservices, build images and run containers
--------------------------------------------------------------
(base) binildass-MacBook-Pro:ch08-01 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-03/ch08/ch08-01
(base) binildass-MacBook-Pro:ch08-01 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch08-01 binil$ sh makeandrun.sh 
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
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  2.676 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.726 s]
[INFO] Ecom ............................................... SUCCESS [  0.034 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.601 s
[INFO] Finished at: 2023-05-17T17:28:10+05:30
[INFO] ------------------------------------------------------------------------
...
Successfully tagged ecom/product-web:latest
Successfully tagged ecom/product-server:latest
(base) binildass-MacBook-Pro:ch08-01 binil$ 


List running containers
-----------------------
(base) binildass-MacBook-Pro:~ binil$ docker ps
CONTAINER ID   IMAGE                                      COMMAND                  CREATED          STATUS          PORTS                      NAMES
d74d0dace020   ecom/product-web                           "java -jar /ecom.jar"    3 seconds ago   Up 3 seconds   0.0.0.0:8080->8080/tcp     product-web
1c6b4953702b   ecom/product-server                        "java -jar /ecom.jar"    4 seconds ago   Up 3 seconds   0.0.0.0:8081->8081/tcp     product-server
1851d743df67   mongo:4.2.24                               "docker-entrypoint.s…"   4 seconds ago   Up 4 seconds   0.0.0.0:27017->27017/tcp   mongo
...
(base) binildass-MacBook-Pro:~ binil$

View Product Server Logs
------------------------
(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ docker logs --follow 1c6b4953702b

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)
...
2023-05-17 12:09:18 INFO  InitializationComponent.init:47 - Start...
2023-05-17 12:09:18 DEBUG InitializationComponent.init:51 - Deleting all existing data on start...
2023-05-17 12:09:18 DEBUG InitializationComponent.init:56 - Creating initial data on start...
2023-05-17 12:09:18 INFO  InitializationComponent.init:105 - End
2023-05-17 12:09:20 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroservice...

View Product Web Logs
---------------------
(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ docker logs --follow d74d0dace020

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)
...
2023-05-17 12:09:17 INFO  InitializationComponent.init:37 - Start
2023-05-17 12:09:17 DEBUG InitializationComponent.init:39 - Doing Nothing...
2023-05-17 12:09:17 INFO  InitializationComponent.init:41 - End
2023-05-17 12:09:19 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroservice...

Test Microservice
-----------------
(base) binildass-MacBook-Pro:~ binil$ minikube ip
192.168.64.6
(base) binildass-MacBook-Pro:~ binil$

http://192.168.64.6:8080/product.html

Clean the Environment
---------------------

(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch08-01 binil$ sh clean.sh 
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
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  0.095 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.013 s]
[INFO] Ecom ............................................... SUCCESS [  0.037 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.324 s
[INFO] Finished at: 2023-05-17T17:45:01+05:30
[INFO] ------------------------------------------------------------------------
product-web
product-server
mongo
product-web
product-server
mongo
Untagged: ecom/product-web:latest
Deleted: sha256:851af5bd9766df43dbf9cbe19fb23ea8c1e431e66aa3453775fb968fc498f65e
Deleted: sha256:1dcd5bc64f33b12b50591a10da018018b014574f3cbcbf4b723227c6f897cf01
Deleted: sha256:d178d7451927d6e1ea0a3acfa3cad1855c7cfed9f8cdae4308ca4545537e1a6a
Untagged: ecom/product-server:latest
Deleted: sha256:0261a4f3988b1b1902b66c305e2f77005b49502048a4800b9d7d41a5211f271b
Deleted: sha256:a966d9fd7df3ec5181f1a986390398b19aa9eb6ec0a86eec32fc3614a13a744f
Deleted: sha256:dd01929d2de42c3471bde974c63883db0320d4d606f96b9f17aeef9f753266a0
(base) binildass-MacBook-Pro:ch08-01 binil$ 


