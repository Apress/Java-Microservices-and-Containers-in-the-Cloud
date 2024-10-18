Microservices and PostgreSQL in Minikube and Rest Template (Full CRUD)
Browser => ProductWeb μS => ProductServer μS => PostgreSQLDB

k8s Deployment Architecture
---------------------------

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

(base) binildass-MacBook-Pro:ch10-01 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch10/ch10-02
(base) binildass-MacBook-Pro:ch10-01 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch10-02 binil$ sh makeandrun.sh 
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
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  3.001 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.572 s]
[INFO] Ecom ............................................... SUCCESS [  0.029 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.762 s
[INFO] Finished at: 2023-05-25T22:47:54+05:30
[INFO] ------------------------------------------------------------------------
...
configmap/postgres-config created
persistentvolumeclaim/postgres-persistent-volume-claim created
deployment.apps/postgres created
service/postgres created
deployment.apps/product-server created
service/product-server created
service/product-server-nodeport created
deployment.apps/product-web created
service/product-web created
http://192.168.64.6:30263
NAME                             READY   STATUS    RESTARTS   AGE
postgres-89dbf9fd9-f5bmc         1/1     Running   0          5s
product-server-85d84cf89-kxvfw   1/1     Running   0          5s
product-web-69b5948fb9-brdnf     1/1     Running   0          4s
NAME                      TYPE           CLUSTER-IP       EXTERNAL-IP   PORT(S)          AGE
kubernetes                ClusterIP      10.96.0.1        <none>        443/TCP          8d
postgres                  ClusterIP      10.110.46.98     <none>        5432/TCP         5s
product-server            ClusterIP      10.99.180.48     <none>        8081/TCP         5s
product-server-nodeport   NodePort       10.102.179.189   <none>        8081:30002/TCP   5s
product-web               LoadBalancer   10.102.155.211   <pending>     8080:30263/TCP   4s
(base) binildass-MacBook-Pro:ch10-02 binil$ 

Start viewing Product Server Microservice logs
----------------------------------------------

(base) binildass-MacBook-Pro:~ binil$ kubectl --tail 30 logs -f product-server-85d84cf89-kxvfw

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2023-05-25 17:18:05 INFO  StartupInfoLogger.logStarting:51 - Starting EcomProductMicroservice...
...
Liquibase: Update has been successful.
2023-05-25 17:18:18 INFO  InitializationComponent.init:45 - Start
2023-05-25 17:18:18 DEBUG InitializationComponent.init:49 - Deleting all products in DB ...
Hibernate: select p1_0.productid,p1_0.code,p1_0.prodname,p1_0.price,p1_0.title from product p1_0
2023-05-25 17:18:18 DEBUG InitializationComponent.init:68 - Creating 2 new products in DB ...
Hibernate: insert into product (code, prodname, price, title) values (?, ?, ?, ?)
Hibernate: insert into product (code, prodname, price, title) values (?, ?, ?, ?)
2023-05-25 17:18:18 INFO  InitializationComponent.init:74 - End
2023-05-25 17:18:19 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroservice...


Start viewing Product Web Microservice logs
--------------------------------------------

(base) binildass-MacBook-Pro:~ binil$ kubectl --tail 30 logs -f product-web-69b5948fb9-brdnf

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

...
2023-05-25 17:18:11 INFO  InitializationComponent.init:37 - Start
2023-05-25 17:18:11 DEBUG InitializationComponent.init:39 - Doing Nothing...
2023-05-25 17:18:11 INFO  InitializationComponent.init:41 - End
2023-05-25 17:18:13 INFO  StartupInfoLogger.logStarted:57 - Started EcomProductMicroservice...

Test the Client
---------------
http://192.168.64.6:30263/product.html

Clean the Environment
---------------------
((base) binildass-MacBook-Pro:ch10-02 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch10/ch10-02
(base) binildass-MacBook-Pro:ch10-02 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch10-02 binil$ sh clean.sh 
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
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  0.086 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.011 s]
[INFO] Ecom ............................................... SUCCESS [  0.034 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.299 s
[INFO] Finished at: 2023-05-19T17:30:17+05:30
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
Untagged: ecom/product-web:latest
Deleted: sha256:7a24b2348dfbcf82a1e28602fa264d864f3089f1c56b1653db892e7d835510e3
Deleted: sha256:1c8213f9607f467dbb525b72ef1c1b506239e3487b959c9bd6d340e1017f8bb4
Deleted: sha256:258f4292f73e88149ba23f8d335ac0a8daf94b06c35995632936c69d1d5f026f
Untagged: ecom/product-server:latest
Deleted: sha256:64f76e22eeb5d036dcc68195d96e7d08cc30d7010ad07bc2c31cdfe00272e7fa
Deleted: sha256:14c634fbd0b2bbad1883af117dcb893c21d502f7f249742f591ee0147966346c
Deleted: sha256:0d93281566b60b6bca1d4bb7ce93a329199533e86a466c7456259043b392adaa
(base) binildass-MacBook-Pro:ch10-02 binil$ 

