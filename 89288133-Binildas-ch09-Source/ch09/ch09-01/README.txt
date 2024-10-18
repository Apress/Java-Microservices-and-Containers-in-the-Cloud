
Microservices in Docker using PostgreSQL in Docker and Rest Template (Full CRUD)
Browser => ProductWeb μS => ProductServer μS => PostgreSQLDB

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
                    |   "PostgreSQL"     |=======( persistent volume )
                    +--------------------+        \_________________/

Build and Run in one go
-----------------------
(base) binildass-MacBook-Pro:ch09-01 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch09/ch09-01
(base) binildass-MacBook-Pro:ch09-01 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch09-01 binil$ sh makeandrun.sh 
[+] Running 0/2
 ⠿ web Error                                                              13.9s
 ⠿ server Error                                                           13.9s
[+] Building 8.6s (21/21) FINISHED                                              
 => [ecom/product-web internal] load build definition from Dockerfile      0.0s
 => => transferring dockerfile: 80B                                        0.0s
 => [ecom/product-web internal] load .dockerignore                         0.0s
 => => transferring context: 2B                                            0.0s
 => [ecom/product-server internal] load build definition from Dockerfile   0.0s
 => => transferring dockerfile: 80B                                        0.0s
 => [ecom/product-server internal] load metadata for docker.io/library/op  0.0s
 => [ecom/product-server internal] load metadata for docker.io/library/ma  8.5s
 => [ecom/product-server internal] load .dockerignore                      0.0s
 => => transferring context: 2B                                            0.0s
 => [ecom/product-web build 1/6] FROM docker.io/library/maven:3.8.1-openj  0.0s
 => [ecom/product-web stage-1 1/2] FROM docker.io/library/openjdk:17-jdk-  0.0s
 => [ecom/product-server internal] load build context                      0.0s
 => => transferring context: 3.37kB                                        0.0s
 => [ecom/product-web internal] load build context                         0.0s
 => => transferring context: 3.64kB                                        0.0s
 => CACHED [ecom/product-web build 2/6] RUN mkdir -p /workspace            0.0s
 => CACHED [ecom/product-web build 3/6] WORKDIR /workspace                 0.0s
 => CACHED [ecom/product-server build 4/6] COPY pom.xml /workspace         0.0s
 => CACHED [ecom/product-server build 5/6] COPY src /workspace/src         0.0s
 => CACHED [ecom/product-server build 6/6] RUN mvn -f pom.xml clean packa  0.0s
 => CACHED [ecom/product-server stage-1 2/2] COPY --from=build /workspace  0.0s
 => [ecom/product-server] exporting to image                               0.0s
 => => exporting layers                                                    0.0s
 => => writing image sha256:d7ae9155f874c06a8f602a3329f08424b05ab633940be  0.0s
 => => naming to docker.io/ecom/product-web                                0.0s
 => => writing image sha256:dbba8f1ffd736cdccdc06a0295b11c644d0b1a39438d0  0.0s
 => => naming to docker.io/ecom/product-server                             0.0s
 => CACHED [ecom/product-web build 4/6] COPY pom.xml /workspace            0.0s
 => CACHED [ecom/product-web build 5/6] COPY src /workspace/src            0.0s
 => CACHED [ecom/product-web build 6/6] RUN mvn -f pom.xml clean package   0.0s
 => CACHED [ecom/product-web stage-1 2/2] COPY --from=build /workspace/ta  0.0s
[+] Running 4/3
 ⠿ Network ch09-01_ecom-network  Created                                   0.1s
 ⠿ Container postgres-docker     Created                                   0.0s
 ⠿ Container product-server      Created                                   0.0s
 ⠿ Container product-web         Created                                   0.0s
