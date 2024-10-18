
Sample using mounted files for MongoDB then use Docker Compose
Browser => ProductWeb μS => Kafka => ProductServer μS => MongoDB μS =mount=> File

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
                    |  business service  |
                    |  "product-server"  |
                    +--------------------+
                              |
                      [backend network]
                              |
                    +--------------------+
                    |  backend service   |  r+w   ___________________
                    |     "MongoDB"      |=======(      volume       )
                    +--------------------+        \_________________/

Create folder /home/docker/binil/mongodata
------------------------------------------

(base) binildass-MacBook-Pro:mongodata binil$ minikube ssh
                         _             _            
            _         _ ( )           ( )           
  ___ ___  (_)  ___  (_)| |/')  _   _ | |_      __  
/' _ ` _ `\| |/' _ `\| || , <  ( ) ( )| '_`\  /'__`\
| ( ) ( ) || || ( ) || || |\`\ | (_) || |_) )(  ___/
(_) (_) (_)(_)(_) (_)(_)(_) (_)`\___/'(_,__/'`\____)

$ pwd
/home/docker
$ cd /home/docker
$ mkdir -p binil/mongodata
$ ls
binil
$ cd binil/mongodata/
$ ls
$ pwd
/home/docker/binil/mongodata
$ 

Build and Run in one go
-----------------------

(base) binildass-MacBook-Pro:ch09-02 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch09/ch09-02
(base) binildass-MacBook-Pro:ch09-02 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch09-02 binil$ sh makeandrun.sh 
[+] Running 0/2
 ⠿ server Error                                                            5.3s
 ⠿ web Error                                                               5.3s
[+] Building 2.2s (21/21) FINISHED                                              
 => [ecom/product-server internal] load build definition from Dockerfile   0.0s
 => => transferring dockerfile: 80B                                        0.0s
 => [ecom/product-web internal] load build definition from Dockerfile      0.0s
 => => transferring dockerfile: 80B                                        0.0s
 => [ecom/product-server internal] load .dockerignore                      0.0s
 => => transferring context: 2B                                            0.0s
 => [ecom/product-web internal] load .dockerignore                         0.0s
 => => transferring context: 2B                                            0.0s
 => [ecom/product-web internal] load metadata for docker.io/library/openj  0.0s
 => [ecom/product-web internal] load metadata for docker.io/library/maven  2.0s
 => [ecom/product-server build 1/6] FROM docker.io/library/maven:3.8.1-op  0.0s
 => [ecom/product-web internal] load build context                         0.0s
 => => transferring context: 3.63kB                                        0.0s
 => [ecom/product-server stage-1 1/2] FROM docker.io/library/openjdk:17-j  0.0s
 => [ecom/product-server internal] load build context                      0.0s
 => => transferring context: 3.67kB                                        0.0s
 => CACHED [ecom/product-server build 2/6] RUN mkdir -p /workspace         0.0s
 => CACHED [ecom/product-server build 3/6] WORKDIR /workspace              0.0s
 => CACHED [ecom/product-web build 4/6] COPY pom.xml /workspace            0.0s
 => CACHED [ecom/product-web build 5/6] COPY src /workspace/src            0.0s
 => CACHED [ecom/product-web build 6/6] RUN mvn -f pom.xml clean package   0.0s
 => CACHED [ecom/product-web stage-1 2/2] COPY --from=build /workspace/ta  0.0s
 => [ecom/product-server] exporting to image                               0.0s
 => => exporting layers                                                    0.0s
 => => writing image sha256:5da54836a2eb1d50886337564ce1cc7c50b90e6bfa3b1  0.0s
 => => naming to docker.io/ecom/product-web                                0.0s
 => => writing image sha256:3be0f774112a50bde4d9a418bb39bdc0ee4cb711c3d1c  0.0s
 => => naming to docker.io/ecom/product-server                             0.0s
 => CACHED [ecom/product-server build 4/6] COPY pom.xml /workspace         0.0s
 => CACHED [ecom/product-server build 5/6] COPY src /workspace/src         0.0s
 => CACHED [ecom/product-server build 6/6] RUN mvn -f pom.xml clean packa  0.0s
 => CACHED [ecom/product-server stage-1 2/2] COPY --from=build /workspace  0.0s
