Microservices in Docker using PostgreSQL in Docker and Rest Template (Full CRUD)
Browser => ProductWeb μS => Kafka => ProductServer μS => PostgreSQLDB

Container Deployment Architecture
---------------------------------

   @/
  /| ->Browser --> 8080[frontend network]
  / \                         |
(User)                        |
                              |
                    +--------------------+
                    |  frontend service  |...ro...<HTTP configuration>
                    |   "product-web"    |
                    +--------------------+
                              |
                      [backend network]
                              |
                    +--------------------+
                    |   infra service    |
                    |      "kafka"       |
                    +--------------------+
                              |
                      [backend network]
                              |
                    +--------------------+
                    |  business service  |
                    |  "product-server"  |
                    +--------------------+
                              |
                      [backend network]
                              |
                    +--------------------+
                    |  backend service   |  r+w   ___________________
                    |  "PostgreSQLDB"    |=======(      volume       )
                    +--------------------+        \_________________/


Build and Run in one go
-----------------------

(base) binildass-MacBook-Pro:ch09-03 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch09/ch09-03
(base) binildass-MacBook-Pro:ch09-03 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch09-03 binil$ sh makeandrun.sh 
[+] Running 0/2
 ⠿ web Error                                                                                             3.7s
 ⠿ server Error                                                                                          3.7s
[+] Building 2.3s (26/26) FINISHED                                                                            
 => [ecom/product-server internal] load build definition from server.Dockerfile                          0.0s
 => => transferring dockerfile: 87B                                                                      0.0s
 => [ecom/product-server internal] load .dockerignore                                                    0.0s
 => => transferring context: 2B                                                                          0.0s
 => [ecom/product-web internal] load build definition from web.Dockerfile                                0.0s
 => => transferring dockerfile: 84B                                                                      0.0s
 => [ecom/product-web internal] load metadata for docker.io/library/openjdk:17-jdk-alpine                0.0s
 => [ecom/product-web internal] load metadata for docker.io/library/maven:3.8.1-openjdk-17-slim          2.2s
...
 ⠿ Network ch09-03_ecom-network  Created                                                                 0.1s
 ⠿ Container zookeeper           Created                                                                 0.0s
 ⠿ Container postgres-docker     Created                                                                 0.0s
 ⠿ Container product-server      Created                                                                 0.0s
 ⠿ Container kafka               Created                                                                 0.0s
 ⠿ Container product-web         Created                                                                 0.0s
