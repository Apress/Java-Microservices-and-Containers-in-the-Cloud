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
(base) binildass-MacBook-Pro:ch11-05 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-05
(base) binildass-MacBook-Pro:ch11-05 binil$ sh make.sh 
[INFO] Scanning for projects...
[INFO] 
...
[INFO] 
[INFO] Built and pushed image as binildas/spring-boot-docker-k8s-helm
[INFO] Executing tasks:
[INFO] [===========================   ] 91.7% complete
[INFO] > launching layer pushers
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  13.205 s
[INFO] Finished at: 2023-05-20T16:06:58+05:30
[INFO] ------------------------------------------------------------------------
(base) binildass-MacBook-Pro:ch11-05 binil$ 

Install the Application
-----------------------

(base) binildass-MacBook-Pro:ch11-05 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-05
(base) binildass-MacBook-Pro:ch12-05 binil$ helm install myfirstspringboot springboothelm
NAME: myfirstspringboot
LAST DEPLOYED: Mon May 29 23:32:19 2023
NAMESPACE: default
STATUS: deployed
REVISION: 1
NOTES:
1. Get the application URL by running these commands:
     NOTE: It may take a few minutes for the LoadBalancer IP to be available.
           You can watch the status of by running 'kubectl get --namespace default svc -w myfirstspringboot-springboothelm'
  export SERVICE_IP=$(kubectl get svc --namespace default myfirstspringboot-springboothelm --template "{{ range (index .status.loadBalancer.ingress 0) }}{{.}}{{ end }}")
  echo http://$SERVICE_IP:8080
(base) binildass-MacBook-Pro:ch12-05 binil$ 

Inspect the Application
-----------------------

(base) binildass-MacBook-Pro:ch12-05 binil$ helm list -a
NAME             	NAMESPACE	REVISION	UPDATED                             	STATUS  	CHART               	APP VERSION
myfirstspringboot	default  	1       	2023-05-29 23:32:19.571111 +0530 IST	deployed	springboothelm-0.1.0	latest     
(base) binildass-MacBook-Pro:ch12-05 binil$ 


(base) binildass-MacBook-Pro:~ binil$ kubectl get all
NAME                                                    READY   STATUS    RESTARTS   AGE
pod/myfirstspringboot-springboothelm-5575ff4d84-pvgzv   1/1     Running   0          88s

NAME                                       TYPE           CLUSTER-IP     EXTERNAL-IP   PORT(S)          AGE
service/kubernetes                         ClusterIP      10.96.0.1      <none>        443/TCP          12d
service/myfirstspringboot-springboothelm   LoadBalancer   10.103.65.93   <pending>     8080:31530/TCP   88s

NAME                                               READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/myfirstspringboot-springboothelm   1/1     1            1           88s

NAME                                                          DESIRED   CURRENT   READY   AGE
replicaset.apps/myfirstspringboot-springboothelm-5575ff4d84   1         1         1       88s
(base) binildass-MacBook-Pro:~ binil$ 



(base) binildass-MacBook-Pro:~ binil$ minikube service myfirstspringboot-springboothelm
|-----------|----------------------------------|-------------|---------------------------|
| NAMESPACE |               NAME               | TARGET PORT |            URL            |
|-----------|----------------------------------|-------------|---------------------------|
| default   | myfirstspringboot-springboothelm | http/8080   | http://192.168.64.6:31530 |
|-----------|----------------------------------|-------------|---------------------------|
🎉  Opening service default/myfirstspringboot-springboothelm in default browser...
(base) binildass-MacBook-Pro:~ binil$ minikube service myfirstspringboot-springboothelm --url
http://192.168.64.6:31530
(base) binildass-MacBook-Pro:~ binil$ 


(base) binildass-MacBook-Pro:ch12-05 binil$ curl http://192.168.64.6:31530
Hello Docker World : 1
(base) binildass-MacBook-Pro:ch12-05 binil$ 


(base) binildass-MacBook-Pro:ch11-05 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-05
(base) binildass-MacBook-Pro:ch12-05 binil$ helm upgrade myfirstspringboot .
Error: Chart.yaml file is missing
(base) binildass-MacBook-Pro:ch12-05 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch12/ch12-05
(base) binildass-MacBook-Pro:ch12-05 binil$ helm upgrade myfirstspringboot .
Error: Chart.yaml file is missing
(base) binildass-MacBook-Pro:ch12-05 binil$ helm upgrade myfirstspringboot springboothelm
Release "myfirstspringboot" has been upgraded. Happy Helming!
NAME: myfirstspringboot
LAST DEPLOYED: Mon May 29 23:39:52 2023
NAMESPACE: default
STATUS: deployed
REVISION: 2
NOTES:
1. Get the application URL by running these commands:
     NOTE: It may take a few minutes for the LoadBalancer IP to be available.
           You can watch the status of by running 'kubectl get --namespace default svc -w myfirstspringboot-springboothelm'
  export SERVICE_IP=$(kubectl get svc --namespace default myfirstspringboot-springboothelm --template "{{ range (index .status.loadBalancer.ingress 0) }}{{.}}{{ end }}")
  echo http://$SERVICE_IP:8080
