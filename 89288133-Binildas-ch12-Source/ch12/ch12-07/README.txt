PostgreSQL, Adminer, Ingress & 2 µS (Full CRUD) in Minikube with Helmfile

            ProductWeb μS => ProductServer μS => PostgreSQLDB
                ^                                     ^
                |                                     |
Browser =>    Ingress  ------------------------->  Adminer

Microservices and PostgreSQL in Minikube and Rest Template (Full CRUD)

            ProductWeb μS => ProductServer μS => PostgreSQLDB
                ^                                     ^
                |                                     |
Browser =>    Ingress  ------------------------->  Adminer

Start minikube
---------------
(base) binildass-MacBook-Pro:ch07-03 binil$ minikube start

config minikube to accesss local docker repository (from where we started minikube)
--------------------------
(base) binildass-MacBook-Pro:ch07-02 binil$ eval $(minikube docker-env)

configure host file:
--------------------
(base) binildass-MacBook-Pro:ch07-02 binil$ minikube ip
192.168.99.101
(base) binildass-MacBook-Pro:~ binil$ sudo nano /etc/hosts

192.168.99.101  adminer.acme.test
192.168.99.101  products.acme.test

(base) binildass-MacBook-Pro:~ binil$ ^O (To Save)
(base) binildass-MacBook-Pro:~ binil$ ^X (To Exit)
(base) binildass-MacBook-Pro:~ sudo killall -HUP mDNSResponder
--------------------

Ingress addon in minikube
-------------------------
helm upgrade --install ingress-nginx ingress-nginx \
  --repo https://kubernetes.github.io/ingress-nginx \
  --namespace ingress-nginx --create-namespace

(base) binildass-MacBook-Pro:~ binil$ kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/nginx-0.30.0/deploy/static/mandatory.yaml
(base) binildass-MacBook-Pro:~ binil$ minikube addons enable ingress
🌟  The 'ingress' addon is enabled
(base) binildass-MacBook-Pro:~ binil$ 

================ HELMFILE ================