Attaching to kafka, postgres-docker, product-server, product-web, zookeeper
zookeeper        | zookeeper 04:36:23.96 
zookeeper        | zookeeper 04:36:23.97 Welcome to the Bitnami zookeeper container
zookeeper        | zookeeper 04:36:23.97 Subscribe to project updates by watching https://github.com/bitnami/containers
zookeeper        | zookeeper 04:36:23.97 Submit issues and feature requests at https://github.com/bitnami/containers/issues
zookeeper        | zookeeper 04:36:23.97 
zookeeper        | zookeeper 04:36:23.98 INFO  ==> ** Starting ZooKeeper setup **
zookeeper        | zookeeper 04:36:24.04 WARN  ==> You have set the environment variable ALLOW_ANONYMOUS_LOGIN=yes. For safety reasons, do not use this flag in a production environment.
postgres-docker  | 
postgres-docker  | PostgreSQL Database directory appears to contain a database; Skipping initialization
postgres-docker  | 
zookeeper        | zookeeper 04:36:24.07 INFO  ==> Initializing ZooKeeper...
zookeeper        | zookeeper 04:36:24.08 INFO  ==> No injected configuration file found, creating default config files...
postgres-docker  | 2023-05-19 04:36:24.142 UTC [1] LOG:  starting PostgreSQL 15.3 on x86_64-pc-linux-musl, compiled by gcc (Alpine 12.2.1_git20220924-r10) 12.2.1 20220924, 64-bit
postgres-docker  | 2023-05-19 04:36:24.142 UTC [1] LOG:  listening on IPv4 address "0.0.0.0", port 5432
postgres-docker  | 2023-05-19 04:36:24.142 UTC [1] LOG:  listening on IPv6 address "::", port 5432
postgres-docker  | 2023-05-19 04:36:24.149 UTC [1] LOG:  listening on Unix socket "/var/run/postgresql/.s.PGSQL.5432"
postgres-docker  | 2023-05-19 04:36:24.154 UTC [23] LOG:  database system was shut down at 2023-05-19 03:54:38 UTC
postgres-docker  | 2023-05-19 04:36:24.161 UTC [1] LOG:  database system is ready to accept connections
...
zookeeper        | 2023-05-19 04:36:26,959 [myid:1] - INFO  [main:o.a.z.ZookeeperBanner@42] - 
zookeeper        | 2023-05-19 04:36:26,959 [myid:1] - INFO  [main:o.a.z.ZookeeperBanner@42] -   ______                  _                                          
zookeeper        | 2023-05-19 04:36:26,965 [myid:1] - INFO  [main:o.a.z.ZookeeperBanner@42] -  |___  /                 | |                                         
zookeeper        | 2023-05-19 04:36:26,966 [myid:1] - INFO  [main:o.a.z.ZookeeperBanner@42] -     / /    ___     ___   | | __   ___    ___   _ __     ___   _ __   
zookeeper        | 2023-05-19 04:36:26,967 [myid:1] - INFO  [main:o.a.z.ZookeeperBanner@42] -    / /    / _ \   / _ \  | |/ /  / _ \  / _ \ | '_ \   / _ \ | '__|
zookeeper        | 2023-05-19 04:36:26,968 [myid:1] - INFO  [main:o.a.z.ZookeeperBanner@42] -   / /__  | (_) | | (_) | |   <  |  __/ |  __/ | |_) | |  __/ | |    
zookeeper        | 2023-05-19 04:36:26,968 [myid:1] - INFO  [main:o.a.z.ZookeeperBanner@42] -  /_____|  \___/   \___/  |_|\_\  \___|  \___| | .__/   \___| |_|
zookeeper        | 2023-05-19 04:36:26,971 [myid:1] - INFO  [main:o.a.z.ZookeeperBanner@42] -                                               | |                     
zookeeper        | 2023-05-19 04:36:26,971 [myid:1] - INFO  [main:o.a.z.ZookeeperBanner@42] -                                               |_|                     
zookeeper        | 2023-05-19 04:36:26,971 [myid:1] - INFO  [main:o.a.z.ZookeeperBanner@42] - 
...
kafka            | kafka 04:36:29.71 INFO  ==> Generated Kafka cluster ID 'WMQUlALJS0K9RvbUPLN6hg'
kafka            | kafka 04:36:29.71 INFO  ==> Formatting storage directories to add metadata...
product-web      | 
product-web      |   .   ____          _            __ _ _
product-web      |  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
product-web      | ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
product-web      |  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
product-web      |   '  |____| .__|_| |_|_| |_\__, | / / / /
product-web      |  =========|_|==============|___/=/_/_/_/
product-web      |  :: Spring Boot ::                (v3.2.0)
product-web      | 
product-server   | 
product-server   |   .   ____          _            __ _ _
product-server   |  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
product-server   | ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
product-server   |  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
product-server   |   '  |____| .__|_| |_|_| |_\__, | / / / /
product-server   |  =========|_|==============|___/=/_/_/_/
product-server   |  :: Spring Boot ::                (v3.2.0)
product-server   | 
product-web      | 2023-05-19 04:36:33 INFO  StartupInfoLogger.logStarting:51 - Starting EcomProductWebMicroserviceApplication v0.0.1-SNAPSHOT using Java 17-ea with PID 1 (/app.jar started by root in /)
product-web      | 2023-05-19 04:36:33 INFO  SpringApplication.logStartupProfileInfo:632 - No active profile set, falling back to 1 default profile: "default"
product-server   | 2023-05-19 04:36:33 INFO  StartupInfoLogger.logStarting:51 - Starting EcomProductServerMicroserviceApplication v0.0.1-SNAPSHOT using Java 17-ea with PID 1 (/app.jar started by root in /)
product-server   | 2023-05-19 04:36:33 INFO  SpringApplication.logStartupProfileInfo:632 - No active profile set, falling back to 1 default profile: "default"
...
product-web      | 2023-05-19 04:36:46 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductWebMicroserviceApplication in 15.507 seconds (process running for 19.624)
product-server   | Database is up to date, no changesets to execute
product-server   | 
product-server   | UPDATE SUMMARY
product-server   | Run:                          0
product-server   | Previously run:               4
product-server   | Filtered out:                 0
product-server   | -------------------------------
product-server   | Total change sets:            4
product-server   | 
product-server   | 2023-05-19 04:36:50 INFO  InitializationComponent.init:42 - Start
product-server   | 2023-05-19 04:36:50 INFO  InitializationComponent.init:67 - End
...


