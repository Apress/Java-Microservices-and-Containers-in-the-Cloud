Sample using mounted files for MongoDB, and then use Kubernetes
Browser => ProductWeb μS => ProductServer μS => MongoDB μS =mount=> File

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

Start Minikube
--------------
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
(base) binildass-MacBook-Pro:ch10-01 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch10/ch10-01
(base) binildass-MacBook-Pro:ch10-01 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch10-01 binil$ sh makeandrun.sh 
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
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  3.687 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.842 s]
[INFO] Ecom ............................................... SUCCESS [  0.043 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
...
Successfully tagged ecom/product-web:latest
Successfully tagged ecom/product-server:latest
persistentvolume/mongo-data-db created
persistentvolumeclaim/mongo-data-db created
statefulset.apps/mongo-cluster created
service/mongo created
service/mongo-nodeport created
deployment.apps/product-server created
service/product-server-nodeport created
deployment.apps/product-web created
service/product-web created
http://192.168.64.6:30503
NAME                              READY   STATUS    RESTARTS   AGE
mongo-cluster-0                   1/1     Running   0          5s
product-server-6fb88b6849-q2pfx   1/1     Running   0          4s
product-web-5dfc886d6d-8cl7t      1/1     Running   0          4s
NAME                      TYPE           CLUSTER-IP       EXTERNAL-IP   PORT(S)           AGE
kubernetes                ClusterIP      10.96.0.1        <none>        443/TCP           8d
mongo                     ClusterIP      10.98.30.13      <none>        27017/TCP         4s
mongo-nodeport            NodePort       10.110.211.153   <none>        27017:30001/TCP   4s
product-server            ClusterIP      10.102.46.205    <none>        8081/TCP          27h
product-server-nodeport   NodePort       10.109.197.184   <none>        8081:30002/TCP    4s
product-web               LoadBalancer   10.98.43.63      <pending>     8080:30503/TCP    4s
(base) binildass-MacBook-Pro:ch10-01 binil$ 

Start viewing Product Server Microservice logs
----------------------------------------------
(base) binildass-MacBook-Pro:~ binil$ kubectl --tail 15 logs -f product-server-6fb88b6849-q2pfx
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)
…
2023-05-25 13:48:21 INFO  InitializationComponent.init:47 - Start...
2023-05-25 13:48:21 DEBUG InitializationComponent.init:51 - Deleting all existing data…
2023-05-25 13:48:22 DEBUG InitializationComponent.init:56 - Creating initial data…
2023-05-25 13:48:22 INFO  InitializationComponent.init:105 - End
2023-05-25 13:48:23 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroservice…

Start viewing Product Web Microservice logs
--------------------------------------------
(base) binildass-MacBook-Pro:~ binil$ kubectl --tail 15 logs -f product-web-5dfc886d6d-8cl7t
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

…
2023-05-25 13:48:20 INFO  InitializationComponent.init:37 - Start
2023-05-25 13:48:20 DEBUG InitializationComponent.init:39 - Doing Nothing...
2023-05-25 13:48:20 INFO  InitializationComponent.init:41 - End
2023-05-25 13:48:21 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroservice…

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


Test the Client
---------------
http://192.168.64.6:30503/product.html

Clean the Environment
---------------------
(base) binildass-MacBook-Pro:ch10-01 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch10/ch10-01
(base) binildass-MacBook-Pro:ch10-01 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch10-01 binil$ sh clean.sh 
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
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  0.108 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.008 s]
[INFO] Ecom ............................................... SUCCESS [  0.037 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.366 s
[INFO] Finished at: 2023-05-19T16:40:53+05:30
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
Untagged: ecom/product-web:latest
Deleted: sha256:a95163453522893df246e09bf9107054f9ba6981854f9bcada1f935db332aaa0
Deleted: sha256:b3314f92cc1c9a2d4baae0dfd3ea70fe5de7266897cb547c9de29aa0334b8b71
Deleted: sha256:18cdd0020b01d103c0820eb04160ed02113d14666c1216c6035b740d6b39e886
Untagged: ecom/product-server:latest
Deleted: sha256:0bb0eec7bd550cbb760423f561e6d1da989a4597105749c255c93d07bc29d930
Deleted: sha256:bc252128ee3a093081b69796bbf9e1f5422b6d75fd4844978e91ec4bab474938
Deleted: sha256:0b124c6eeab71497fbd4c0b0d1c98cbcac0eed8f9ee37744ee9be14da36da28d
(base) binildass-MacBook-Pro:ch10-01 binil$ 
