Sample using Docker Repository
Browser => ProductWeb μS ==> In-Memory-Data

********************** Docker in Minikube ********************************
==========================================================================

Start Minikube so that we have a docker daemon
----------------------------------------------
(base) binildass-MacBook-Pro:ch07-03 binil$ minikube start
...
(base) binildass-MacBook-Pro:ch07-03 binil$

Set the Docker environment variables
------------------------------------
(base) binildass-MacBook-Pro:ch07-03 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-03/ch07/ch07-03
(base) binildass-MacBook-Pro:ch07-03 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch07-03 binil$ 

Login to Docker Hub from Terminal
---------------------------------
(base) binildass-MacBook-Pro:ch07-03 binil$ docker login
Login with your Docker ID to push and pull images from Docker Hub. If you don't have a Docker ID, head over to https://hub.docker.com to create one.
Username: binildas
Password: 
Error saving credentials: error storing credentials - err: exec: "docker-credential-desktop": executable file not found in $PATH, out: ``
(base) binildass-MacBook-Pro:ch07-03 binil$ 


Build Product Web, push image to docker hub, delete local image, pull & run from docker hub
-------------------------------------------------------------------------------------------
(base) binildass-MacBook-Pro:ch07-03 binil$ sh makeandrun.sh

(base) binildass-MacBook-Pro:ch07-03 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-03/ch07/ch07-03
(base) binildass-MacBook-Pro:ch07-03 binil$ docker push binildas/product-web
Using default tag: latest
The push refers to repository [docker.io/binildas/product-web]
2c54cf7f9c27: Preparing 
ceaf9e1ebef5: Preparing 
9b9b7f3d56a0: Preparing 
f1b5933fe4b5: Preparing 
denied: requested access to the resource is denied
(base) binildass-MacBook-Pro:ch07-03 binil$ 

ERROR! So, Lets set aside the makeandrun.sh and do it manual