List the running Containers
---------------------------
(base) binildass-MacBook-Pro:~ binil$ docker ps -a
CONTAINER ID   IMAGE                       COMMAND                  CREATED             STATUS                           PORTS                                                  NAMES
04af0ca8b625   ecom/product-web            "java -jar app.jar"      11 minutes ago      Up 11 minutes                    0.0.0.0:8080->8080/tcp                                 product-web
46bed40df286   bitnami/kafka:latest        "/opt/bitnami/script…"   11 minutes ago      Up 11 minutes                    0.0.0.0:9092->9092/tcp                                 kafka
04bbad0ecab1   ecom/product-server         "java -jar app.jar"      11 minutes ago      Up 11 minutes                    0.0.0.0:8081->8081/tcp                                 product-server
826d4769a738   bitnami/zookeeper:latest    "/opt/bitnami/script…"   11 minutes ago      Up 11 minutes                    2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp, 8080/tcp   zookeeper
97b2b3d126d3   postgres:15.3-alpine3.18    "docker-entrypoint.s…"   11 minutes ago      Up 11 minutes                    0.0.0.0:5432->5432/tcp                                 postgres-docker
...
(base) binildass-MacBook-Pro:~ binil$

Find out Minikube IP
--------------------

(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ minikube ip
192.168.64.6
(base) binildass-MacBook-Pro:~ binil$

Test the Client
---------------
(base) binildass-MacBook-Pro:ch09-02 binil$ minikube ip
192.168.64.6
(base) binildass-MacBook-Pro:ch09-02 binil$ 

Open below link in Safari

http://192.168.64.6:8080/product.html

Clean the Environment
---------------------

(base) binildass-MacBook-Pro:ch09-03 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch09/ch09-03
(base) binildass-MacBook-Pro:ch09-03 binil$ sh clean.sh 
[+] Running 6/5
 ⠿ Container product-web         Removed                                                                0.3s
 ⠿ Container kafka               Removed                                                                1.3s
 ⠿ Container product-server      Removed                                                                0.4s
 ⠿ Container postgres-docker     Removed                                                                0.2s
 ⠿ Container zookeeper           Removed                                                                0.5s
 ⠿ Network ch09-03_ecom-network  Removed                                                                0.0s
Untagged: ecom/product-server:latest
Deleted: sha256:c6ce2f15999e2ee5b9212307b10a02b0e3e0460293aaae28eb7de620e85f6f21
Untagged: ecom/product-web:latest
Deleted: sha256:dc32eced6a8235ee9e6b2021d6f6022745d7ca0f67c59ddaaa4e58150bcafddf
(base) binildass-MacBook-Pro:ch09-03 binil$ 