Attaching to postgres-docker, product-server, product-web
postgres-docker  | 
postgres-docker  | PostgreSQL Database directory appears to contain a database; Skipping initialization
postgres-docker  | 
postgres-docker  | 2023-05-18 14:16:09.496 UTC [1] LOG:  starting PostgreSQL 15.3 on x86_64-pc-linux-musl, compiled by gcc (Alpine 12.2.1_git20220924-r10) 12.2.1 20220924, 64-bit
postgres-docker  | 2023-05-18 14:16:09.497 UTC [1] LOG:  listening on IPv4 address "0.0.0.0", port 5432
postgres-docker  | 2023-05-18 14:16:09.497 UTC [1] LOG:  listening on IPv6 address "::", port 5432
postgres-docker  | 2023-05-18 14:16:09.500 UTC [1] LOG:  listening on Unix socket "/var/run/postgresql/.s.PGSQL.5432"
postgres-docker  | 2023-05-18 14:16:09.503 UTC [24] LOG:  database system was shut down at 2023-05-18 14:14:35 UTC
postgres-docker  | 2023-05-18 14:16:09.509 UTC [1] LOG:  database system is ready to accept connections
product-server   | 
product-server   |   .   ____          _            __ _ _
product-server   |  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
product-server   | ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
product-server   |  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
product-server   |   '  |____| .__|_| |_|_| |_\__, | / / / /
product-server   |  =========|_|==============|___/=/_/_/_/
product-server   |  :: Spring Boot ::                (v3.2.0)
product-server   | 
product-web      | 
product-web      |   .   ____          _            __ _ _
product-web      |  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
product-web      | ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
product-web      |  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
product-web      |   '  |____| .__|_| |_|_| |_\__, | / / / /
product-web      |  =========|_|==============|___/=/_/_/_/
product-web      |  :: Spring Boot ::                (v3.2.0)
product-web      | 
product-server   | 2023-05-18 14:16:14 INFO  StartupInfoLogger.logStarting:51 - Starting EcomProductMicroserviceApplication v0.0.1-SNAPSHOT using Java 17-ea with PID 1 (/app.jar started by root in /)
product-server   | 2023-05-18 14:16:14 DEBUG StartupInfoLogger.logStarting:52 - Running with Spring Boot v3.0.6, Spring v6.0.8
product-server   | 2023-05-18 14:16:14 INFO  SpringApplication.logStartupProfileInfo:632 - No active profile set, falling back to 1 default profile: "default"
product-web      | 2023-05-18 14:16:14 INFO  StartupInfoLogger.logStarting:51 - Starting EcomProductMicroserviceApplication v0.0.1-SNAPSHOT using Java 17-ea with PID 1 (/app.jar started by root in /)
product-web      | 2023-05-18 14:16:14 DEBUG StartupInfoLogger.logStarting:52 - Running with Spring Boot v3.0.6, Spring v6.0.8
product-web      | 2023-05-18 14:16:14 INFO  SpringApplication.logStartupProfileInfo:632 - No active profile set, falling back to 1 default profile: "default"
product-web      | 2023-05-18 14:16:19 INFO  InitializationComponent.init:37 - Start
product-web      | 2023-05-18 14:16:19 DEBUG InitializationComponent.init:39 - Doing Nothing...
product-web      | 2023-05-18 14:16:19 INFO  InitializationComponent.init:41 - End
product-web      | 2023-05-18 14:16:21 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroserviceApplication in 8.483 seconds (process running for 10.976)
product-server   | Database is up to date, no changesets to execute
product-server   | 
product-server   | UPDATE SUMMARY
product-server   | Run:                          0
product-server   | Previously run:               1
product-server   | Filtered out:                 0
product-server   | -------------------------------
product-server   | Total change sets:            1
product-server   | 
product-server   | 2023-05-18 14:16:25 INFO  InitializationComponent.init:42 - Start
product-server   | 2023-05-18 14:16:25 DEBUG InitializationComponent.init:46 - Deleting all products in DB during initialization
product-server   | Hibernate: select p1_0.productid,p1_0.code,p1_0.prodname,p1_0.price,p1_0.title from product p1_0
product-server   | Hibernate: delete from product where productid=?
product-server   | Hibernate: delete from product where productid=?
product-server   | 2023-05-18 14:16:25 DEBUG InitializationComponent.init:63 - Creating 2 new products in DB during initialization
product-server   | Hibernate: insert into product (code, prodname, price, title) values (?, ?, ?, ?)
product-server   | Hibernate: insert into product (code, prodname, price, title) values (?, ?, ?, ?)
product-server   | 2023-05-18 14:16:25 INFO  InitializationComponent.init:68 - End
product-server   | 2023-05-18 14:16:26 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroserviceApplication in 13.921 seconds (process running for 16.898)