[+] Running 5/1
 ⠿ Network ch09-02_ecom-network  Created                                   0.1s
 ⠿ Volume "ch09-02_db-data"      Created                                   0.0s
 ⠿ Container mongo               Created                                   0.0s
 ⠿ Container product-server      Created                                   0.0s
 ⠿ Container product-web         Created                                   0.0s
Attaching to mongo, product-server, product-web
mongo           | 2023-05-18T15:37:50.700+0000 I  CONTROL  [main] Automatically disabling TLS 1.0, to force-enable TLS 1.0 specify --sslDisabledProtocols 'none'
mongo           | 2023-05-18T15:37:50.710+0000 W  ASIO     [main] No TransportLayer configured during NetworkInterface startup
mongo           | 2023-05-18T15:37:50.711+0000 I  CONTROL  [initandlisten] MongoDB starting : pid=1 port=27017 dbpath=/data/db 64-bit host=1ab728047d18
mongo           | 2023-05-18T15:37:50.711+0000 I  CONTROL  [initandlisten] db version v4.2.24
mongo           | 2023-05-18T15:37:50.711+0000 I  CONTROL  [initandlisten] git version: 5e4ec1d24431fcdd28b579a024c5c801b8cde4e2
mongo           | 2023-05-18T15:37:50.711+0000 I  CONTROL  [initandlisten] OpenSSL version: OpenSSL 1.1.1  11 Sep 2018
mongo           | 2023-05-18T15:37:50.711+0000 I  CONTROL  [initandlisten] allocator: tcmalloc
mongo           | 2023-05-18T15:37:50.711+0000 I  CONTROL  [initandlisten] modules: none
mongo           | 2023-05-18T15:37:50.711+0000 I  CONTROL  [initandlisten] build environment:
mongo           | 2023-05-18T15:37:50.711+0000 I  CONTROL  [initandlisten]     distmod: ubuntu1804
mongo           | 2023-05-18T15:37:50.711+0000 I  CONTROL  [initandlisten]     distarch: x86_64
mongo           | 2023-05-18T15:37:50.711+0000 I  CONTROL  [initandlisten]     target_arch: x86_64
mongo           | 2023-05-18T15:37:50.711+0000 I  CONTROL  [initandlisten] options: { net: { bindIp: "*" } }
mongo           | 2023-05-18T15:37:50.712+0000 I  STORAGE  [initandlisten] wiredtiger_open config: create,cache_size=1399M,cache_overflow=(file_max=0M),session_max=33000,eviction=(threads_min=4,threads_max=4),config_base=false,statistics=(fast),log=(enabled=true,archive=true,path=journal,compressor=snappy),file_manager=(close_idle_time=100000,close_scan_interval=10,close_handle_minimum=250),statistics_log=(wait=0),verbose=[recovery_progress,checkpoint_progress],
mongo           | 2023-05-18T15:37:51.444+0000 I  STORAGE  [initandlisten] WiredTiger message [1684424271:444761][1:0x7f71c1704b00], txn-recover: Set global recovery timestamp: (0, 0)
mongo           | 2023-05-18T15:37:51.448+0000 I  RECOVERY [initandlisten] WiredTiger recoveryTimestamp. Ts: Timestamp(0, 0)
mongo           | 2023-05-18T15:37:51.468+0000 I  STORAGE  [initandlisten] Timestamp monitor starting
mongo           | 2023-05-18T15:37:51.490+0000 I  CONTROL  [initandlisten] 
mongo           | 2023-05-18T15:37:51.490+0000 I  CONTROL  [initandlisten] ** WARNING: Access control is not enabled for the database.
mongo           | 2023-05-18T15:37:51.490+0000 I  CONTROL  [initandlisten] **          Read and write access to data and configuration is unrestricted.
mongo           | 2023-05-18T15:37:51.490+0000 I  CONTROL  [initandlisten] 
mongo           | 2023-05-18T15:37:51.523+0000 I  STORAGE  [initandlisten] createCollection: admin.system.version with provided UUID: 5090b31c-a02c-4e28-956f-e3e533f995f4 and options: { uuid: UUID("5090b31c-a02c-4e28-956f-e3e533f995f4") }
mongo           | 2023-05-18T15:37:51.531+0000 I  INDEX    [initandlisten] index build: done building index _id_ on ns admin.system.version
mongo           | 2023-05-18T15:37:51.537+0000 I  COMMAND  [initandlisten] setting featureCompatibilityVersion to 4.2
mongo           | 2023-05-18T15:37:51.539+0000 I  STORAGE  [initandlisten] Flow Control is enabled on this deployment.
mongo           | 2023-05-18T15:37:51.541+0000 I  STORAGE  [initandlisten] createCollection: local.startup_log with generated UUID: 60f24ef4-d744-48bb-b280-05dcdf9c0ada and options: { capped: true, size: 10485760 }
mongo           | 2023-05-18T15:37:51.546+0000 I  INDEX    [initandlisten] index build: done building index _id_ on ns local.startup_log
mongo           | 2023-05-18T15:37:51.547+0000 I  FTDC     [initandlisten] Initializing full-time diagnostic data capture with directory '/data/db/diagnostic.data'
mongo           | 2023-05-18T15:37:51.580+0000 I  CONTROL  [LogicalSessionCacheReap] Sessions collection is not set up; waiting until next sessions reap interval: config.system.sessions does not exist
mongo           | 2023-05-18T15:37:51.583+0000 I  NETWORK  [listener] Listening on /tmp/mongodb-27017.sock
mongo           | 2023-05-18T15:37:51.583+0000 I  NETWORK  [listener] Listening on 0.0.0.0
mongo           | 2023-05-18T15:37:51.583+0000 I  NETWORK  [listener] waiting for connections on port 27017
mongo           | 2023-05-18T15:37:51.590+0000 I  STORAGE  [LogicalSessionCacheRefresh] createCollection: config.system.sessions with provided UUID: e720abf8-1551-4960-83a1-93060a420fc7 and options: { uuid: UUID("e720abf8-1551-4960-83a1-93060a420fc7") }
mongo           | 2023-05-18T15:37:51.594+0000 I  INDEX    [LogicalSessionCacheRefresh] index build: done building index _id_ on ns config.system.sessions
mongo           | 2023-05-18T15:37:51.604+0000 I  INDEX    [LogicalSessionCacheRefresh] index build: starting on config.system.sessions properties: { v: 2, key: { lastUse: 1 }, name: "lsidTTLIndex", ns: "config.system.sessions", expireAfterSeconds: 1800 } using method: Hybrid
mongo           | 2023-05-18T15:37:51.604+0000 I  INDEX    [LogicalSessionCacheRefresh] build may temporarily use up to 200 megabytes of RAM
mongo           | 2023-05-18T15:37:51.604+0000 I  INDEX    [LogicalSessionCacheRefresh] index build: collection scan done. scanned 0 total records in 0 seconds
mongo           | 2023-05-18T15:37:51.604+0000 I  INDEX    [LogicalSessionCacheRefresh] index build: inserted 0 keys from external sorter into index in 0 seconds
mongo           | 2023-05-18T15:37:51.606+0000 I  INDEX    [LogicalSessionCacheRefresh] index build: done building index lsidTTLIndex on ns config.system.sessions
product-server  | 
product-server  |   .   ____          _            __ _ _
product-server  |  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
product-server  | ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
product-server  |  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
product-server  |   '  |____| .__|_| |_|_| |_\__, | / / / /
product-server  |  =========|_|==============|___/=/_/_/_/
product-server  |  :: Spring Boot ::                (v3.2.0)
product-server  | 
product-server  | 2023-05-18 15:37:55 INFO  StartupInfoLogger.logStarting:51 - Starting EcomProductMicroserviceApplication v0.0.1-SNAPSHOT using Java 17-ea with PID 1 (/app.jar started by root in /)
product-server  | 2023-05-18 15:37:55 DEBUG StartupInfoLogger.logStarting:52 - Running with Spring Boot v3.0.6, Spring v6.0.8
product-server  | 2023-05-18 15:37:55 INFO  SpringApplication.logStartupProfileInfo:632 - No active profile set, falling back to 1 default profile: "default"
product-web     | 
product-web     |   .   ____          _            __ _ _
product-web     |  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
product-web     | ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
product-web     |  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
product-web     |   '  |____| .__|_| |_|_| |_\__, | / / / /
product-web     |  =========|_|==============|___/=/_/_/_/
product-web     |  :: Spring Boot ::                (v3.2.0)
product-web     | 
product-web     | 2023-05-18 15:37:56 INFO  StartupInfoLogger.logStarting:51 - Starting EcomProductMicroserviceApplication v0.0.1-SNAPSHOT using Java 17-ea with PID 1 (/app.jar started by root in /)
product-web     | 2023-05-18 15:37:56 DEBUG StartupInfoLogger.logStarting:52 - Running with Spring Boot v3.0.6, Spring v6.0.8
product-web     | 2023-05-18 15:37:56 INFO  SpringApplication.logStartupProfileInfo:632 - No active profile set, falling back to 1 default profile: "default"
mongo           | 2023-05-18T15:38:00.154+0000 I  NETWORK  [listener] connection accepted from 172.26.0.1:46302 #1 (1 connection now open)
mongo           | 2023-05-18T15:38:00.155+0000 I  NETWORK  [listener] connection accepted from 172.26.0.1:46306 #2 (2 connections now open)
mongo           | 2023-05-18T15:38:00.172+0000 I  NETWORK  [conn2] received client metadata from 172.26.0.1:46306 conn2: { driver: { name: "mongo-java-driver|sync|spring-boot", version: "4.8.2" }, os: { type: "Linux", name: "Linux", architecture: "amd64", version: "5.10.57" }, platform: "Java/Oracle Corporation/17-ea+14" }
mongo           | 2023-05-18T15:38:00.173+0000 I  NETWORK  [conn1] received client metadata from 172.26.0.1:46302 conn1: { driver: { name: "mongo-java-driver|sync|spring-boot", version: "4.8.2" }, os: { type: "Linux", name: "Linux", architecture: "amd64", version: "5.10.57" }, platform: "Java/Oracle Corporation/17-ea+14" }
product-web     | 2023-05-18 15:38:00 INFO  InitializationComponent.init:37 - Start
product-web     | 2023-05-18 15:38:00 DEBUG InitializationComponent.init:39 - Doing Nothing...
product-web     | 2023-05-18 15:38:00 INFO  InitializationComponent.init:41 - End
product-server  | 2023-05-18 15:38:01 INFO  InitializationComponent.init:47 - Start...
product-server  | 2023-05-18 15:38:01 DEBUG InitializationComponent.init:51 - Deleting all existing data on start...
mongo           | 2023-05-18T15:38:01.371+0000 I  NETWORK  [listener] connection accepted from 172.26.0.1:46318 #3 (3 connections now open)
mongo           | 2023-05-18T15:38:01.372+0000 I  NETWORK  [conn3] received client metadata from 172.26.0.1:46318 conn3: { driver: { name: "mongo-java-driver|sync|spring-boot", version: "4.8.2" }, os: { type: "Linux", name: "Linux", architecture: "amd64", version: "5.10.57" }, platform: "Java/Oracle Corporation/17-ea+14" }
product-server  | 2023-05-18 15:38:01 DEBUG InitializationComponent.init:56 - Creating initial data on start...
mongo           | 2023-05-18T15:38:01.527+0000 I  STORAGE  [conn3] createCollection: test.productCategoryOR with generated UUID: 27b4e05f-68b8-428e-8e53-9e2e5ef6dac4 and options: {}
mongo           | 2023-05-18T15:38:01.532+0000 I  INDEX    [conn3] index build: done building index _id_ on ns test.productCategoryOR
mongo           | 2023-05-18T15:38:01.588+0000 I  STORAGE  [conn3] createCollection: test.productOR with generated UUID: 6bbfae5b-4b1d-449f-9621-3cb160577ee7 and options: {}
mongo           | 2023-05-18T15:38:01.592+0000 I  INDEX    [conn3] index build: done building index _id_ on ns test.productOR
product-server  | 2023-05-18 15:38:01 INFO  InitializationComponent.init:105 - End
product-web     | 2023-05-18 15:38:02 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroserviceApplication in 7.909 seconds (process running for 10.326)
product-server  | 2023-05-18 15:38:03 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroserviceApplication in 9.453 seconds (process running for 12.254)

