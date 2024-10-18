Microservices and PostgreSQL in Minikube and Rest Template (Full CRUD)

            ProductWeb μS => ProductServer μS => PostgreSQLDB
                ^                                     ^
                |                                     |
Browser =>    Ingress  ------------------------->  Adminer

Container Deployment Architecture
---------------------------------

   @/
  /| ->Browser ---------------------------> 8080[frontend network]
  / \     |                                            |
(User)    |                                            |
          |                                            |
          |     +--------------------+       +--------------------+
          |     | frontend service   |       |  frontend service  |
          +---->|      Adminer       |       |   "product-web"    |
                +--------------------+       +--------------------+
                          |                            |
                          |                    [backend network]
                          |                            |
                          |                  +--------------------+
                          |                  |  business service  |
                          |                  |  "product-server"  |
                          |                  +--------------------+
                          |                            |
                          |                    [backend network]
                          |                            |
                          |                  +--------------------+
                          |                  |  backend service   |
                          +----------------->|   "PostgreSQL"     |
                                             +--------------------+ 
                                                       |
                                               [backend network]
                                                       |
                                                       |r+w
                                              ___________________
                                             ( persistent volume )
                                              \_________________/


Start minikube
---------------
(base) binildass-MacBook-Pro:ch11-03 binil$ minikube start

config minikube to accesss local docker repository (from where we started minikube)
--------------------------
(base) binildass-MacBook-Pro:ch11-03 binil$ eval $(minikube docker-env)

configure host file:
--------------------
(base) binildass-MacBook-Pro:ch11-03 binil$ minikube ip
192.168.64.5
(base) binildass-MacBook-Pro:~ binil$ sudo nano /etc/hosts

192.168.64.5  adminer.acme.com
192.168.64.5  products.acme.com

(base) binildass-MacBook-Pro:~ binil$ ^O (To Save)
(base) binildass-MacBook-Pro:~ binil$ ^X (To Exit)
(base) binildass-MacBook-Pro:~ sudo killall -HUP mDNSResponder
--------------------

Ingress addon in minikube
-------------------------
(base) binildass-MacBook-Pro:~ binil$ kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/nginx-0.30.0/deploy/static/mandatory.yaml
(base) binildass-MacBook-Pro:~ binil$ minikube addons enable ingress
🌟  The 'ingress' addon is enabled
(base) binildass-MacBook-Pro:~ binil$ 

Build & Package Application, create docker image & Run Kubernetes Pods
----------------------------------------------------------------------

/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-03
(base) binildass-MacBook-Pro:ch11-03 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch11-03 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-03
(base) binildass-MacBook-Pro:ch11-03 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch11-03 binil$ sh makeandrun.sh 
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO] 
[INFO] Ecom-Product-Server-Microservice                                   [jar]
[INFO] Ecom-Product-Web-Microservice                                      [jar]
[INFO] Ecom                                                               [pom]
[INFO] 
[INFO] -------< com.acme.ecom.product:Ecom-Product-Server-Microservice >-------
[INFO] Building Ecom-Product-Server-Microservice 0.0.1-SNAPSHOT           [1/3]
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
...
[INFO] 
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  2.975 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.660 s]
[INFO] Ecom ............................................... SUCCESS [  0.020 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.850 s
[INFO] Finished at: 2023-05-28T22:17:15+05:30
[INFO] ------------------------------------------------------------------------
...
Successfully built 513021567259
Successfully tagged ecom/product-web:latest
Successfully tagged ecom/product-server:latest
configmap/postgres-config created
persistentvolumeclaim/postgres-persistent-volume-claim created
deployment.apps/postgres created
service/postgres created
deployment.apps/adminer created
service/adminer created
deployment.apps/product-server created
service/product-server created
deployment.apps/product-web created
service/product-web created
ingress.networking.k8s.io/ingress-service created
http://192.168.64.6:31858
NAME                              READY   STATUS    RESTARTS   AGE
adminer-847f44cbd4-hg4gf          1/1     Running   0          5s
postgres-89dbf9fd9-vr8k2          0/1     Pending   0          5s
product-server-5bf9db77dc-v9jl7   1/1     Running   0          4s
product-web-7496dc46f9-fqfkq      1/1     Running   0          4s
NAME             TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)          AGE
adminer          ClusterIP   10.104.83.51     <none>        8080/TCP         4s
kubernetes       ClusterIP   10.96.0.1        <none>        443/TCP          11d
postgres         ClusterIP   10.104.112.235   <none>        5432/TCP         5s
product-server   ClusterIP   10.104.42.20     <none>        8081/TCP         4s
product-web      NodePort    10.106.179.41    <none>        8080:31858/TCP   4s
(base) binildass-MacBook-Pro:ch11-03 binil$ 