List the running Containers
---------------------------
(base) binildass-MacBook-Pro:~ binil$ docker ps -a
CONTAINER ID   IMAGE                                      COMMAND                  CREATED          STATUS                        PORTS                    NAMES
e4a759d23d06   ecom/product-web                           "java -jar app.jar"      2 minutes ago    Up 2 minutes                  0.0.0.0:8080->8080/tcp   product-web
a8417e698525   ecom/product-server                        "java -jar app.jar"      2 minutes ago    Up 2 minutes                  0.0.0.0:8081->8081/tcp   product-server
5aaae71c94ca   postgres:15.3-alpine3.18                   "docker-entrypoint.s…"   2 minutes ago    Up 2 minutes                  0.0.0.0:5432->5432/tcp   postgres-docker
d8e0a2641c9f   0d4c0564c465  
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
Open below link in Chrome

http://192.168.64.6:8080/product.html

================

(base) binildass-MacBook-Pro:ch05-05 binil$ docker exec -it postgres-docker bash
bash-5.1# psql -U postgres
psql (9.6.21)
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
         3 | Test01      | Test01        | Test01                                     |   100
(3 rows)

productdb=# \q
bash-5.1# exit
exit
(base) binildass-MacBook-Pro:ch05-05 binil$ 

Clean the Environment
---------------------
(base) binildass-MacBook-Pro:ch09-01 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch09/ch09-01
(base) binildass-MacBook-Pro:ch09-01 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch09-01 binil$ sh clean.sh 
[+] Running 4/4
 ⠿ Container product-web         Removed                                   0.2s
 ⠿ Container product-server      Removed                                   0.2s
 ⠿ Container postgres-docker     Removed                                   0.1s
 ⠿ Network ch09-01_ecom-network  Removed                                   0.1s
Untagged: ecom/product-web:latest
Deleted: sha256:d7ae9155f874c06a8f602a3329f08424b05ab633940be2e1f54f062f5e42c2a0
Untagged: ecom/product-server:latest
Deleted: sha256:dbba8f1ffd736cdccdc06a0295b11c644d0b1a39438d0a96bd875b70974cefec
(base) binildass-MacBook-Pro:ch09-01 binil$ 

Server side logs while Cleaning the Environment
-----------------------------------------------
...
product-web exited with code 143
product-web exited with code 0
product-server exited with code 143
product-server exited with code 0
postgres-docker  | 2023-05-18 14:20:05.879 UTC [1] LOG:  received fast shutdown request
postgres-docker  | 2023-05-18 14:20:05.881 UTC [1] LOG:  aborting any active transactions
postgres-docker  | 2023-05-18 14:20:05.885 UTC [1] LOG:  background worker "logical replication launcher" (PID 27) exited with exit code 1
postgres-docker  | 2023-05-18 14:20:05.885 UTC [22] LOG:  shutting down
postgres-docker  | 2023-05-18 14:20:05.886 UTC [22] LOG:  checkpoint starting: shutdown immediate
postgres-docker  | 2023-05-18 14:20:05.892 UTC [22] LOG:  checkpoint complete: wrote 7 buffers (0.0%); 0 WAL file(s) added, 0 removed, 0 recycled; write=0.002 s, sync=0.002 s, total=0.007 s; sync files=6, longest=0.001 s, average=0.001 s; distance=2 kB, estimate=2 kB
postgres-docker  | 2023-05-18 14:20:05.897 UTC [1] LOG:  database system is shut down
postgres-docker exited with code 0
(base) binildass-MacBook-Pro:ch09-01 binil$ 
