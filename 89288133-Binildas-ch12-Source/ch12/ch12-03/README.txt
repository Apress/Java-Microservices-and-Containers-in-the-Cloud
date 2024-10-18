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

Configure Maven to push to public Docker Hub Repository
-------------------------------------------------------

cp /Users/binil/Applns/apache/maven/apache-maven-3.6.2/conf/settings.xml /Users/binil/.m2

cd /Users/binil/.m2
vi settings.xml

<servers>
    <server>
        <id>registry.hub.docker.com</id>
        <username>binildas</username>
        <password><DockerHub ******** Password></password>
    </server>
</servers>

Build the Application
---------------------

(base) binildass-MacBook-Pro:ch11-03 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-03
(base) binildass-MacBook-Pro:ch11-03 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch11-03 binil$ sh make.sh 
[INFO] Scanning for projects...
[WARNING] 
...
[INFO] 
[INFO] ---------< com.acme.ecom.product:spring-boot-docker-k8s-helm >----------
[INFO] Building Spring Boot µS 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
...
[INFO] Using credentials from Docker config (/Users/binil/.docker/config.json) for ...
[INFO] The base image requires auth. Trying again for eclipse-temurin:17-jre...
[INFO] Using credentials from Docker config (/Users/binil/.docker/config.json) for ...
[INFO] Using base image with digest: sha256:620beab172aa74c8763723f59214ccf9fbb7144cbfe09c0c40f7810956b2323d
[INFO] 
[INFO] Container entrypoint set to [java, -cp, @/app/jib-classpath-file, com.acme.ecom.product.Application]
[INFO] 
[INFO] Built and pushed image as binildas/spring-boot-docker-k8s-helm
[INFO] Executing tasks:
[INFO] [==============================] 100.0% complete
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  27.088 s
[INFO] Finished at: 2023-05-20T13:09:48+05:30
[INFO] ------------------------------------------------------------------------
(base) binildass-MacBook-Pro:ch11-03 binil$ 

Bring up the Application Serrver
--------------------------------
(base) binildass-MacBook-Pro:ch11-03 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-03
(base) binildass-MacBook-Pro:ch11-03 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch11-03 binil$ sh run.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2023-05-20 07:45:25 INFO  StartupInfoLogger.logStarting:51 - Starting Application using Java 17-ea ...
2023-05-20 07:45:25 DEBUG StartupInfoLogger.logStarting:52 - Running with Spring Boot v3.0.6...
2023-05-20 07:45:25 INFO  SpringApplication.logStartupProfileInfo:632 - No active profile set...
2023-05-20 07:45:27 INFO  StartupInfoLogger.logStarted:57 - Started Application in 2.809 seconds ...
2023-05-20 07:45:27 INFO  Application.main:50 - Started...
2023-05-20 07:46:08 INFO  Application.home:40 - Start
2023-05-20 07:46:08 DEBUG Application.home:42 - Inside hello.Application.home() : 1
2023-05-20 07:46:08 INFO  Application.home:43 - Returning...
...


Test the Application
---------------------

(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:~ binil$ minikube ip
192.168.64.6
(base) binildass-MacBook-Pro:~ binil$ curl http://192.168.64.6:8080
Hello Docker World : 1
(base) binildass-MacBook-Pro:~ binil$ 

Clean the projects
------------------

(base) binildass-MacBook-Pro:ch11-03 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-03
(base) binildass-MacBook-Pro:ch11-03 binil$ sh clean.sh 
[INFO] Scanning for projects...
[WARNING] 
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.241 s
[INFO] Finished at: 2023-05-20T13:19:36+05:30
[INFO] ------------------------------------------------------------------------
(base) binildass-MacBook-Pro:ch11-03 binil$ 

Clean the Local Docker Runtime
------------------------------
(base) binildass-MacBook-Pro:~ binil$ eval $(minikube docker-env)

(base) binildass-MacBook-Pro:~ binil$ docker ps -a
CONTAINER ID   IMAGE                                              COMMAND                  CREATED          STATUS                      PORTS                    NAMES
12a30743f3fa   binildas/spring-boot-docker-k8s-helm:latest   "java -cp app:app/li…"   3 minutes ago    Up 3 minutes                0.0.0.0:8080->8080/tcp   inspiring_goodall
...
(base) binildass-MacBook-Pro:~ binil$ docker stop 12a30743f3fa
12a30743f3fa
(base) binildass-MacBook-Pro:~ binil$ docker rm 12a30743f3fa
12a30743f3fa
(base) binildass-MacBook-Pro:~ binil$ 