(base) binildass-MacBook-Pro:.Trash binil$ cd /Users/binil/binil/code/mac/mybooks/docker-04/ch12/
(base) binildass-MacBook-Pro:ch12 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/ch12
(base) binildass-MacBook-Pro:ch12 binil$ mkdir ch12-07
(base) binildass-MacBook-Pro:ch12 binil$ cp -r BookCode/ch12/ch12-06/* /Users/binil/binil/code/mac/mybooks/docker-04/ch12/ch12-07/
(base) binildass-MacBook-Pro:ch12-07 binil$ tree .
.
├── 01-ProductServer
├── 02-ProductWeb
├── Dockerfile
├── README.txt
├── acme-postgres.yaml
├── acme-product-server.yaml
├── acme-product-web.yaml
├── adminer.yaml
├── app
├── clean.sh
├── ingress
├── ingress.yaml
├── make.sh
├── postgres
└── run.sh

41 directories, 67 files
(base) binildass-MacBook-Pro:ch12-07 binil$ mkdir charts
(base) binildass-MacBook-Pro:ch12-07 binil$ mv ./charts/ ./app
(base) binildass-MacBook-Pro:ch12-07 binil$ mv ./app ./charts/
(base) binildass-MacBook-Pro:ch12-07 binil$ mv ./ingress ./charts/
(base) binildass-MacBook-Pro:ch12-07 binil$ mv ./postgres ./charts/
(base) binildass-MacBook-Pro:ch12-07 binil$ mkdir values
(base) binildass-MacBook-Pro:ch12-07 binil$ mv ./acme*.yaml ./values/
(base) binildass-MacBook-Pro:ch12-07 binil$ mv ./adminer.yaml ./values/
(base) binildass-MacBook-Pro:ch12-07 binil$ mv ./ingress.yaml ./values/
(base) binildass-MacBook-Pro:ch12-07 binil$ touch helmfile.yaml
(base) binildass-MacBook-Pro:ch12-07 binil$ tree .
.
├── 01-ProductServer
├── 02-ProductWeb
├── Dockerfile
├── README.txt
├── charts
│   ├── app
│   ├── ingress
│   └── postgres
├── clean.sh
├── helmfile.yaml
├── make.sh
├── run.sh
└── values
    ├── acme-postgres.yaml
    ├── acme-product-server.yaml
    ├── acme-product-web.yaml
    ├── adminer.yaml
    └── ingress.yaml

42 directories, 68 files
(base) binildass-MacBook-Pro:ch12-07 binil$ 

Different from sample in /Users/binil/binil/code/mac/mybooks/docker-04/ch12/ch12-06, in current sample we will treat nginx-ingress chart as a separate Helm release, apart from the Ingress Controller configuration for routing
Tweak ./charts/ingress/Chart.yaml to remove nginx-ingress dependencies.
Indicate from which Helm repositories we would like to download stable/nginx-ingress chart & all other Helm releases too in:
./helmfile.yaml

(base) binildass-MacBook-Pro:ch12-07 binil$ rm ./charts/ingress/Chart.lock 
(base) binildass-MacBook-Pro:ch12-07 binil$ 

Helm Releases
-------------

(base) binildass-MacBook-Pro:ch11-07 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-07
(base) binildass-MacBook-Pro:ch11-07 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch11-07 binil$ sh run.sh 
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
[INFO] --------< com.acme.ecom.product:Ecom-Product-Web-Microservice >---------
[INFO] Building Ecom-Product-Web-Microservice 0.0.1-SNAPSHOT              [2/3]
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
...
[INFO] 
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  3.604 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.655 s]
[INFO] Ecom ............................................... SUCCESS [  0.037 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  4.502 s
[INFO] Finished at: 2023-05-23T11:35:18+05:30
[INFO] ------------------------------------------------------------------------
...
Successfully tagged ecom/product-web:latest
Successfully tagged ecom/product-server:latest
Adding repo stable https://charts.helm.sh/stable
"stable" has been added to your repositories

Adding repo stable https://charts.helm.sh/stable
"stable" has been added to your repositories

Building dependency release=postgres, chart=charts/postgres
Building dependency release=adminer, chart=charts/app
Building dependency release=product-web, chart=charts/app
Building dependency release=ingress-controller, chart=charts/ingress
Building dependency release=product-server, chart=charts/app
Upgrading release=product-server, chart=charts/app
Upgrading release=product-web, chart=charts/app
Upgrading release=ingress-controller, chart=charts/ingress
Upgrading release=adminer, chart=charts/app
Upgrading release=ingress-backend, chart=stable/nginx-ingress
Upgrading release=postgres, chart=charts/postgres
Release "product-web" does not exist. Installing it now.
NAME: product-web
LAST DEPLOYED: Tue May 23 11:35:27 2023
NAMESPACE: default
STATUS: deployed
REVISION: 1
TEST SUITE: None

...  

UPDATED RELEASES:
NAME                 CHART                  VERSION   DURATION
product-web          ./charts/app           0.1.0           1s
adminer              ./charts/app           0.1.0           1s
product-server       ./charts/app           0.1.0           1s
postgres             ./charts/postgres      0.1.0           1s
ingress-controller   ./charts/ingress       1.1.0           1s
ingress-backend      stable/nginx-ingress   1.36.0          2s

😿  service default/product-web has no node port
NAME              	NAMESPACE	REVISION	UPDATED                             	STATUS  	CHART               	APP VERSION
adminer           	default  	1       	2023-05-23 11:35:27.712645 +0530 IST	deployed	app-0.1.0           	1.16.0     
ingress-backend   	default  	1       	2023-05-23 11:35:29.28951 +0530 IST 	deployed	nginx-ingress-1.36.0	0.30.0     
ingress-controller	default  	1       	2023-05-23 11:35:27.685351 +0530 IST	deployed	ingress-1.1.0       	1.17.0     
postgres          	default  	1       	2023-05-23 11:35:27.668141 +0530 IST	deployed	postgres-0.1.0      	1.16.0     
product-server    	default  	1       	2023-05-23 11:35:27.708752 +0530 IST	deployed	app-0.1.0           	1.16.0     
product-web       	default  	1       	2023-05-23 11:35:27.70829 +0530 IST 	deployed	app-0.1.0           	1.16.0     
NAME                                               READY   UP-TO-DATE   AVAILABLE   AGE
adminer                                            0/1     1            0           7s
ingress-backend-nginx-ingress-controller           0/1     1            0           6s
ingress-backend-nginx-ingress-default-backend      0/1     1            0           6s
ingress-controller-nginx-ingress-controller        0/1     1            0           7s
ingress-controller-nginx-ingress-default-backend   0/1     1            0           7s
postgres                                           0/1     1            0           7s
product-server                                     0/1     1            0           7s
product-web                                        0/1     1            0           7s
(base) binildass-MacBook-Pro:ch11-07 binil$ 


Insepect the deployments
------------------------

(base) binildass-MacBook-Pro:~ binil$ kubectl get all
NAME                                                                  READY   STATUS             RESTARTS        AGE
pod/adminer-76dbb9d656-txt7x                                          1/1     Running            0               15m
pod/ingress-backend-nginx-ingress-controller-5c9fd47cdc-jdmnp         0/1     CrashLoopBackOff   7 (5m5s ago)    15m
pod/ingress-backend-nginx-ingress-default-backend-66459f57d9-lz47h    1/1     Running            0               15m
pod/ingress-controller-nginx-ingress-controller-5b85694fbc-glsjz      0/1     CrashLoopBackOff   7 (4m14s ago)   15m
pod/ingress-controller-nginx-ingress-default-backend-67f467d9dgckvw   1/1     Running            0               15m
pod/postgres-7b6687c664-zxl65                                         1/1     Running            0               15m
pod/product-server-6466f4469d-4wngc                                   1/1     Running            0               15m
pod/product-web-67b769d6df-7k5m8                                      1/1     Running            0               15m

NAME                                                       TYPE           CLUSTER-IP       EXTERNAL-IP   PORT(S)                      AGE
service/adminer                                            ClusterIP      10.107.104.233   <none>        8080/TCP                     15m
service/ingress-backend-nginx-ingress-controller           LoadBalancer   10.103.217.67    <pending>     80:30865/TCP,443:32028/TCP   15m
service/ingress-backend-nginx-ingress-default-backend      ClusterIP      10.102.28.132    <none>        80/TCP                       15m
service/ingress-controller-nginx-ingress-controller        LoadBalancer   10.102.131.89    <pending>     80:31498/TCP,443:32508/TCP   15m
service/ingress-controller-nginx-ingress-default-backend   ClusterIP      10.96.207.107    <none>        80/TCP                       15m
service/kubernetes                                         ClusterIP      10.96.0.1        <none>        443/TCP                      6d
service/postgres                                           ClusterIP      10.98.224.131    <none>        5432/TCP                     15m
service/product-server                                     ClusterIP      10.106.56.232    <none>        8080/TCP                     15m
service/product-web                                        ClusterIP      10.99.44.189     <none>        8080/TCP                     15m

NAME                                                               READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/adminer                                            1/1     1            1           15m
deployment.apps/ingress-backend-nginx-ingress-controller           0/1     1            0           15m
deployment.apps/ingress-backend-nginx-ingress-default-backend      1/1     1            1           15m
deployment.apps/ingress-controller-nginx-ingress-controller        0/1     1            0           15m
deployment.apps/ingress-controller-nginx-ingress-default-backend   1/1     1            1           15m
deployment.apps/postgres                                           1/1     1            1           15m
deployment.apps/product-server                                     1/1     1            1           15m
deployment.apps/product-web                                        1/1     1            1           15m

NAME                                                                          DESIRED   CURRENT   READY   AGE
replicaset.apps/adminer-76dbb9d656                                            1         1         1       15m
replicaset.apps/ingress-backend-nginx-ingress-controller-5c9fd47cdc           1         1         0       15m
replicaset.apps/ingress-backend-nginx-ingress-default-backend-66459f57d9      1         1         1       15m
replicaset.apps/ingress-controller-nginx-ingress-controller-5b85694fbc        1         1         0       15m
replicaset.apps/ingress-controller-nginx-ingress-default-backend-67f467d9d7   1         1         1       15m
replicaset.apps/postgres-7b6687c664                                           1         1         1       15m
replicaset.apps/product-server-6466f4469d                                     1         1         1       15m
replicaset.apps/product-web-67b769d6df                                        1         1         1       15m
(base) binildass-MacBook-Pro:~ binil$ 

Test the Client
---------------
Open below link in Chrome

http://products.acme.test/product.html

http://adminer.acme.test
http://products.acme.test/productsweb/
http://products.acme.test/api/
http://products.acme.test/api/products/

Below Links Not working:

http://products.acme.test/api/swagger-ui.html


Clean the Project
-----------------

(base) binildass-MacBook-Pro:ch11-07 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-07
(base) binildass-MacBook-Pro:ch11-07 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch11-07 binil$ sh clean.sh 
release "adminer" uninstalled
release "product-web" uninstalled
release "product-server" uninstalled
release "postgres" uninstalled
release "ingress-backend" uninstalled
release "ingress-controller" uninstalled
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
[INFO] Ecom-Product-Server-Microservice ................... SUCCESS [  0.126 s]
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  0.013 s]
[INFO] Ecom ............................................... SUCCESS [  0.039 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.451 s
[INFO] Finished at: 2023-05-23T11:59:45+05:30
[INFO] ------------------------------------------------------------------------
Untagged: ecom/product-web:latest
Deleted: sha256:86b53b4e3a59466dae6802f57fdf86108fa1e64eabe36c8a33987e142cae4bef
Deleted: sha256:17e560f3151852a3fd75957b27f5bf8933eab8dfacd2b19d5a44e35a2a105cc0
Untagged: ecom/product-server:latest
Deleted: sha256:bc204663abefc757ae56166267f35f86ca7f95ce033af728905b9cbe8f557a8f
Deleted: sha256:66891269ccd6f7a363ea8a3789d741cb85335ddb64e2da0366acc8fd61dae755
Deleted: sha256:9b0fa692861b65fd2080f7bf1a12120bbc0f245f1677e6850bb1ceccace6a669
(base) binildass-MacBook-Pro:ch11-07 binil$ 