Inspect folder /home/docker/binil/mongodata
-------------------------------------------

(base) binildass-MacBook-Pro:mongodata binil$ minikube ssh
                         _             _            
            _         _ ( )           ( )           
  ___ ___  (_)  ___  (_)| |/')  _   _ | |_      __  
/' _ ` _ `\| |/' _ `\| || , <  ( ) ( )| '_`\  /'__`\
| ( ) ( ) || || ( ) || || |\`\ | (_) || |_) )(  ___/
(_) (_) (_)(_)(_) (_)(_)(_) (_)`\___/'(_,__/'`\____)

$ 
...
$ pwd
/home/docker/binil/mongodata
$ ls
WiredTiger			       diagnostic.data
WiredTiger.lock			       index-1--3235613002971921450.wt
WiredTiger.turtle		       index-11--3235613002971921450.wt
WiredTiger.wt			       index-3--3235613002971921450.wt
WiredTigerLAS.wt		       index-5--3235613002971921450.wt
_mdb_catalog.wt			       index-6--3235613002971921450.wt
collection-0--3235613002971921450.wt   index-9--3235613002971921450.wt
collection-10--3235613002971921450.wt  journal
collection-2--3235613002971921450.wt   mongod.lock
collection-4--3235613002971921450.wt   sizeStorer.wt
collection-8--3235613002971921450.wt   storage.bson
$ 