Describe Ingress
----------------
(base) binildass-MacBook-Pro:~ binil$ kubectl describe ingress
Name:             ingress-service
Labels:           <none>
Namespace:        default
Address:          192.168.64.6
Ingress Class:    <none>
Default backend:  <default>
Rules:
  Host                Path  Backends
  ----                ----  --------
  adminer.acme.test   
                      /   adminer:8080 (10.244.1.234:8080)
  products.acme.test  
                      /   product-web:8080 (10.244.1.233:8080)
Annotations:          kubernetes.io/ingress.class: nginx
Events:
  Type    Reason  Age                    From                      Message
  ----    ------  ----                   ----                      -------
  Normal  Sync    7m15s (x2 over 7m44s)  nginx-ingress-controller  Scheduled for sync
(base) binildass-MacBook-Pro:~ binil$ 

Start viewing Product Server Microservice logs
----------------------------------------------
(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ kubectl --tail 60 logs -f product-server-5bf9db77dc-v9jl7
 
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2023-05-28 16:48:03 INFO  StartupInfoLogger.logStarting:51 - Starting EcomProductMicroservice...
...

UPDATE SUMMARY
Run:                          1
Previously run:               0
Filtered out:                 0
-------------------------------
Total change sets:            1

Liquibase: Update has been successful.
2023-05-28 16:48:11 INFO  InitializationComponent.init:45 - Start
2023-05-28 16:48:11 DEBUG InitializationComponent.init:49 - Deleting all products in DB ...
2023-05-28 16:48:11 DEBUG InitializationComponent.init:68 - Creating 2 new products ...
Hibernate: insert into product (code, prodname, price, title) values (?, ?, ?, ?)
Hibernate: insert into product (code, prodname, price, title) values (?, ?, ?, ?)
2023-05-28 16:48:11 INFO  InitializationComponent.init:74 - End
2023-05-28 16:48:13 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroserviceApplication in 11.201 seconds (process running for 13.333)
2023-05-28 16:48:46 INFO  ProductRestController.getAllProducts:124 - Start
...

Start viewing Product Web Microservice logs
-------------------------------------------
(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ kubectl --tail 60 logs -f product-web-7496dc46f9-fqfkq

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2023-05-28 16:47:29 INFO  StartupInfoLogger.logStarting:51 - Starting EcomProductMicroservice...
...
2023-05-28 16:47:35 INFO  InitializationComponent.init:37 - Start
2023-05-28 16:47:35 DEBUG InitializationComponent.init:39 - Doing Nothing...
2023-05-28 16:47:35 INFO  InitializationComponent.init:41 - End
2023-05-28 16:47:36 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroservice...
2023-05-28 16:48:46 INFO  ProductRestController.getAllProducts:75 - Start
2023-05-28 16:48:46 INFO  ProductRestController.getAllProducts:82 - Ending...


Test the Client
---------------
Open below link in Chrome

http://products.acme.test/product.html

http://adminer.acme.test
http://products.acme.test/productsweb

Clean the Environment
---------------------
(base) binildass-MacBook-Pro:ch11-03 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-03
(base) binildass-MacBook-Pro:ch11-03 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch11-03 binil$ sh clean.sh 
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
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  0.114 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.009 s]
[INFO] Ecom ............................................... SUCCESS [  0.036 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.384 s
[INFO] Finished at: 2023-05-28T22:46:54+05:30
[INFO] ------------------------------------------------------------------------
service "product-web" deleted
deployment.apps "product-web" deleted
service "product-server" deleted
deployment.apps "product-server" deleted
service "adminer" deleted
deployment.apps "adminer" deleted
service "postgres" deleted
deployment.apps "postgres" deleted
persistentvolumeclaim "postgres-persistent-volume-claim" deleted
configmap "postgres-config" deleted
ingress.networking.k8s.io "ingress-service" deleted
Untagged: ecom/product-web:latest
Untagged: ecom/product-server:latest
(base) binildass-MacBook-Pro:ch11-03 binil$ 