(base) binildass-MacBook-Pro:ch12-05 binil$ 


(base) binildass-MacBook-Pro:ch12-05 binil$ helm list -a
NAME             	NAMESPACE	REVISION	UPDATED                             	STATUS  	CHART               	APP VERSION
myfirstspringboot	default  	2       	2023-05-29 23:39:52.906387 +0530 IST	deployed	springboothelm-0.1.1	latest     
(base) binildass-MacBook-Pro:ch12-05 binil$ 


(base) binildass-MacBook-Pro:~ binil$ kubectl get all
NAME                                                    READY   STATUS    RESTARTS   AGE
pod/myfirstspringboot-springboothelm-5575ff4d84-pvgzv   1/1     Running   0          8m2s
pod/myfirstspringboot-springboothelm-5575ff4d84-zqhq4   1/1     Running   0          29s

NAME                                       TYPE           CLUSTER-IP     EXTERNAL-IP   PORT(S)          AGE
service/kubernetes                         ClusterIP      10.96.0.1      <none>        443/TCP          12d
service/myfirstspringboot-springboothelm   LoadBalancer   10.103.65.93   <pending>     8080:31530/TCP   8m2s

NAME                                               READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/myfirstspringboot-springboothelm   2/2     2            2           8m2s

NAME                                                          DESIRED   CURRENT   READY   AGE
replicaset.apps/myfirstspringboot-springboothelm-5575ff4d84   2         2         2       8m2s
(base) binildass-MacBook-Pro:~ binil$ 


(base) binildass-MacBook-Pro:ch12-05 binil$ helm rollback myfirstspringboot 1
Rollback was a success! Happy Helming!
(base) binildass-MacBook-Pro:ch12-05 binil$

(base) binildass-MacBook-Pro:ch12-05 binil$ helm list -a
NAME             	NAMESPACE	REVISION	UPDATED                             	STATUS  	CHART               	APP VERSION
myfirstspringboot	default  	3       	2023-05-29 23:42:25.252089 +0530 IST	deployed	springboothelm-0.1.0	latest     
(base) binildass-MacBook-Pro:ch12-05 binil$ 

(base) binildass-MacBook-Pro:~ binil$ kubectl get all
NAME                                                    READY   STATUS    RESTARTS   AGE
pod/myfirstspringboot-springboothelm-5575ff4d84-pvgzv   1/1     Running   0          10m

NAME                                       TYPE           CLUSTER-IP     EXTERNAL-IP   PORT(S)          AGE
service/kubernetes                         ClusterIP      10.96.0.1      <none>        443/TCP          12d
service/myfirstspringboot-springboothelm   LoadBalancer   10.103.65.93   <pending>     8080:31530/TCP   10m

NAME                                               READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/myfirstspringboot-springboothelm   1/1     1            1           10m

NAME                                                          DESIRED   CURRENT   READY   AGE
replicaset.apps/myfirstspringboot-springboothelm-5575ff4d84   1         1         1       10m
(base) binildass-MacBook-Pro:~ binil$ 



Delete the Application
----------------------

(base) binildass-MacBook-Pro:ch12-05 binil$ helm delete myfirstspringboot
release "myfirstspringboot" uninstalled
(base) binildass-MacBook-Pro:ch12-05 binil$ 


(base) binildass-MacBook-Pro:ch12-05 binil$ helm list -a
NAME	NAMESPACE	REVISION	UPDATED	STATUS	CHART	APP VERSION
(base) binildass-MacBook-Pro:ch12-05 binil$ 


(base) binildass-MacBook-Pro:~ binil$ kubectl get all
NAME                 TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
service/kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   12d
(base) binildass-MacBook-Pro:~ binil$ 

Clean the projects
------------------

(base) binildass-MacBook-Pro:ch11-05 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-05
(base) binildass-MacBook-Pro:ch11-05 binil$ sh clean.sh 
[INFO] Scanning for projects...
[INFO] 
[INFO] ---------< com.acme.ecom.product:spring-boot-docker-k8s-helm >----------
[INFO] Building Spring Boot µS 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:3.2.0:clean (default-clean) @ spring-boot-docker-k8s-helm ---
[INFO] Deleting /Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-05/target
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.297 s
[INFO] Finished at: 2023-05-20T16:31:39+05:30
[INFO] ------------------------------------------------------------------------
(base) binildass-MacBook-Pro:ch11-05 binil$ 



