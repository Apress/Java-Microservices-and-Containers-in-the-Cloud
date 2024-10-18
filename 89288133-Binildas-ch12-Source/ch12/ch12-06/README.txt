PostgreSQL, Adminer, Ingress & 2 µS (Full CRUD) in Minikube with Helm

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
    ▪ Using image registry.k8s.io/ingress-nginx/kube-webhook-certgen:v20230312-helm-chart-4.5.2-28-g66a760794
    ▪ Using image docker.io/kubernetesui/metrics-scraper:v1.0.8
    ▪ Using image docker.io/kubernetesui/dashboard:v2.7.0
    ▪ Using image registry.k8s.io/ingress-nginx/kube-webhook-certgen:v20230312-helm-chart-4.5.2-28-g66a760794
    ▪ Using image registry.k8s.io/ingress-nginx/controller:v1.7.0
💡  Some dashboard features require the metrics-server addon. To enable all features please run:

	minikube addons enable metrics-server	


🔎  Verifying ingress addon...
🌟  Enabled addons: ingress-dns, storage-provisioner, default-storageclass, dashboard, ingress
🏄  Done! kubectl is now configured to use "minikube" cluster and "default" namespace by default
(base) binildass-MacBook-Pro:~ binil$ 


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

Create first Helm chart (Postgre)
---------------------------------

