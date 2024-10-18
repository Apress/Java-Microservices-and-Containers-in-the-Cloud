Sample using Docker Repository
Browser => ProductWeb μS ==> In-Memory-Data

Settings:
---------
(base) binildass-MacBook-Pro:~ binil$ vi /Users/binil/.m2/settings.xml
...
<settings>
    <server>
        <id>registry.hub.docker.com</id>
        <username>binildas</username>
        <password>********</password>
    </server>

  </servers>
</settings>
...
(base) binildass-MacBook-Pro:~ binil$ 

Build Product Web, containerize & push image to docker hub
----------------------------------------------------------
(base) binildass-MacBook-Pro:ch07-02 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-03/ch07/ch07-02
(base) binildass-MacBook-Pro:ch07-02 binil$ mvn compile jib:build
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO] 
[INFO] Ecom-Product-Web-Microservice                                      [jar]
[INFO] Ecom                                                               [pom]
[INFO] 
[INFO] --------< com.acme.ecom.product:Ecom-Product-Web-Microservice >---------
[INFO] Building Ecom-Product-Web-Microservice 0.0.1-SNAPSHOT              [1/2]
[INFO] --------------------------------[ jar ]---------------------------------
...
[INFO] Containerizing application to registry.hub.docker.com/binildas/product-web...
[WARNING] Base image 'eclipse-temurin:8-jre' does not use a specific image digest - build may not be reproducible
[INFO] Using credentials from Maven settings file for registry.hub.docker.com/binildas/product-web
[INFO] The base image requires auth. Trying again for eclipse-temurin:8-jre...
[WARNING] The system does not have docker-credential-desktop CLI
[WARNING]   Caused by: Cannot run program "docker-credential-desktop": error=20, Not a directory
...
[INFO] Using base image with digest: sha256:ca34c4ad9cb6b4fcbfb1ee24c94539901a6266fa585bef4ecfb57bc53468f6f9
[INFO] 
[INFO] Container entrypoint set to [java, -cp, /app/resources:/app/classes:/app/libs/*, com.acme.ecom.product.EcomProductMicroserviceApplication]
[INFO] 
[INFO] Built and pushed image as registry.hub.docker.com/binildas/product-webNFO] 
...
[INFO] ----------< com.acme.ecom.product:Ecom-Product-Microservice >-----------
[INFO] Building Ecom 0.0.1-SNAPSHOT                                       [2/2]
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] 
[INFO] --- jib-maven-plugin:3.2.0:build (default-cli) @ Ecom-Product-Microservice ---
[INFO] Skipping containerization because packaging is 'pom'...
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for Ecom 0.0.1-SNAPSHOT:
[INFO] 
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [01:52 min]
[INFO] Ecom ............................................... SUCCESS [  0.064 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  01:52 min
[INFO] Finished at: 2022-05-27T10:30:39+05:30
[INFO] ------------------------------------------------------------------------
(base) binildass-MacBook-Pro:ch07-02 binil$ 

Start Minikube so that we have a docker daemon
----------------------------------------------
(base) binildass-MacBook-Pro:ch07-02 binil$ minikube start
...
(base) binildass-MacBook-Pro:ch07-02 binil$

Set the Docker environment variables
------------------------------------
(base) binildass-MacBook-Pro:ch07-02 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-03/ch07/ch07-02
(base) binildass-MacBook-Pro:ch07-02 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch07-02 binil$ 

Run Product Web Microservice Container
--------------------------------------
(base) binildass-MacBook-Pro:ch07-02 binil$ docker run -it -p 8080:8080 binildas/product-web
Unable to find image 'binildas/product-web:latest' locally
latest: Pulling from binildas/product-web
125a6e411906: Pull complete 
42222acc001c: Pull complete 
4da85a7c2f39: Pull complete 
ebcc7b1a7ad2: Pull complete 
4edc7728a946: Pull complete 
9a2bc4698b49: Pull complete 
68af01cc5861: Pull complete 
f3636ebea575: Pull complete 
Digest: sha256:56254d5a6e84cbe625013d245d8fa7dfe157722d0a6dd50d434f8e63dfc339ad
Status: Downloaded newer image for binildas/product-web:latest

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.4.4)

2022-05-27 05:12:40 INFO  StartupInfoLogger.logStarting:55 - Starting EcomProductMicroserviceA...
2022-05-27 05:12:40 DEBUG StartupInfoLogger.logStarting:56 - Running with Spring Boot ...
2022-05-27 05:12:40 INFO  SpringApplication.logStartupProfileInfo:662 - No active ...
2022-05-27 05:12:41 INFO  InitializationComponent.init:37 - Start
2022-05-27 05:12:41 DEBUG InitializationComponent.init:39 - Doing Nothing...
2022-05-27 05:12:41 INFO  InitializationComponent.init:41 - End
2022-05-27 05:12:42 INFO  StartupInfoLogger.logStarted:61 - Started EcomProductMicroservice...
2022-05-27 05:16:03 INFO  ProductRestController.getAllProducts:63 - Start
2022-05-27 05:16:03 DEBUG ProductRestController.lambda$getAllProducts$0:73 - Product [productId=1, ...
2022-05-27 05:16:03 DEBUG ProductRestController.lambda$getAllProducts$0:73 - Product [productId=2, ...

Inspect Docker images
---------------------
(base) binildass-MacBook-Pro:ch07-02 binil$ docker images
REPOSITORY                                      TAG            IMAGE ID       CREATED         SIZE
...
binildas/product-web                       latest         b547edfdc0be   52 years ago    247MB
(base) binildass-MacBook-Pro:ch07-02 binil$ 

Inspect Docker Containers
-------------------------
(base) binildass-MacBook-Pro:ch07-02 binil$ docker ps
CONTAINER ID   IMAGE                       COMMAND                  CREATED          STATUS          PORTS                                      NAMES
e95b2b5eb64e   binildas/product-web   "java -cp /app/resou…"   6 minutes ago    Up 6 minutes    0.0.0.0:8080->8080/tcp                     loving_williams
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
(base) binildass-MacBook-Pro:ch07-02 binil$ docker stop e95b
e95b
(base) binildass-MacBook-Pro:ch07-02 binil$ 

Remove ProductWeb Microservice Docker Container
---------------------------------------------
Remove 
(base) binildass-MacBook-Pro:ch07-02 binil$ docker rm e95b
e95b
(base) binildass-MacBook-Pro:ch07-02 binil$ 


Remove ProductWeb Microservice Docker Image from Local registry
---------------------------------------------------------------
(base) binildass-MacBook-Pro:ch07-02 binil$ docker rmi b547
Untagged: binildas/product-web:latest
Untagged: binildas/product-web@sha256:56254d5a6e84cbe625013d245d8fa7dfe157722d0a6dd50d434f8e63dfc339ad
Deleted: sha256:b547edfdc0be94d5a44b168ff13f515aa916d63534a2d6075e000ab829a363e8
Deleted: sha256:c3febfe120497920ed4ac5cc0c24a0b9b92768e4053cdd58c772f1854bc32683
Deleted: sha256:a2170a61f2de92b28f29895e022a640d918d28e73c4b927bb4d6028fcbb459df
Deleted: sha256:37644127895687afe72438090371bdc57e6ba0fb5350c0d2b3e271bf71db8af9
Deleted: sha256:c623a074e60f3af2fd31049f0c66a20e966f6b4bd0139ae60d2a8b4a441cec3d
Deleted: sha256:eae2aea820cb20b9f5cc1755f90974adcd5e5bb82fc9abf852175d5a1f5db37c
Deleted: sha256:7802e8f380776a9d9c193f6b4b7a65fa334188cd69e0bb38d5e9fefecf5d7648
Deleted: sha256:e586f3fd46693fbc7a1d8d81b6156ced9ef924fb8c6e44a2ae5fbe7cad4a73f2
Deleted: sha256:e59fc94956120a6c7629f085027578e6357b48061d45714107e79f04a81a6f0c
(base) binildass-MacBook-Pro:ch07-02 binil$ 