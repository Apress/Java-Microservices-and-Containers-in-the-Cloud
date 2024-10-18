Start minikube
--------------
(base) binildass-MacBook-Pro:~ binil$ minikube start
😄  minikube v1.30.1 on Darwin 13.3
✨  Using the hyperkit driver based on existing profile
👍  Starting control plane node minikube in cluster minikube
🔄  Restarting existing hyperkit VM for "minikube" ...
🐳  Preparing Kubernetes v1.26.3 on Docker 20.10.23 ...
🔗  Configuring bridge CNI (Container Networking Interface) ...
🔎  Verifying Kubernetes components...
    ▪ Using image gcr.io/k8s-minikube/minikube-ingress-dns:0.0.2
    ▪ Using image registry.k8s.io/ingress-nginx/controller:v1.7.0
    ▪ Using image gcr.io/k8s-minikube/storage-provisioner:v5
    ▪ Using image registry.k8s.io/ingress-nginx/kube-webhook-certgen:v20230312-helm-chart-4.5.2-28-g66a760794
    ▪ Using image registry.k8s.io/ingress-nginx/kube-webhook-certgen:v20230312-helm-chart-4.5.2-28-g66a760794
🔎  Verifying ingress addon...
🌟  Enabled addons: ingress-dns, storage-provisioner, default-storageclass, ingress
🏄  Done! kubectl is now configured to use "minikube" cluster and "default" namespace by default
(base) binildass-MacBook-Pro:~ binil$ 

Build the Application
---------------------

(base) binildass-MacBook-Pro:ch11-02 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-02
(base) binildass-MacBook-Pro:ch11-04 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch11-02 binil$ sh make.sh 
[INFO] Scanning for projects...
...
[INFO] 
[INFO] ---------< com.acme.ecom.product:spring-boot-docker-k8s-helm >----------
[INFO] Building Spring Boot µS 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
...
[INFO] dockerfile: null
[INFO] contextDirectory: /Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-02
[INFO] Building Docker context /Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-02
[INFO] Path(dockerfile): null
[INFO] Path(contextDirectory): /Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-02
[INFO] 
[INFO] Image will be built as binildas/spring-boot-docker-k8s-helm:latest
[INFO] 
[INFO] Step 1/8 : FROM openjdk:8-jdk-alpine
[INFO] 
[INFO] Pulling from library/openjdk
[INFO] Digest: sha256:94792824df2df33402f201713f932b58cb9de94a0cd524164a0f2283343547b3
[INFO] Status: Image is up to date for openjdk:8-jdk-alpine
[INFO]  ---> a3562aa0b991
[INFO] Step 2/8 : VOLUME /tmp
[INFO] 
[INFO]  ---> Running in b99fe0bf1984
[INFO] Removing intermediate container b99fe0bf1984
[INFO]  ---> 9889cb2fe045
[INFO] Step 3/8 : ARG DEPENDENCY=target/dependency
[INFO] 
[INFO]  ---> Running in 9f2c3e8d3b3c
[INFO] Removing intermediate container 9f2c3e8d3b3c
[INFO]  ---> f76af392e455
[INFO] Step 4/8 : COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
[INFO] 
[INFO]  ---> dc5915c673ef
[INFO] Step 5/8 : COPY ${DEPENDENCY}/META-INF /app/META-INF
[INFO] 
[INFO]  ---> 3fe521a4d333
[INFO] Step 6/8 : COPY ${DEPENDENCY}/BOOT-INF/classes /app
[INFO] 
[INFO]  ---> c12840b86149
[INFO] Step 7/8 : ENTRYPOINT ["java","-cp","app:app/lib/*","com.acme.ecom.product.Application"]
[INFO] 
[INFO]  ---> Running in 6ef7a3ecb5d3
[INFO] Removing intermediate container 6ef7a3ecb5d3
[INFO]  ---> 843f15f9f247
[INFO] Step 8/8 : EXPOSE 8080
[INFO] 
[INFO]  ---> Running in 1ddf7fa59c99
[INFO] Removing intermediate container 1ddf7fa59c99
[INFO]  ---> 0c23b3413de1
[INFO] Successfully built 0c23b3413de1
[INFO] Successfully tagged binildas/spring-boot-docker-k8s-helm:latest
[INFO] 
...
[INFO] Successfully built binildas/spring-boot-docker-k8s-helm:latest
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  13.935 s
[INFO] Finished at: 2023-05-20T11:54:39+05:30
[INFO] ------------------------------------------------------------------------
(base) binildass-MacBook-Pro:ch11-02 binil$ 