(base) binildass-MacBook-Pro:ch12-06 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/ch12/ch12-06
(base) binildass-MacBook-Pro:ch12-06 binil$ ls
README.txt
(base) binildass-MacBook-Pro:ch12-06 binil$ helm create postgres
Creating postgres
(base) binildass-MacBook-Pro:ch12-06 binil$ ls
README.txt	postgres
(base) binildass-MacBook-Pro:ch12-06 binil$ rm -r ./postgres/templates/*
(base) binildass-MacBook-Pro:ch12-06 binil$ touch ./postgres/templates/deployment.yaml
(base) binildass-MacBook-Pro:ch12-06 binil$ touch ./postgres/templates/pcv.yaml
(base) binildass-MacBook-Pro:ch12-06 binil$ touch ./postgres/templates/service.yaml
(base) binildass-MacBook-Pro:ch12-06 binil$ touch ./postgres/templates/config.yaml
(base) binildass-MacBook-Pro:ch12-06 binil$ 

Create Templates for Postgre Kubernetes objects:
Copy contents from BookCode/ch12/ch12-06/postgres/templates/* to above files

Create Dummy values for Postgre Kubernetes objects:
Copy contents from BookCode/ch12/ch12-06/postgres/values.yaml to ./postgres/values.yaml

Update metadata in ./postgres/Charts.yaml
Copy contents from BookCode/ch12/ch12-06/postgres/Charts.yaml to ./postgres/Charts.yaml

(base) binildass-MacBook-Pro:ch12-06 binil$ touch ./acme-postgres.yaml
Update this file with acme postgres specific values
Copy contents from BookCode/ch12/ch12-06/acme-postgres.yaml to ./acme-postgres.yaml


To validate ./postgres/templates/*.yaml with its actual values before running the helm install command:
(base) binildass-MacBook-Pro:ch12-06 binil$ helm template postgres

One more sanitary command is lint provided by helm which you could run to identify possible issues forehand:
(base) binildass-MacBook-Pro:ch12-06 binil$ helm lint postgres
==> Linting postgres
[INFO] Chart.yaml: icon is recommended

1 chart(s) linted, 0 chart(s) failed
(base) binildass-MacBook-Pro:ch12-06 binil$ 

To test the configuration before running the final install command
(base) binildass-MacBook-Pro:ch12-06 binil$ helm install postgres --debug --dry-run postgres
install.go:158: [debug] Original chart version: ""
install.go:175: [debug] CHART PATH: /Users/binil/binil/code/mac/mybooks/docker-04/ch12/ch12-06/postgres
...
(base) binildass-MacBook-Pro:ch12-06 binil$ 

Create a New Release Now (Common script at the end)...

Create Common Helm charts for product-server, product-web & adminer
-------------------------------------------------------------------

(base) binildass-MBP:ch12-06 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/ch12/ch12-06
(base) binildass-MBP:ch12-06 binil$ ls
01-ProductServer	README.txt		make.sh
02-ProductWeb		acme-postgres.yaml	postgres
(base) binildass-MBP:ch12-06 binil$ eval $(minikube docker-env)
(base) binildass-MBP:ch12-06 binil$ helm create app
Creating app
(base) binildass-MBP:ch12-06 binil$ rm -r ./app/templates/*
(base) binildass-MBP:ch12-06 binil$ touch ./app/templates/deployment.yaml
(base) binildass-MBP:ch12-06 binil$ touch ./app/templates/service.yaml

Create Templates for Postgre Kubernetes objects:
Copy contents from BookCode/ch12/ch12-06/app/templates/* to above files

Create Dummy values for Postgre Kubernetes objects:
Copy contents from BookCode/ch12/ch12-06/app/values.yaml to ./app/values.yaml

Update metadata in ./app/Charts.yaml

(base) binildass-MBP:ch12-06 binil$ touch ./adminer.yaml
(base) binildass-MBP:ch12-06 binil$ touch ./acme-product-server.yaml
(base) binildass-MBP:ch12-06 binil$ touch ./acme-product-web.yaml

Update above files with acme specific values
Copy contents from corresponding files BookCode/ch12/ch12-06/acme-*.yaml to ./acme-*.yaml
Copy contents from corresponding files BookCode/ch12/ch12-06/adminer.yaml to ./adminer.yaml

Create Ingress Chart
--------------------

(base) binildass-MBP:ch12-06 binil$ helm create ingress
Creating ingress
(base) binildass-MBP:ch12-06 binil$ rm -r ./ingress/templates/*
(base) binildass-MBP:ch12-06 binil$ 

Create Chart.yaml for Ingress Kubernetes objects:
Copy contents from BookCode/ch12/ch12-06/ingress/Chart.yaml to ./ingress/Chart.yaml

(base) binildass-MBP:ch12-06 binil$ helm dependency update ./ingress/
Saving 1 charts
Downloading nginx-ingress from repo https://charts.helm.sh/stable
Deleting outdated charts
(base) binildass-MBP:ch12-06 binil$ 

(base) binildass-MBP:ch12-06 binil$ touch ./ingress/templates/ingress.yaml

Copy contents from BookCode/ch12/ch12-06/ingress/templates/ingress.yaml to ./ingress/templates/ingress.yaml

Copy contents from BookCode/ch12/ch12-06/ingress/values.yaml to ./ingress/values.yaml

(base) binildass-MBP:ch12-06 binil$ touch ./ingress.yaml
Copy contents from BookCode/ch12/ch12-06/ingress.yaml to ./ingress.yaml

Helm Releases
-------------

(base) binildass-MacBook-Pro:ch11-06 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-06
(base) binildass-MacBook-Pro:ch11-06 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch11-06 binil$ sh run.sh 
[INFO] Scanning for projects...
[INFO] 
[INFO] -------< com.acme.ecom.product:Ecom-Product-Server-Microservice >-------
[INFO] Building Ecom-Product-Server-Microservice 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.900 s
[INFO] Finished at: 2023-05-22T23:31:59+05:30
[INFO] ------------------------------------------------------------------------
[INFO] Scanning for projects...
[INFO] 
[INFO] --------< com.acme.ecom.product:Ecom-Product-Web-Microservice >---------
[INFO] Building Ecom-Product-Web-Microservice 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.539 s
[INFO] Finished at: 2023-05-22T23:32:02+05:30
[INFO] ------------------------------------------------------------------------
...
Successfully tagged ecom/product-web:latest
Successfully tagged ecom/product-server:latest
NAME: postgres
LAST DEPLOYED: Mon May 22 23:32:05 2023
NAMESPACE: default
STATUS: deployed
REVISION: 1
TEST SUITE: None
NAME: adminer
LAST DEPLOYED: Mon May 22 23:32:06 2023
NAMESPACE: default
STATUS: deployed
REVISION: 1
TEST SUITE: None
NAME: product-server
LAST DEPLOYED: Mon May 22 23:32:06 2023
NAMESPACE: default
STATUS: deployed
REVISION: 1
TEST SUITE: None
NAME: product-web
LAST DEPLOYED: Mon May 22 23:32:06 2023
NAMESPACE: default
STATUS: deployed
REVISION: 1
TEST SUITE: None
NAME: ingress
LAST DEPLOYED: Mon May 22 23:32:06 2023
NAMESPACE: default
STATUS: deployed
REVISION: 1
TEST SUITE: None
http://192.168.64.6:32216
NAME          	NAMESPACE	REVISION	UPDATED                             	STATUS  	CHART         	APP VERSION
adminer       	default  	1       	2023-05-22 23:32:06.114053 +0530 IST	deployed	app-0.1.0     	1.16.0     
ingress       	default  	1       	2023-05-22 23:32:06.840657 +0530 IST	deployed	ingress-1.1.0 	1.17.0     
postgres      	default  	1       	2023-05-22 23:32:05.81118 +0530 IST 	deployed	postgres-0.1.0	1.16.0     
product-server	default  	1       	2023-05-22 23:32:06.359578 +0530 IST	deployed	app-0.1.0     	1.16.0     
product-web   	default  	1       	2023-05-22 23:32:06.595214 +0530 IST	deployed	app-0.1.0     	1.16.0     
NAME                                    READY   UP-TO-DATE   AVAILABLE   AGE
adminer                                 1/1     1            1           6s
ingress-nginx-ingress-controller        0/1     1            0           5s
ingress-nginx-ingress-default-backend   0/1     1            0           5s
postgres                                0/1     1            0           6s
product-server                          1/1     1            1           6s
product-web                             1/1     1            1           5s
(base) binildass-MacBook-Pro:ch11-06 binil$ 

Insepect the deployments
------------------------

(base) binildass-MacBook-Pro:~ binil$ kubectl get all
NAME                                                        READY   STATUS    RESTARTS     AGE
pod/adminer-76dbb9d656-l2jw7                                1/1     Running   0            2m32s
pod/ingress-nginx-ingress-controller-684dbd8f68-2vfgs       0/1     Running   3 (7s ago)   2m32s
pod/ingress-nginx-ingress-default-backend-968d47cfb-7gz2f   1/1     Running   0            2m32s
pod/postgres-7b6687c664-qdskf                               1/1     Running   0            2m33s
pod/product-server-6466f4469d-jd6cc                         1/1     Running   0            2m32s
pod/product-web-67b769d6df-gp5ln                            1/1     Running   0            2m32s

NAME                                            TYPE           CLUSTER-IP       EXTERNAL-IP   PORT(S)                      AGE
service/adminer                                 ClusterIP      10.100.185.153   <none>        8080/TCP                     2m32s
service/ingress-nginx-ingress-controller        LoadBalancer   10.98.101.250    <pending>     80:31703/TCP,443:31558/TCP   2m32s
service/ingress-nginx-ingress-default-backend   ClusterIP      10.107.158.114   <none>        80/TCP                       2m32s
service/kubernetes                              ClusterIP      10.96.0.1        <none>        443/TCP                      5d22h
service/postgres                                ClusterIP      10.97.43.38      <none>        5432/TCP                     2m33s
service/product-server                          ClusterIP      10.96.49.6       <none>        8080/TCP                     2m32s
service/product-web                             ClusterIP      10.98.17.73      <none>        8080/TCP                     2m32s

NAME                                                    READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/adminer                                 1/1     1            1           2m32s
deployment.apps/ingress-nginx-ingress-controller        0/1     1            0           2m32s
deployment.apps/ingress-nginx-ingress-default-backend   1/1     1            1           2m32s
deployment.apps/postgres                                1/1     1            1           2m33s
deployment.apps/product-server                          1/1     1            1           2m32s
deployment.apps/product-web                             1/1     1            1           2m32s

NAME                                                              DESIRED   CURRENT   READY   AGE
replicaset.apps/adminer-76dbb9d656                                1         1         1       2m32s
replicaset.apps/ingress-nginx-ingress-controller-684dbd8f68       1         1         0       2m32s
replicaset.apps/ingress-nginx-ingress-default-backend-968d47cfb   1         1         1       2m32s
replicaset.apps/postgres-7b6687c664                               1         1         1       2m33s
replicaset.apps/product-server-6466f4469d                         1         1         1       2m32s
replicaset.apps/product-web-67b769d6df                            1         1         1       2m32s
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
http://products.acme.test/swagger-ui.html

================

Clean the Project
-----------------

(base) binildass-MacBook-Pro:ch11-06 binil$ sh clean.sh 
release "adminer" uninstalled
release "product-web" uninstalled
release "product-server" uninstalled
release "postgres" uninstalled
release "ingress" uninstalled
[INFO] Scanning for projects...
[INFO] 
[INFO] -------< com.acme.ecom.product:Ecom-Product-Server-Microservice >-------
[INFO] Building Ecom-Product-Server-Microservice 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.270 s
[INFO] Finished at: 2023-05-22T23:34:52+05:30
[INFO] ------------------------------------------------------------------------
Untagged: ecom/product-web:latest
Deleted: sha256:20a9771d51f364858119568ee2966962cb446be002c2fc7c84c59e6ed0b7199a
Deleted: sha256:67198ac9eae41d4f49fb7fde3b3b3c1d7674ac892ff3ef612985b57cf24e3601
Deleted: sha256:b1751676d4cf4fcd0d94cd5446f2657d745f45b466f1dd03a23b9a87596b18ac
Untagged: ecom/product-server:latest
Deleted: sha256:425f68653d2974860ab61209c80caf2cb59a6d8497fe5c8ad2280f3993c9510d
Deleted: sha256:5cc849ba5c2fbd6d376b5896ae606b2e83bc5d8cbe16d672a30945fae9cf3dee
Deleted: sha256:407ed7483f3521b866817042a500d156f2583c3b4f0b337b29e89aadc0a62eb1
(base) binildass-MacBook-Pro:ch11-06 binil$ 

The Project Structure
---------------------

./ch11-06/
├── 01-ProductServer
│   ├── Dockerfile
│   ├── make.bat
│   ├── make.sh
│   ├── pom.xml
│   ├── run.bat
│   ├── run.sh
│   └── src
│       └── main
│           ├── java
│           │   └── com
│           │       └── acme
│           │           └── ecom
│           │               └── product
│           │                   ├── EcomProductMicroserviceApplication.java
│           │                   ├── InitializationComponent.java
│           │                   ├── config
│           │                   │   └── SwaggerConfig.java
│           │                   ├── controller
│           │                   │   ├── ProductMapper.java
│           │                   │   └── ProductRestController.java
│           │                   ├── model
│           │                   │   ├── Product.java
│           │                   │   └── ProductOR.java
│           │                   └── repository
│           │                       ├── ProductRepository.java
│           │                       └── ProductRepositoryConfiguration.java
│           └── resources
│               ├── application.properties
│               ├── db
│               │   └── changelog
│               │       ├── 01_init_product.sql
│               │       ├── db.changelog-master.xml
│               │       └── initial-schema_inventory.xml
│               └── log4j2-spring.xml
├── 02-ProductWeb
│   ├── Dockerfile
│   ├── default.conf
│   ├── make.bat
│   ├── make.sh
│   ├── pom.xml
│   ├── run.bat
│   ├── run.sh
│   └── src
│       └── main
│           ├── java
│           │   └── com
│           │       └── acme
│           │           └── ecom
│           │               └── product
│           │                   ├── EcomProductMicroserviceApplication.java
│           │                   ├── InitializationComponent.java
│           │                   ├── config
│           │                   │   └── SwaggerConfig.java
│           │                   ├── controller
│           │                   │   ├── ProductRestController.java
│           │                   │   └── ProductRestControllerConfiguration.java
│           │                   └── model
│           │                       ├── Product.java
│           │                       └── ProductCategory.java
│           └── resources
│               ├── application.properties
│               ├── log4j2-spring.xml
│               └── static
│                   ├── css
│                   │   ├── app.css
│                   │   └── bootstrap.css
│                   ├── js
│                   │   ├── app.js
│                   │   ├── controller
│                   │   │   └── product_controller.js
│                   │   └── service
│                   │       └── product_service.js
│                   └── product.html
├── Dockerfile
├── README.txt
├── acme-postgres.yaml
├── acme-product-server.yaml
├── acme-product-web.yaml
├── adminer.yaml
├── app
│   ├── Chart.yaml
│   ├── charts
│   ├── templates
│   │   ├── deployment.yaml
│   │   └── service.yaml
│   └── values.yaml
├── clean.sh
├── ingress
│   ├── Chart.lock
│   ├── Chart.yaml
│   ├── charts
│   │   └── nginx-ingress-1.36.0.tgz
│   ├── templates
│   │   └── ingress.yaml
│   └── values.yaml
├── ingress.yaml
├── make.sh
├── pom.xml
├── postgres
│   ├── Chart.yaml
│   ├── charts
│   ├── templates
│   │   ├── config.yaml
│   │   ├── deployment.yaml
│   │   ├── pcv.yaml
│   │   └── service.yaml
│   └── values.yaml
└── run.sh

41 directories, 68 files
(base) binildass-MacBook-Pro:ch11 binil$ 