Build and Package Product Web Microservice
------------------------------------------
(base) binildass-MacBook-Pro:ch07-03 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-03/ch07/ch07-03
(base) binildass-MacBook-Pro:ch07-03 binil$ mvn -Dmaven.test.skip=true clean package
[INFO] Scanning for projects...
...
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  1.622 s]
[INFO] Ecom ............................................... SUCCESS [  0.017 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.796 s
[INFO] Finished at: 2022-05-27T11:50:50+05:30
[INFO] ------------------------------------------------------------------------
(base) binildass-MacBook-Pro:ch07-03 binil$ 

Build Product Web Microservice Docker Image
-------------------------------------------
(base) binildass-MacBook-Pro:ch07-03 binil$ docker build  --build-arg JAR_FILE=02-ProductWeb/target/*.jar -t binildas/product-web .
Sending build context to Docker daemon  21.68MB
Step 1/5 : FROM openjdk:8-jdk-alpine
 ---> a3562aa0b991
Step 2/5 : VOLUME /tmp
 ---> Running in b33ed0a15fb0
Removing intermediate container b33ed0a15fb0
 ---> 388ef9de0cd0
Step 3/5 : ARG JAR_FILE
 ---> Running in cdab850cc040
Removing intermediate container cdab850cc040
 ---> 9c06159a60bf
Step 4/5 : COPY ${JAR_FILE} ecom.jar
 ---> 9ed33fae3b42
Step 5/5 : ENTRYPOINT ["java","-jar","/ecom.jar"]
 ---> Running in 0949e3c49a48
Removing intermediate container 0949e3c49a48
 ---> 079251d7b3b0
Successfully built 079251d7b3b0
Successfully tagged binildas/product-web:latest
(base) binildass-MacBook-Pro:ch07-03 binil$ 


-----------------------------
(base) binildass-MacBook-Pro:ch07-03 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-03/ch07/ch07-03
(base) binildass-MacBook-Pro:ch07-03 binil$ minikube ssh
                         _             _            
            _         _ ( )           ( )           
  ___ ___  (_)  ___  (_)| |/')  _   _ | |_      __  
/' _ ` _ `\| |/' _ `\| || , <  ( ) ( )| '_`\  /'__`\
| ( ) ( ) || || ( ) || || |\`\ | (_) || |_) )(  ___/
(_) (_) (_)(_)(_) (_)(_)(_) (_)`\___/'(_,__/'`\____)

$ docker login
Login with your Docker ID to push and pull images from Docker Hub. If you don't have a Docker ID, head over to https://hub.docker.com to create one.
Username: binildas
Password: 
WARNING! Your password will be stored unencrypted in /home/docker/.docker/config.json.
Configure a credential helper to remove this warning. See
https://docs.docker.com/engine/reference/commandline/login/#credentials-store

Login Succeeded
$ docker push binildas/product-web
Using default tag: latest
The push refers to repository [docker.io/binildas/product-web]
2c54cf7f9c27: Pushed 
ceaf9e1ebef5: Layer already exists 
9b9b7f3d56a0: Layer already exists 
f1b5933fe4b5: Layer already exists 
latest: digest: sha256:420cc888c575f695b5f2b84092b6bfa73d153bc0937cb846a28366ff14fa1f04 size: 1159
$ exit
logout
(base) binildass-MacBook-Pro:ch07-03 binil$ 

Remove Product Web Microservice Docker Image from Local
-------------------------------------------------------
(base) binildass-MacBook-Pro:ch07-03 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-03/ch07/ch07-03
(base) binildass-MacBook-Pro:ch07-03 binil$ docker rmi binildas/product-web
Untagged: binildas/product-web:latest
Untagged: binildas/product-web@sha256:420cc888c575f695b5f2b84092b6bfa73d153bc0937cb846a28366ff14fa1f04
Deleted: sha256:079251d7b3b01df698620fc9648125c62b220de7f031e3610d06970d994ca2ed
Deleted: sha256:9ed33fae3b42ca3cc4cbeacd66ed895e6c08476b7675330403851e4c51c685c2
Deleted: sha256:8669fe79edd0531ae647090475346821d36d7403d854b569d20bf66c828a67eb
Deleted: sha256:9c06159a60bf81b5ef9df17c7f1ed2ea960938ee732f32237d7d6e2920ddf940
Deleted: sha256:388ef9de0cd08f6c7b1ab124ece60eaa56ef8624976895912881c2b8737dacc4
(base) binildass-MacBook-Pro:ch07-03 binil$ 

Run Product Web Microservice Container
--------------------------------------
(base) binildass-MacBook-Pro:ch07-03 binil$ docker run -p 8080:8080  --name product-web binildas/product-web
Unable to find image 'binildas/product-web:latest' locally
latest: Pulling from binildas/product-web
e7c96db7181b: Already exists 
f910a506b6cb: Already exists 
c2274a1a0e27: Already exists 
fa880b6dd374: Pull complete 
Digest: sha256:420cc888c575f695b5f2b84092b6bfa73d153bc0937cb846a28366ff14fa1f04
Status: Downloaded newer image for binildas/product-web:latest

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.4.4)

2022-05-27 06:40:30 INFO  StartupInfoLogger.logStarting:55 - Starting EcomProductMicroservice...
2022-05-27 06:40:30 DEBUG StartupInfoLogger.logStarting:56 - Running with Spring Boot ...
2022-05-27 06:40:30 INFO  SpringApplication.logStartupProfileInfo:662 - No active ...
2022-05-27 06:40:31 INFO  InitializationComponent.init:37 - Start
2022-05-27 06:40:31 DEBUG InitializationComponent.init:39 - Doing Nothing...
2022-05-27 06:40:31 INFO  InitializationComponent.init:41 - End
2022-05-27 06:40:32 INFO  StartupInfoLogger.logStarted:61 - Started EcomProductMicroser...
2022-05-27 06:42:49 DEBUG ProductRestController.lambda$getAllProducts$0:73 - Product [productId=1...
2022-05-27 06:42:49 DEBUG ProductRestController.lambda$getAllProducts$0:73 - Product [productId=2...

Inspect Docker images
---------------------
(base) binildass-MacBook-Pro:ch07-03 binil$ docker images
REPOSITORY                                      TAG            IMAGE ID       CREATED          SIZE
binildas/product-web                       latest         079251d7b3b0   22 minutes ago   126MB
...
(base) binildass-MacBook-Pro:ch07-03 binil$ 

Inspect Docker Containers
-------------------------
(base) binildass-MacBook-Pro:ch07-03 binil$ docker ps
CONTAINER ID   IMAGE                       COMMAND                  CREATED         STATUS         PORTS                                      NAMES
c06c74e8bc19   binildas/product-web   "java -jar /ecom.jar"    6 minutes ago   Up 6 minutes   0.0.0.0:8080->8080/tcp                     product-web
...
(base) binildass-MacBook-Pro:ch07-02 binil$ 

Find Minikube ip
----------------
(base) binildass-MacBook-Pro:ch07-02 binil$ minikube ip
192.168.64.5
(base) binildass-MacBook-Pro:ch07-02 binil$ 

Test the Client
---------------
Open below link in Chrome

http://192.168.64.5:8080/product.html

Stop ProductWeb Microservice Docker Container
---------------------------------------------
(base) binildass-MacBook-Pro:ch07-03 binil$ docker stop c06c
c06c
(base) binildass-MacBook-Pro:ch07-03 binil$ 

Remove ProduetWeb Microservice Docker Container
----------------------------------------------
(base) binildass-MacBook-Pro:ch07-03 binil$ docker rm c06c
c06c
(base) binildass-MacBook-Pro:ch07-03 binil$ 

Remove ProductWeb Microservice Docker Image from Local registry
---------------------------------------------------------------
(base) binildass-MacBook-Pro:ch07-03 binil$ docker rmi 0792
Untagged: binildas/product-web:latest
Untagged: binildas/product-web@sha256:420cc888c575f695b5f2b84092b6bfa73d153bc0937cb846a28366ff14fa1f04
Deleted: sha256:079251d7b3b01df698620fc9648125c62b220de7f031e3610d06970d994ca2ed
Deleted: sha256:8669fe79edd0531ae647090475346821d36d7403d854b569d20bf66c828a67eb
(base) binildass-MacBook-Pro:ch07-03 binil$ 

********************** Docker in Minikube ********************************
==================== Build in a single script ============================

(base) binildass-MacBook-Pro:ch07-03 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch07/ch07-03
(base) binildass-MacBook-Pro:ch07-03 binil$ sh makeandrun.sh 
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO] 
[INFO] Ecom-Product-Web-Microservice                                      [jar]
[INFO] Ecom                                                               [pom]
[INFO] 
...
[INFO] 
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  2.365 s]
[INFO] Ecom ............................................... SUCCESS [  0.032 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.593 s
[INFO] Finished at: 2023-05-17T15:58:26+05:30
[INFO] ------------------------------------------------------------------------
...
Sending build context to Docker daemon  23.56MB
Step 1/5 : FROM openjdk:17-jdk-alpine
17-jdk-alpine: Pulling from library/openjdk
5843afab3874: Pull complete 
53c9466125e4: Pull complete 
d8d715783b80: Pull complete 
Digest: sha256:4b6abae565492dbe9e7a894137c966a7485154238902f2f25e9dbd9784383d81
Status: Downloaded newer image for openjdk:17-jdk-alpine
 ---> 264c9bdce361
Step 2/5 : VOLUME /tmp
 ---> Running in e6831d576709
Removing intermediate container e6831d576709
 ---> e717babc2075
Step 3/5 : ARG JAR_FILE
 ---> Running in 0ce7f6e17381
Removing intermediate container 0ce7f6e17381
 ---> 0fd102d3c9b1
Step 4/5 : COPY ${JAR_FILE} ecom.jar
 ---> c7d9498b97f8
Step 5/5 : ENTRYPOINT ["java","-jar","/ecom.jar"]
 ---> Running in b722a3e81c7a
Removing intermediate container b722a3e81c7a
 ---> a970ec2c2bc9
Successfully built a970ec2c2bc9
Successfully tagged binildas/product-web:latest
Using default tag: latest
The push refers to repository [docker.io/binildas/product-web]
8ee83a82eb9d: Pushed 
34f7184834b2: Mounted from library/openjdk 
5836ece05bfd: Mounted from library/openjdk 
72e830a4dff5: Mounted from library/openjdk 
latest: digest: sha256:6bab645fbd91d5b9811d414c86b9bcf6aa2a77a82a6c2c6fcc72f005424cafed size: 1163
Untagged: binildas/product-web:latest
Untagged: binildas/product-web@sha256:6bab645fbd91d5b9811d414c86b9bcf6aa2a77a82a6c2c6fcc72f005424cafed
Deleted: sha256:a970ec2c2bc9f6356894f376fa0abc4c7c30e9f3bf25f42b35df150dc7b6f843
Deleted: sha256:c7d9498b97f8e569d83ec98bbbb59089dff3dca01fb24fba54ad1ede35abb750
Deleted: sha256:19cbe35d2fc6801dc34cb71ecf30987ae9b3ab3f5bfa045209e71c2882ccde0f
Deleted: sha256:0fd102d3c9b1f3fe32718cbbacce11f36fc3002affece75e1667402b49682652
Deleted: sha256:e717babc20751338000df0aa46cdee486c90f00ab4e42efcbcd0ebabc8fca0c4
Unable to find image 'binildas/product-web:latest' locally
latest: Pulling from binildas/product-web
5843afab3874: Already exists 
53c9466125e4: Already exists 
d8d715783b80: Already exists 
0a00af6c82df: Pull complete 
Digest: sha256:6bab645fbd91d5b9811d414c86b9bcf6aa2a77a82a6c2c6fcc72f005424cafed
Status: Downloaded newer image for binildas/product-web:latest

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.0.6)

2023-05-17 10:29:58 INFO  StartupInfoLogger.logStarting:51 - Starting EcomProductMicroservice...
...
2023-05-17 10:30:00 INFO  InitializationComponent.init:37 - Start
2023-05-17 10:30:00 DEBUG InitializationComponent.init:39 - Doing Nothing...
2023-05-17 10:30:00 INFO  InitializationComponent.init:41 - End
2023-05-17 10:30:01 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroserviceApplication in 4.68 seconds (process running for 6.164)
2023-05-17 10:39:35 INFO  ProductRestController.getAllProducts:63 - Start
2023-05-17 10:39:35 DEBUG ProductRestController.lambda$getAllProducts$0:73 ...
2023-05-17 10:39:35 DEBUG ProductRestController.lambda$getAllProducts$0:73 - ...
2023-05-17 10:39:35 INFO  ProductRestController.getAllProducts:74 - Ending
2023-05-17 10:39:45 INFO  ProductRestController.addProduct:97 - Start

Find Minikube ip
----------------
(base) binildass-MacBook-Pro:~ binil$ minikube ip
192.168.64.6
(base) binildass-MacBook-Pro:~ binil$ 

Test the Client
---------------
Open below link in Chrome

http://192.168.64.6:8080/product.html