Inspect Local Docker Repository
-------------------------------

(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)clear
(base) binildass-MacBook-Pro:~ binil$ docker images
REPOSITORY                                           TAG                IMAGE ID       CREATED         SIZE
binildas/spring-boot-docker-k8s-helm            latest             0c23b3413de1   3 minutes ago   124MB
...
(base) binildass-MacBook-Pro:~ binil$

OPTIONAL STEP: Delete Previous Images from public Docker Hub Repository
----------------------------------------------------------------------
(base) binildass-MacBook-Pro:~ binil$ HUB_TOKEN=$(curl -s -H "Content-Type: application/json" -X POST -d '{"username": "binildas" , "password": "********" }' https://hub.docker.com/v2/users/login/ | jq -r .token)
(base) binildass-MacBook-Pro:~ binil$ echo $HUB_TOKEN
eyJ4NWMiOlsiTUlJQytUQ0NBc...
(base) binildass-MacBook-Pro:~ binil$ curl -i -X DELETE -H "Accept: application/json" -H "Authorization: JWT $HUB_TOKEN" https://hub.docker.com/v2/repositories/binildas/spring-boot-docker-k8s-helm/tags/latest/
HTTP/1.1 204 No Content
date: Sat, 20 May 2023 06:08:59 GMT
x-ratelimit-limit: 600
x-ratelimit-reset: 1684562998
x-ratelimit-remaining: 600
x-trace-id: f6de7fa45a63c1eb6b076df17971cc37
server: nginx
x-frame-options: deny
x-content-type-options: nosniff
x-xss-protection: 1; mode=block
strict-transport-security: max-age=31536000

(base) binildass-MacBook-Pro:~ binil$ 

Login to public Docker Hub Repository
-------------------------------------
(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ docker login
Authenticating with existing credentials...
WARNING! Your password will be stored unencrypted in /Users/binil/.docker/config.json.
Configure a credential helper to remove this warning. See
https://docs.docker.com/engine/reference/commandline/login/#credentials-store

Login Succeeded
(base) binildass-MacBook-Pro:~ binil$

Push Images to public Docker Hub Repository
-------------------------------------------
(base) binildass-MacBook-Pro:~ binil$ docker push binildas/spring-boot-docker-k8s-helm:latest
The push refers to repository [docker.io/binildas/spring-boot-docker-k8s-helm]
250c3b6ce2b0: Pushed 
1bc0685fedc8: Pushed 
cc66d1dae976: Pushed 
ceaf9e1ebef5: Pushed 
9b9b7f3d56a0: Pushed 
f1b5933fe4b5: Pushed 
latest: digest: sha256:083ad89fa86e9852ba783eadb5f9c174e746ba1e0cc8404f24cc6d76fdcd9345 size: 1575
(base) binildass-MacBook-Pro:~ binil$ 

Delete image from Local Docker Repository
-----------------------------------------

(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ docker rmi binildas/spring-boot-docker-k8s-helm
Untagged: binildas/spring-boot-docker-k8s-helm:latest
Untagged: binildas/spring-boot-docker-k8s-helm@sha256:083ad89fa86e9852ba783eadb5f9c174e746ba1e0cc8404f24cc6d76fdcd9345
Deleted: sha256:0c23b3413de19c1cd748cec039e97f56053f1f794104684087978d9248e85928
Deleted: sha256:843f15f9f24789204c1181dc57900319639280dd049f11bd402791b0cbc4591d
Deleted: sha256:c12840b861499fd434b282d8db18bf05c2d178727fecb2e115ddc153a6175ef0
Deleted: sha256:81465787adcd32da479f90c3a5e7daacb38cb584120183ddea6d0eb1c02a88db
Deleted: sha256:3fe521a4d333bd3c7e1c3b8663c33dc006206ed0685119abb2a944cfece14f35
Deleted: sha256:aaa56a50ac7d9b5f4b2897e6811c5858e86c069804b332ea4ccdf12e506c4c2d
Deleted: sha256:dc5915c673ef185b703a480b1f539a6dfd115e928aefe26be3d9a60a421c3145
Deleted: sha256:7909b529b48c213f82ca2d67ff818f8df8b9f51fd555335808e25a8e0dd8ff71
Deleted: sha256:f76af392e4557a88262ab2975c139805b70657406de1f8be3aa0306a04edcb14
Deleted: sha256:9889cb2fe045eb9f5b7b3811796377440f5f6890fda70a4a92870424856985cd
(base) binildass-MacBook-Pro:~ binil$ 

Bring up the Application Serrver
--------------------------------

(base) binildass-MacBook-Pro:ch11-02 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-02
(base) binildass-MacBook-Pro:ch11-02 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch11-02 binil$ sh run.sh 
Unable to find image 'binildas/spring-boot-docker-k8s-helm:latest' locally
latest: Pulling from binildas/spring-boot-docker-k8s-helm
5843afab3874: Already exists 
53c9466125e4: Already exists 
d8d715783b80: Already exists 
af78a462dd1f: Pull complete 
71d05ae2a767: Pull complete 
04170f3d7f6b: Pull complete 
Digest: sha256:275170154dd952be90cffdff0b585d28975a47070d75f59e5f782862bb36a864
Status: Downloaded newer image for binildas/spring-boot-docker-k8s-helm:latest

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2023-05-20 07:07:33 INFO  StartupInfoLogger.logStarting:51 - Starting Application using Java...
2023-05-20 07:07:33 DEBUG StartupInfoLogger.logStarting:52 - Running with Spring Boot v3.0.6...
2023-05-20 07:07:33 INFO  SpringApplication.logStartupProfileInfo:632 - No active ...
2023-05-20 07:07:35 INFO  StartupInfoLogger.logStarted:57 - Started Application ...
2023-05-20 07:07:35 INFO  Application.main:50 - Started...
...

Test the Application
---------------------

(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ minikube ip
192.168.64.6
(base) binildass-MacBook-Pro:~ binil$ curl http://192.168.64.6:8080/
Hello Docker World : 1
(base) binildass-MacBook-Pro:~ binil$

Clean the projects
------------------
(base) binildass-MacBook-Pro:ch11-02 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-02
(base) binildass-MacBook-Pro:ch11-02 binil$ sh clean.sh 
[INFO] Scanning for projects...
[INFO] 
[INFO] ---------< com.acme.ecom.product:spring-boot-docker-k8s-helm >----------
[INFO] Building Spring Boot µS 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:3.2.0:clean (default-clean) @ spring-boot-docker-k8s-helm ---
[INFO] Deleting /Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-02/target
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.285 s
[INFO] Finished at: 2023-05-20T12:41:11+05:30
[INFO] ------------------------------------------------------------------------
(base) binildass-MacBook-Pro:ch11-02 binil$ 

List Running Containers
-----------------------

(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ docker ps -a
CONTAINER ID   IMAGE                                              COMMAND                  CREATED             STATUS                           PORTS                    NAMES
f39c79e72b6f   binildas/spring-boot-docker-k8s-helm:latest   "java -cp app:app/li…"   18 minutes ago      Up 18 minutes                    0.0.0.0:8080->8080/tcp   sad_chandrasekhar
...
(base) binildass-MacBook-Pro:~ binil$ eval $

Stop Running Container
----------------------

(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ docker stop f39c79e72b6f
f39c79e72b6f
(base) binildass-MacBook-Pro:~ binil$ 

List Running Container
-----------------------

(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ docker ps -a
CONTAINER ID   IMAGE                                              COMMAND                  CREATED             STATUS                           PORTS     NAMES
f39c79e72b6f   binildas/spring-boot-docker-k8s-helm:latest   "java -cp app:app/li…"   20 minutes ago      Exited (143) 39 seconds ago                sad_chandrasekhar
...
(base) binildass-MacBook-Pro:~ binil$ 

Remove Stopped Container
------------------------

(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ docker rm f39c79e72b6f
f39c79e72b6f
(base) binildass-MacBook-Pro:~ binil$ 


