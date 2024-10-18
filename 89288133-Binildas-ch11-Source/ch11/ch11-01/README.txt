
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


Build & Package Application, create docker image & Run Kubernetes Pods
----------------------------------------------------------------------

(base) binildass-MacBook-Pro:ch11-01 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-01
(base) binildass-MacBook-Pro:ch11-01 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch11-01 binil$ sh makeandrun.sh 
[INFO] Scanning for projects...
[INFO] 
[INFO] -------< se.callista.blog.synch_kafka:kafka-request-reply-util >--------
[INFO] Building Kafka Request Reply utility 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.272 s
[INFO] Finished at: 2023-05-26T23:52:51+05:30
[INFO] ------------------------------------------------------------------------
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
[INFO] Kafka Request Reply utility ........................ SUCCESS [  1.119 s]
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  2.534 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.499 s]
[INFO] Ecom ............................................... SUCCESS [  0.002 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
...
Successfully built e3650ec2a015
Successfully tagged ecom/product-server:latest
deployment.apps/zookeeper-deployment created
deployment.apps/kafka-1-deployment created
service/zookeeper-ip-service created
service/kafka-1-ip-service created
configmap/postgres-config created
persistentvolumeclaim/postgres-persistent-volume-claim created
deployment.apps/postgres created
service/postgres created
deployment.apps/product-server created
service/product-server created
deployment.apps/product-web created
service/product-web created
http://192.168.64.6:32535
NAME                                   READY   STATUS              RESTARTS   AGE
kafka-1-deployment-7bcc6dd87f-rxhkd    1/1     Running             0          8s
postgres-89dbf9fd9-g9jx8               1/1     Running             0          7s
product-server-5bb6569674-9dflb        0/1     ContainerCreating   0          5s
product-server-5bb6569674-g2488        0/1     ContainerCreating   0          5s
product-server-5bb6569674-nzlv2        0/1     ContainerCreating   0          5s
product-web-787d9ddf75-9m5c6           0/1     ContainerCreating   0          4s
product-web-787d9ddf75-g82vm           0/1     ContainerCreating   0          4s
product-web-787d9ddf75-tn8lk           0/1     ContainerCreating   0          4s
zookeeper-deployment-886ff5f87-qmnl6   1/1     Running             0          8s
NAME                   TYPE           CLUSTER-IP       EXTERNAL-IP   PORT(S)          AGE
kafka-1-ip-service     ClusterIP      10.98.44.163     <none>        9092/TCP         7s
kubernetes             ClusterIP      10.96.0.1        <none>        443/TCP          9d
postgres               ClusterIP      10.97.76.252     <none>        5432/TCP         7s
product-server         ClusterIP      10.106.100.232   <none>        8081/TCP         4s
product-web            LoadBalancer   10.110.112.90    <pending>     8080:32535/TCP   4s
zookeeper-ip-service   NodePort       10.111.204.18    <none>        2181:30059/TCP   7s
(base) binildass-MacBook-Pro:ch11-01 binil$ 


Start viewing Product Server Microservice 01 logs
-------------------------------------------------
(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ kubectl --tail 30 logs -f product-server-5bb6569674-9dflb
...

Start viewing Product Server Microservice 02 logs
-------------------------------------------------
(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ kubectl --tail 30 logs -f product-server-5bb6569674-g2488
...

Start viewing Product Server Microservice 03 logs
-------------------------------------------------
(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ kubectl --tail 30 logs -f product-server-5bb6569674-nzlv2

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2023-05-19 13:49:12 INFO  StartupInfoLogger.logStarting:51 - Starting EcomProductServerMicroservice...
...
Running Changeset: db/changelog/initial-schema_inventory.xml::product::Binildas
Running Changeset: db/changelog/initial-schema_inventory.xml::addAutoIncrement-product::Binildas
Running Changeset: db/changelog/initial-schema_inventory.xml::insert-product-01::Binildas
Running Changeset: db/changelog/initial-schema_inventory.xml::insert-product-02::Binildas

UPDATE SUMMARY
Run:                          4
Previously run:               0
Filtered out:                 0
-------------------------------
Total change sets:            4

Liquibase: Update has been successful.
2023-05-19 13:49:53 INFO  InitializationComponent.init:42 - Start
2023-05-19 13:49:53 INFO  InitializationComponent.init:67 - End
2023-05-19 13:50:04 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductServerMicroserviceApplication in 57.979 seconds (process running for 69.734)
...

Start viewing Product Web Microservice 01 logs
----------------------------------------------
(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ kubectl --tail 30 logs -f product-web-787d9ddf75-9m5c6
...

Start viewing Product Web Microservice 02 logs
----------------------------------------------
(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ kubectl --tail 30 logs -f product-web-787d9ddf75-g82vm
...

Start viewing Product Web Microservice 03 logs
----------------------------------------------
(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ kubectl --tail 30 logs -f product-web-787d9ddf75-tn8lk

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2023-05-19 13:49:11 INFO  StartupInfoLogger.logStarting:51 - Starting EcomProductWebMicroservice...
2023-05-19 13:49:43 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductWebMicroservice...
...


Test the Client
===================
http://192.168.64.6:32535/product.html

Scalability Testing
===================
Note: Change URLs in scripts before you run

Start cURL Client 01
--------------------
(base) binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/ch11/ch11-01/02-ProductWeb
(base) binildass-MacBook-Pro:02-ProductWeb binil$ sh pingrun1.sh 

Start cURL Client 02
--------------------
(base) binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/ch11/ch11-01/02-ProductWeb
(base) binildass-MacBook-Pro:02-ProductWeb binil$ sh pingrun2.sh 

Start cURL Client 03
--------------------
(base) binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/ch11/ch11-01/02-ProductWeb
(base) binildass-MacBook-Pro:02-ProductWeb binil$ sh pingrun3.sh 

Clean the Environment
---------------------

(base) binildass-MacBook-Pro:ch11-01 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-01
(base) binildass-MacBook-Pro:ch11-01 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch11-01 binil$ sh clean.sh 
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
[INFO] Kafka Request Reply utility ........................ SUCCESS [  0.073 s]
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  0.061 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.016 s]
[INFO] Ecom ............................................... SUCCESS [  0.002 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.337 s
[INFO] Finished at: 2023-05-19T19:37:11+05:30
[INFO] ------------------------------------------------------------------------
service "product-web" deleted
deployment.apps "product-web" deleted
service "product-server" deleted
service "product-server-nodeport" deleted
deployment.apps "product-server" deleted
service "postgres" deleted
deployment.apps "postgres" deleted
persistentvolumeclaim "postgres-persistent-volume-claim" deleted
configmap "postgres-config" deleted
service "zookeeper-ip-service" deleted
service "kafka-1-ip-service" deleted
deployment.apps "zookeeper-deployment" deleted
deployment.apps "kafka-1-deployment" deleted
Untagged: ecom/product-web:latest
Deleted: sha256:efaf8b064a2615022351057916cc822a24ee5df04b7ab1e60bd34b82c5da03db
Deleted: sha256:0f6a04ecf224543342b1466817e03b1b3e861ede7e807917f599cd385514b4f0
Deleted: sha256:a88f566a32ff58922b12040b3400143fc60163fb1633e2710b0cef378a7cba5a
Untagged: ecom/product-server:latest
Deleted: sha256:07bccb3548f800b8ae0c3849775aeb73aaef434a259796b6e27eb179630de5ca
Deleted: sha256:b005d8778b209574d724c12c4540a579585a448336c8401a3a3fc4a8b13954bd
Deleted: sha256:ae55a290b5a82afbf120f95cb193c2a286ee1dfea6f19f9aa7c4566e8b097d29
(base) binildass-MacBook-Pro:ch11-01 binil$ 

