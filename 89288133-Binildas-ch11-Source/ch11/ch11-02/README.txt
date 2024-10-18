
Microservices in Kubernetes using MongoDB and Rest Template (Full CRUD)
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

Start Minikube
--------------
(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ minikube start
😄  minikube v1.30.1 on Darwin 13.3
✨  Using the hyperkit driver based on existing profile
👍  Starting control plane node minikube in cluster minikube
🔄  Restarting existing hyperkit VM for "minikube" ...
🐳  Preparing Kubernetes v1.26.3 on Docker 20.10.23 ...
🔗  Configuring bridge CNI (Container Networking Interface) ...
🔎  Verifying Kubernetes components...
    ▪ Using image gcr.io/k8s-minikube/storage-provisioner:v5
    ▪ Using image gcr.io/k8s-minikube/minikube-ingress-dns:0.0.2
    ▪ Using image registry.k8s.io/ingress-nginx/controller:v1.7.0
    ▪ Using image registry.k8s.io/ingress-nginx/kube-webhook-certgen:v20230312-helm-chart-4.5.2-28-g66a760794
    ▪ Using image registry.k8s.io/ingress-nginx/kube-webhook-certgen:v20230312-helm-chart-4.5.2-28-g66a760794
🔎  Verifying ingress addon...
🌟  Enabled addons: ingress-dns, storage-provisioner, default-storageclass, ingress
🏄  Done! kubectl is now configured to use "minikube" cluster and "default" namespace by default
(base) binildass-MacBook-Pro:~ binil$ 


Inspect or Create folder /home/docker/binil/mongodata
-----------------------------------------------------

(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ minikube ssh
                         _             _            
            _         _ ( )           ( )           
  ___ ___  (_)  ___  (_)| |/')  _   _ | |_      __  
/' _ ` _ `\| |/' _ `\| || , <  ( ) ( )| '_`\  /'__`\
| ( ) ( ) || || ( ) || || |\`\ | (_) || |_) )(  ___/
(_) (_) (_)(_)(_) (_)(_)(_) (_)`\___/'(_,__/'`\____)

$ pwd
/home/docker
$ ls
$ mkdir -p binil/mongodata
$ ls
binil
$ cd binil/mongodata
$ pwd
/home/docker/binil/mongodata
$ ls
$ 

Build & Package Application, create docker image & Run Kubernetes Pods
----------------------------------------------------------------------

(base) binildass-MacBook-Pro:ch11-02 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-02
(base) binildass-MacBook-Pro:ch11-02 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch11-02 binil$ sh makeandrun.sh 
[INFO] Scanning for projects...
[INFO] 
...
[INFO] 
[INFO] Kafka Request Reply utility ........................ SUCCESS [  1.129 s]
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  1.242 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.481 s]
[INFO] Ecom ............................................... SUCCESS [  0.002 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.023 s
[INFO] Finished at: 2023-05-19T20:17:30+05:30
[INFO] ------------------------------------------------------------------------
DEPRECATED: The legacy builder is deprecated and will be removed in a future release.
            Install the buildx component to build images with BuildKit:
            https://docs.docker.com/go/buildx/

Sending build context to Docker daemon  85.98MB
Step 1/5 : FROM openjdk:17-jdk-alpine
 ---> 264c9bdce361
Step 2/5 : VOLUME /tmp
 ---> Using cache
 ---> 3965cd0089ca
Step 3/5 : ARG JAR_FILE
 ---> Using cache
 ---> 3caec54372e5
Step 4/5 : COPY ${JAR_FILE} ecom.jar
 ---> 60785fca313d
Step 5/5 : ENTRYPOINT ["java","-jar","/ecom.jar"]
 ---> Running in ca76206829ff
Removing intermediate container ca76206829ff
 ---> 29345d8c2803
Successfully built 29345d8c2803
Successfully tagged ecom/product-web:latest
DEPRECATED: The legacy builder is deprecated and will be removed in a future release.
            Install the buildx component to build images with BuildKit:
            https://docs.docker.com/go/buildx/

Sending build context to Docker daemon  85.98MB
Step 1/5 : FROM openjdk:17-jdk-alpine
 ---> 264c9bdce361
Step 2/5 : VOLUME /tmp
 ---> Using cache
 ---> 3965cd0089ca
Step 3/5 : ARG JAR_FILE
 ---> Using cache
 ---> 3caec54372e5
Step 4/5 : COPY ${JAR_FILE} ecom.jar
 ---> b92d95617e7c
Step 5/5 : ENTRYPOINT ["java","-jar","/ecom.jar"]
 ---> Running in 4f21c9daff6e
Removing intermediate container 4f21c9daff6e
 ---> 036cdd098891
Successfully built 036cdd098891
Successfully tagged ecom/product-server:latest
deployment.apps/zookeeper-deployment created
deployment.apps/kafka-1-deployment created
service/zookeeper-ip-service created
service/kafka-1-ip-service created
persistentvolume/mongo-data-db created
persistentvolumeclaim/mongo-data-db created
statefulset.apps/mongo-cluster created
service/mongo created
service/mongo-nodeport created
deployment.apps/product-server created
service/product-server created
service/product-server-nodeport created
deployment.apps/product-web created
service/product-web created
http://192.168.64.6:32627
NAME                                   READY   STATUS              RESTARTS   AGE
kafka-1-deployment-7bcc6dd87f-jrdjx    1/1     Running             0          9s
mongo-cluster-0                        1/1     Running             0          8s
product-server-5cc8967574-6p249        0/1     ContainerCreating   0          6s
product-server-5cc8967574-7gnz9        0/1     ContainerCreating   0          6s
product-server-5cc8967574-jr2lv        0/1     ContainerCreating   0          6s
product-web-ff4f65986-9plf7            0/1     ContainerCreating   0          5s
product-web-ff4f65986-qd9bs            0/1     ContainerCreating   0          5s
product-web-ff4f65986-s8hm4            0/1     ContainerCreating   0          5s
zookeeper-deployment-886ff5f87-nv5xn   0/1     ContainerCreating   0          9s
NAME                      TYPE           CLUSTER-IP      EXTERNAL-IP   PORT(S)           AGE
kafka-1-ip-service        ClusterIP      10.108.5.80     <none>        9092/TCP          9s
kubernetes                ClusterIP      10.96.0.1       <none>        443/TCP           2d8h
mongo                     ClusterIP      10.103.88.174   <none>        27017/TCP         8s
mongo-nodeport            NodePort       10.108.6.234    <none>        27017:30001/TCP   8s
product-server            ClusterIP      10.103.49.104   <none>        8081/TCP          6s
product-server-nodeport   NodePort       10.109.23.86    <none>        8081:30002/TCP    6s
product-web               LoadBalancer   10.99.185.187   <pending>     8080:32627/TCP    5s
zookeeper-ip-service      ClusterIP      10.107.235.56   <none>        2181/TCP          9s
(base) binildass-MacBook-Pro:ch11-02 binil$ 


Start viewing Product Server Microservice 01 logs
-------------------------------------------------
(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ kubectl --tail 35 logs -f product-server-5cc8967574-6p249
...

Start viewing Product Server Microservice 02 logs
-------------------------------------------------
(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ kubectl --tail 35 logs -f product-server-5cc8967574-7gnz9
...

Start viewing Product Server Microservice 03 logs
-------------------------------------------------
(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ kubectl --tail 35 logs -f product-server-5cc8967574-jr2lv

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2023-05-19 14:47:58 INFO  StartupInfoLogger.logStarting:51 - Starting EcomProductServerMicroservice...
2023-05-19 14:47:59 DEBUG StartupInfoLogger.logStarting:52 - Running with Spring Boot v3.0.6...
2023-05-19 14:48:23 INFO  InitializationComponent.init:45 - Start
2023-05-19 14:48:23 DEBUG InitializationComponent.init:46 - Cleaning DB & Creating few products
2023-05-19 14:48:26 INFO  InitializationComponent.init:68 - End
2023-05-19 14:48:47 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductServer...
...

Inspect folder /home/docker/binil/mongodata
-------------------------------------------

(base) binildass-MacBook-Pro:~ binil$ minikube ssh
                         _             _            
            _         _ ( )           ( )           
  ___ ___  (_)  ___  (_)| |/')  _   _ | |_      __  
/' _ ` _ `\| |/' _ `\| || , <  ( ) ( )| '_`\  /'__`\
| ( ) ( ) || || ( ) || || |\`\ | (_) || |_) )(  ___/
(_) (_) (_)(_)(_) (_)(_)(_) (_)`\___/'(_,__/'`\____)

$ pwd
...
$ pwd
/home/docker/binil/mongodata
$ ls
$ ls
WiredTiger			       diagnostic.data
WiredTiger.lock			       index-1--1658350542916083623.wt
WiredTiger.turtle		       index-11--1658350542916083623.wt
WiredTiger.wt			       index-3--1658350542916083623.wt
WiredTigerLAS.wt		       index-5--1658350542916083623.wt
_mdb_catalog.wt			       index-6--1658350542916083623.wt
collection-0--1658350542916083623.wt   index-9--1658350542916083623.wt
collection-10--1658350542916083623.wt  journal
collection-2--1658350542916083623.wt   mongod.lock
collection-4--1658350542916083623.wt   sizeStorer.wt
collection-8--1658350542916083623.wt   storage.bson
$ 

Start viewing Product Web Microservice 01 logs
----------------------------------------------
(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ kubectl --tail 35 logs -f product-web-ff4f65986-9plf7
...

Start viewing Product Web Microservice 02 logs
----------------------------------------------
(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ kubectl --tail 35 logs -f product-web-ff4f65986-qd9bs
...

Start viewing Product Web Microservice 03 logs
----------------------------------------------
(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ kubectl --tail 35 logs -f product-web-ff4f65986-s8hm4

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2023-05-19 14:47:58 INFO  StartupInfoLogger.logStarting:51 - Starting EcomProductWebMicroservice...
2023-05-19 14:47:59 DEBUG StartupInfoLogger.logStarting:52 - Running with Spring Boot v3.0.6...
2023-05-19 14:48:42 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductWebMicroservice...
...

Test the Client
===================
http://192.168.64.6:32627/product.html

Scalability Testing
===================

Start cURL Client 01
--------------------
(base) binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/ch11/ch11-02/02-ProductWeb
(base) binildass-MacBook-Pro:02-ProductWeb binil$ sh pingrun1.sh 

Start cURL Client 02
--------------------
(base) binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/ch11/ch11-02/02-ProductWeb
(base) binildass-MacBook-Pro:02-ProductWeb binil$ sh pingrun2.sh 

Start cURL Client 03
--------------------
(base) binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/ch11/ch11-02/02-ProductWeb
(base) binildass-MacBook-Pro:02-ProductWeb binil$ sh pingrun3.sh

==========================

Clean the Environment
---------------------

(base) binildass-MacBook-Pro:ch11-02 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-02
(base) binildass-MacBook-Pro:ch11-02 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch11-02 binil$ sh clean.sh 
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO] 
[INFO] Kafka Request Reply utility                                        [jar]
[INFO] Ecom-Product-Server-Microservice                                   [jar]
[INFO] Ecom-Product-Web-Microservice                                      [jar]
[INFO] Ecom                                                               [pom]
[INFO] 
...
[INFO] 
[INFO] Kafka Request Reply utility ........................ SUCCESS [  0.087 s]
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  0.075 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.013 s]
[INFO] Ecom ............................................... SUCCESS [  0.003 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.415 s
[INFO] Finished at: 2023-05-19T20:41:43+05:30
[INFO] ------------------------------------------------------------------------
service "product-web" deleted
deployment.apps "product-web" deleted
service "product-server" deleted
service "product-server-nodeport" deleted
deployment.apps "product-server" deleted
service "mongo" deleted
service "mongo-nodeport" deleted
statefulset.apps "mongo-cluster" deleted
persistentvolumeclaim "mongo-data-db" deleted
persistentvolume "mongo-data-db" deleted
service "zookeeper-ip-service" deleted
service "kafka-1-ip-service" deleted
deployment.apps "zookeeper-deployment" deleted
deployment.apps "kafka-1-deployment" deleted
Untagged: ecom/product-web:latest
Deleted: sha256:29345d8c280349bce58be60ca9ac3023d436d00987ae0d1afceafe087a4b7936
Deleted: sha256:60785fca313d3a4856c2d0a8bb7c26429fd51ec2dcbd2e57121c2d9948e4e467
Deleted: sha256:2086e507dbdaf482367fed38d848d0a931a6f511ac7023fd4de9a8a01434a835
Untagged: ecom/product-server:latest
Deleted: sha256:036cdd0988915d0455e4f18046dd2484574e1c34c7b1ea21b6ee6da0f004da8c
Deleted: sha256:b92d95617e7cefc524909d0b1fd2d9d728ae2b26a2977418a4f056022f961ded
Deleted: sha256:284ec1dbc22314943f52984bbd160904b47e6ea34ec57ca30b4c7f12be8069c4
(base) binildass-MacBook-Pro:ch11-02 binil$ 
