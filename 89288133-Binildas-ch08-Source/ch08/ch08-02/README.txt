Sample using Docker Network
Browser => ProductWeb μS => ProductServer μS => MongoDB

Build & Package Microservices, build images and run containers
--------------------------------------------------------------

(base) binildass-MacBook-Pro:ch08-02 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/ch08/ch08-02
(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch08-02 binil$ sh makeandrun.sh 
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
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  2.849 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.696 s]
[INFO] Ecom ............................................... SUCCESS [  0.036 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.749 s
[INFO] Finished at: 2023-05-17T19:18:44+05:30
[INFO] ------------------------------------------------------------------------
...
Successfully tagged ecom/product-web:latest
Successfully tagged ecom/product-server:latest
(base) binildass-MacBook-Pro:ch08-02 binil$ 


List Available Networks
-----------------------
(base) binildass-MacBook-Pro:~ binil$ docker network ls
NETWORK ID     NAME           DRIVER    SCOPE
dd54cc2c1315   bridge         bridge    local
99fcd2aebfe6   ecom-network   bridge    local
63c09f36910a   host           host      local
53b6c6340a22   none           null      local
(base) binildass-MacBook-Pro:~ binil$ 

List running containers
-----------------------

(base) binildass-MacBook-Pro:~ binil$ docker ps
CONTAINER ID   IMAGE                                      COMMAND                  CREATED         STATUS         PORTS                      NAMES
3932dc2d5d76   ecom/product-web                           "java -jar /ecom.jar"    3 minutes ago   Up 3 minutes   0.0.0.0:8080->8080/tcp     product-web
b0e6ec849808   ecom/product-server                        "java -jar /ecom.jar"    3 minutes ago   Up 3 minutes   0.0.0.0:8081->8081/tcp     product-server
8ad4240df7b8   mongo:4.2.24                               "docker-entrypoint.s…"   3 minutes ago   Up 3 minutes   0.0.0.0:27017->27017/tcp   mongo
...
(base) binildass-MacBook-Pro:ch08-01 binil$ 

Inspect ecom-network
--------------------
(base) binildass-MacBook-Pro:~ binil$ docker network inspect ecom-network
[
    {
        "Name": "ecom-network",
        "Id": "99fcd2aebfe609f96d1c33a263ce8da4e165a64de0337162b0b24573b367e873",
        "Created": "2023-05-17T13:48:47.179657086Z",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": {},
            "Config": [
                {
                    "Subnet": "172.22.0.0/16",
                    "Gateway": "172.22.0.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "3932dc2d5d76d997753f3fafc9d9b76d136b77c60d24c0460676dba80cd7a1ce": {
                "Name": "product-web",
                "EndpointID": "b72e18fe19cb8fb35880101ba966cae0c7540e459f275be379c77a4feab7dc1c",
                "MacAddress": "02:42:ac:16:00:04",
                "IPv4Address": "172.22.0.4/16",
                "IPv6Address": ""
            },
            "8ad4240df7b8f6f604a424d292eb41ae0a20d82ae727b36afa2e237a4ed350cb": {
                "Name": "mongo",
                "EndpointID": "1188ba315d6bcb669447c33eb4a3ddf2158686ec06705a5f04c5f6791e74c7eb",
                "MacAddress": "02:42:ac:16:00:02",
                "IPv4Address": "172.22.0.2/16",
                "IPv6Address": ""
            },
            "b0e6ec84980829078b6398658de5e1ff9a94a005545769900c06acad1eb57c38": {
                "Name": "product-server",
                "EndpointID": "975c7c7f33cc8cb199f4fa65cf8029a7e1cce983f74dc6d42feec26ea0525e1e",
                "MacAddress": "02:42:ac:16:00:03",
                "IPv4Address": "172.22.0.3/16",
                "IPv6Address": ""
            }
        },
        "Options": {},
        "Labels": {}
    }
]
(base) binildass-MacBook-Pro:~ binil$ 

View Product Server Logs
------------------------
(base) binildass-MacBook-Pro:01-ProductServer binil$ docker logs --follow b0e6ec849808

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

...
2023-05-17 13:49:02 INFO  InitializationComponent.init:47 - Start...
2023-05-17 13:49:02 DEBUG InitializationComponent.init:51 - Deleting all existing data on start...
2023-05-17 13:49:03 DEBUG InitializationComponent.init:56 - Creating initial data on start...
2023-05-17 13:49:03 INFO  InitializationComponent.init:105 - End
2023-05-17 13:49:05 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroservice...

View Product Web Logs
---------------------
(base) binildass-MacBook-Pro:02-ProductWeb binil$ docker logs --follow 3932dc2d5d76

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

...
2023-05-17 13:49:01 INFO  InitializationComponent.init:37 - Start
2023-05-17 13:49:01 DEBUG InitializationComponent.init:39 - Doing Nothing...
2023-05-17 13:49:01 INFO  InitializationComponent.init:41 - End
2023-05-17 13:49:03 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroservice...

Test Microservice
-----------------
(base) binildass-MacBook-Pro:~ binil$ minikube ip
192.168.64.6
(base) binildass-MacBook-Pro:~ binil$ 

http://192.168.64.6:8080/product.html

Clean the Environment
---------------------
(base) binildass-MacBook-Pro:ch08-02 binil$ sh clean.sh 
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
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  0.082 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.013 s]
[INFO] Ecom ............................................... SUCCESS [  0.035 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.298 s
[INFO] Finished at: 2023-05-17T19:26:34+05:30
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
(base) binildass-MacBook-Pro:ch08-02 binil$ 




