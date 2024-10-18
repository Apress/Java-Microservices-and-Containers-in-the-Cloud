Microservices in Docker using MongoDB in Docker and Rest Template (Full CRUD)
Browser => ProductWeb μS => Kafka => ProductServer μS => MongoDB

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
                    |  backend service   |
                    |     "MongoDB"      |
                    +--------------------+

Build and Run in one go
-----------------------

(base) binildass-MacBook-Pro:ch09-04 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch09/ch09-04
(base) binildass-MacBook-Pro:ch09-04 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch09-04 binil$ sh makeandrun.sh 
[+] Running 0/2
 ⠿ web Error                                                                                             3.6s
 ⠿ server Error                                                                                          3.6s
[+] Building 55.7s (25/26)                                                                                    
 => [ecom/product-server internal] load build definition from server.Dockerfile                          0.0s
 => => transferring dockerfile: 487B                                                                     0.0s
 => [ecom/product-web internal] load build definition from web.Dockerfile                                0.0s
 => => transferring dockerfile: 481B                                                                     0.0s
 => [ecom/product-server internal] load .dockerignore                                                    0.0s
 => => transferring context: 2B                                                                          0.0s
 => [ecom/product-web internal] load .dockerignore                                                       0.0s
 => => transferring context: 2B                                                                          0.0s
 => [ecom/product-web internal] load metadata for docker.io/library/openjdk:17-jdk-alpine                0.0s
 => [ecom/product-web internal] load metadata for docker.io/library/maven:3.8.1-openjdk-17-slim         55.6s
 => [auth] library/maven:pull token for registry-1.docker.io                                             0.0s
 => CACHED [ecom/product-server stage-2 1/2] FROM docker.io/library/openjdk:17-jdk-alpine                0.0s
 => [ecom/product-web internal] load build context                                                       0.7s
 => => transferring context: 43.01MB                                                                     0.6s
 => [ecom/product-server prebuild 1/5] FROM docker.io/library/maven:3.8.1-openjdk-17-slim@sha256:b33f2c  0.0s
 => [ecom/product-server internal] load build context                                                    0.7s
 => => transferring context: 42.83MB                                                                     0.7s
 => CACHED [ecom/product-server prebuild 2/5] RUN mkdir -p /workspace                                    0.0s
 => CACHED [ecom/product-server prebuild 3/5] WORKDIR /workspace                                         0.0s
 => CACHED [ecom/product-web prebuild 4/5] COPY ./kafka-request-reply-util .                             0.0s
 => CACHED [ecom/product-web prebuild 5/5] RUN mvn clean install                                         0.0s
 => CACHED [ecom/product-web build 2/4] COPY --from=prebuild /root/.m2 /root/.m2                         0.0s
 => [ecom/product-web build 3/4] COPY ./02-ProductWeb .                                                  0.3s
 => CACHED [ecom/product-server prebuild 4/5] COPY ./kafka-request-reply-util .                          0.0s
 => CACHED [ecom/product-server prebuild 5/5] RUN mvn clean install                                      0.0s
 => CACHED [ecom/product-server build 2/4] COPY --from=prebuild /root/.m2 /root/.m2                      0.0s
 => [ecom/product-server build 3/4] COPY ./01-ProductServer .                                            0.2s
 => [ecom/product-server build 4/4] RUN mvn clean package                                               51.8s
 => [ecom/product-web build 4/4] RUN mvn clean package                                                  51.0s
 => [ecom/product-web stage-2 2/2] COPY --from=build ./target/*.jar app.jar                              0.1s
 => [ecom/product-server] exporting to image                                                             0.5s
 => => exporting layers                                                                                  0.3s
 => => writing image sha256:b54513d0dc3a3d5b791c988114aebb7c46dbfee106d6f0d3e1c8f4188fb5b59d             0.0s
 => => naming to docker.io/ecom/product-web                                                              0.0s
 => => writing image sha256:d0cb4d2302a2891120ae3f5b28616ebaaa85a5f25177f2c4a423b2458c0323f5             0.0s
 => => naming to docker.io/ecom/product-server                                                           0.0s
 => [ecom/product-server stage-2 2/2] COPY --from=build ./target/*.jar app.jar                           0.1s
[+] Running 6/5
 ⠿ Network ch09-04_ecom-network  Created                                                                 0.1s
 ⠿ Container zookeeper           Created                                                                 0.1s
 ⠿ Container mongo               Created                                                                 0.1s
 ⠿ Container kafka               Created                                                                 0.0s
 ⠿ Container product-server      Created                                                                 0.0s
 ⠿ Container product-web         Created                                                                 0.0s
Attaching to kafka, mongo, product-server, product-web, zookeeper
zookeeper       | zookeeper 07:42:30.25 
zookeeper       | zookeeper 07:42:30.25 Welcome to the Bitnami zookeeper container
zookeeper       | zookeeper 07:42:30.26 Subscribe to project updates by watching https://github.com/bitnami/containers
zookeeper       | zookeeper 07:42:30.26 Submit issues and feature requests at https://github.com/bitnami/containers/issues
zookeeper       | zookeeper 07:42:30.26 
zookeeper       | zookeeper 07:42:30.26 INFO  ==> ** Starting ZooKeeper setup **
...
zookeeper       | 2023-05-19 07:42:33,847 [myid:] - INFO  [main:o.a.z.s.q.QuorumPeerConfig@177] - Reading configuration from: /opt/bitnami/zookeeper/bin/../conf/zoo.cfg
product-server  | 
product-server  |   .   ____          _            __ _ _
product-server  |  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
product-server  | ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
product-server  |  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
product-server  |   '  |____| .__|_| |_|_| |_\__, | / / / /
product-server  |  =========|_|==============|___/=/_/_/_/
product-server  |  :: Spring Boot ::                (v3.2.0)
product-server  | 
product-web     | 
product-web     |   .   ____          _            __ _ _
product-web     |  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
product-web     | ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
product-web     |  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
product-web     |   '  |____| .__|_| |_|_| |_\__, | / / / /
product-web     |  =========|_|==============|___/=/_/_/_/
product-web     |  :: Spring Boot ::                (v3.2.0)
product-web     | 
product-server  | 2023-05-19 07:42:45 INFO  StartupInfoLogger.logStarting:51 - Starting EcomProductServerMicroserviceApplication v0.0.1-SNAPSHOT using Java 17-ea with PID 1 (/app.jar started by root in /)
product-server  | 2023-05-19 07:42:45 DEBUG StartupInfoLogger.logStarting:52 - Running with Spring Boot v3.0.6, Spring v6.0.8
product-server  | 2023-05-19 07:42:45 INFO  SpringApplication.logStartupProfileInfo:632 - No active profile set, falling back to 1 default profile: "default"
product-web     | 2023-05-19 07:42:46 INFO  StartupInfoLogger.logStarting:51 - Starting EcomProductWebMicroserviceApplication v0.0.1-SNAPSHOT using Java 17-ea with PID 1 (/app.jar started by root in /)
product-web     | 2023-05-19 07:42:46 DEBUG StartupInfoLogger.logStarting:52 - Running with Spring Boot v3.0.6, Spring v6.0.8
product-web     | 2023-05-19 07:42:46 INFO  SpringApplication.logStartupProfileInfo:632 - No active profile set, falling back to 1 default profile: "default"
kafka           | [2023-05-19 07:43:00,128] INFO [KafkaRaftServer nodeId=1] Kafka Server started (kafka.server.KafkaRaftServer)
product-server  | 2023-05-19 07:43:00 INFO  InitializationComponent.init:45 - Start
product-server  | 2023-05-19 07:43:00 DEBUG InitializationComponent.init:46 - Cleaning DB & Creating few products
mongo           | 2023-05-19T07:43:00.574+0000 I  NETWORK  [listener] connection accepted from 172.21.0.4:52342 #3 (3 connections now open)
mongo           | 2023-05-19T07:43:00.576+0000 I  NETWORK  [conn3] received client metadata from 172.21.0.4:52342 conn3: { driver: { name: "mongo-java-driver|sync|spring-boot", version: "4.8.2" }, os: { type: "Linux", name: "Linux", architecture: "amd64", version: "5.10.57" }, platform: "Java/Oracle Corporation/17-ea+14" }
mongo           | 2023-05-19T07:43:00.827+0000 I  STORAGE  [conn3] createCollection: test.product with generated UUID: 8752ff0c-8d9c-45e2-8f1a-97fdac630cfc and options: {}
mongo           | 2023-05-19T07:43:00.836+0000 I  INDEX    [conn3] index build: done building index _id_ on ns test.product
product-server  | 2023-05-19 07:43:00 INFO  InitializationComponent.init:68 - End
product-web     | 2023-05-19 07:43:03 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductWebMicroserviceApplication in 22.74 seconds (process running for 30.259)
product-server  | 2023-05-19 07:43:06 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductServerMicroserviceApplication in 28.284 seconds (process running for 35.075)
product-web     | 2023-05-19 07:43:28 INFO  ProductRestController.getAllProducts:77 - Start
product-web     | 2023-05-19 07:43:28 DEBUG LogAccessor.debug:191 - Using partition : 0
product-web     | 2023-05-19 07:43:28 DEBUG LogAccessor.debug:191 - Using partition : 0
product-web     | 2023-05-19 07:43:28 DEBUG LogAccessor.debug:313 - Sending: ProducerRecord(topic=product-req-topic, partition=null, headers=RecordHeaders(headers = [RecordHeader(key = kafka_replyTopic, value = [112, 114, 111, 100, 117, 99, 116, 45, 114, 101, 113, 45, 114, 101, 112, 108, 121, 45, 116, 111, 112, 105, 99]), RecordHeader(key = kafka_replyPartition, value = [0, 0, 0, 0]), RecordHeader(key = kafka_correlationId, value = [-82, 91, 123, -20, -39, -113, 75, 95, -109, -56, 125, -111, 126, -101, -93, -98])], isReadOnly = false), key=null, value=com.acme.ecom.product.model.Products@2d970a42, timestamp=null) with correlationId: ae5b7bec-d98f-4b5f-93c8-7d917e9ba39e
product-web     | 2023-05-19 07:43:28 INFO  ProductRestController.getAllProducts:111 - Ending
product-server  | 2023-05-19 07:43:29 INFO  ProductListener.listenWithHeaders:59 - Start
...


List the running Containers
---------------------------
(base) binildass-MacBook-Pro:~ binil$ docker ps -a
CONTAINER ID   IMAGE                       COMMAND                  CREATED         STATUS                     PORTS                                                  NAMES
daa1660d72be   ecom/product-web            "java -jar app.jar"      7 minutes ago   Up 7 minutes               0.0.0.0:8080->8080/tcp                                 product-web
0fccd7324b74   ecom/product-server         "java -jar app.jar"      7 minutes ago   Up 7 minutes               0.0.0.0:8081->8081/tcp                                 product-server
96b1d6742af7   bitnami/kafka:latest        "/opt/bitnami/script…"   7 minutes ago   Up 7 minutes               0.0.0.0:9092->9092/tcp                                 kafka
26bb0fdb3b15   mongo:4.2.24                "docker-entrypoint.s…"   7 minutes ago   Up 7 minutes               0.0.0.0:27017->27017/tcp                               mongo
2d9c5f4d53d8   bitnami/zookeeper:latest    "/opt/bitnami/script…"   7 minutes ago   Up 7 minutes               2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp, 8080/tcp   zookeeper
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

(base) binildass-MacBook-Pro:ch09-04 binil$ sh clean.sh 
[+] Running 6/5
 ⠿ Container kafka               Removed                                                                1.5s
 ⠿ Container product-web         Removed                                                                0.4s
 ⠿ Container product-server      Removed                                                                0.4s
 ⠿ Container mongo               Removed                                                                0.3s
 ⠿ Container zookeeper           Removed                                                                0.5s
 ⠿ Network ch09-04_ecom-network  Removed                                                                0.0s
Untagged: ecom/product-server:latest
Deleted: sha256:d0cb4d2302a2891120ae3f5b28616ebaaa85a5f25177f2c4a423b2458c0323f5
Untagged: ecom/product-web:latest
Deleted: sha256:b54513d0dc3a3d5b791c988114aebb7c46dbfee106d6f0d3e1c8f4188fb5b59d
(base) binildass-MacBook-Pro:ch09-04 binil$ 