List the running Containers
---------------------------
(base) binildass-MacBook-Pro:~ binil$ docker ps -a
CONTAINER ID   IMAGE                                      COMMAND                  CREATED         STATUS                      PORTS                      NAMES
ce87baac5629   ecom/product-web                           "java -jar app.jar"      6 minutes ago   Up 6 minutes                0.0.0.0:8080->8080/tcp     product-web
e5ba8e0b327a   ecom/product-server                        "java -jar app.jar"      6 minutes ago   Up 6 minutes                0.0.0.0:8081->8081/tcp     product-server
1ab728047d18   mongo:4.2.24                               "docker-entrypoint.s…"   6 minutes ago   Up 6 minutes                0.0.0.0:27017->27017/tcp   mongo
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

Open below link in Chrome

http://192.168.64.6:8080/product.html

Clean the Environment
---------------------
(base) binildass-MacBook-Pro:ch09-02 binil$ sh clean.sh 
[+] Running 4/4
 ⠿ Container product-web         Removed                                   0.2s
 ⠿ Container product-server      Removed                                   0.2s
 ⠿ Container mongo               Removed                                   0.2s
 ⠿ Network ch09-02_ecom-network  Removed                                   0.0s
Untagged: ecom/product-web:latest
Deleted: sha256:5da54836a2eb1d50886337564ce1cc7c50b90e6bfa3b10b1ec0be9242f1290f3
Untagged: ecom/product-server:latest
Deleted: sha256:3be0f774112a50bde4d9a418bb39bdc0ee4cb711c3d1cd387036de18d1f3114b
(base) binildass-MacBook-Pro:ch09-02 binil$ 