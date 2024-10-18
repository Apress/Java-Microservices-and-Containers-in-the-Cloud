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

(base) binildass-MacBook-Pro:ch11-04 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-04
(base) binildass-MacBook-Pro:ch11-04 binil$ eval $(minikube docker-env)clear
(base) binildass-MacBook-Pro:ch11-04 binil$ sh make.sh 
[INFO] Scanning for projects...
[INFO] 
[INFO] ---------< com.acme.ecom.product:spring-boot-docker-k8s-helm >----------
[INFO] Building Spring Boot µS 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
...
[INFO] Successfully built binildas/spring-boot-docker-k8s-helm:latest
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  8.804 s
[INFO] Finished at: 2023-05-20T15:23:30+05:30
[INFO] ------------------------------------------------------------------------
(base) binildass-MacBook-Pro:ch11-04 binil$ 


Bring up the Application Serrver
--------------------------------

(base) binildass-MacBook-Pro:ch11-04 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-04
(base) binildass-MacBook-Pro:ch11-04 binil$ eval $(minikube docker-env)clear
(base) binildass-MacBook-Pro:ch11-04 binil$ sh run.sh 
deployment.apps/springboothelm created
service/springboothelm created
http://192.168.64.6:30048
NAME                              READY   STATUS    RESTARTS   AGE
springboothelm-78f7fdc64d-kplcd   1/1     Running   0          3s
NAME             TYPE           CLUSTER-IP     EXTERNAL-IP   PORT(S)          AGE
kubernetes       ClusterIP      10.96.0.1      <none>        443/TCP          3d4h
springboothelm   LoadBalancer   10.109.29.58   <pending>     8080:30048/TCP   3s
(base) binildass-MacBook-Pro:ch11-04 binil$ 

Test the Application
---------------------

(base) binildass-MacBook-Pro:~ binil$ curl http://192.168.64.6:30048
Hello Docker World : 1
(base) binildass-MacBook-Pro:~ binil$ 

Clean the projects & Bring down Applications
--------------------------------------------

(base) binildass-MacBook-Pro:ch11-04 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-04
(base) binildass-MacBook-Pro:ch11-04 binil$ eval $(minikube docker-env)clear
(base) binildass-MacBook-Pro:ch11-04 binil$ sh clean.sh 
[INFO] Scanning for projects...
[INFO] 
[INFO] ---------< com.acme.ecom.product:spring-boot-docker-k8s-helm >----------
[INFO] Building Spring Boot µS 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:3.2.0:clean (default-clean) @ spring-boot-docker-k8s-helm ---
[INFO] Deleting /Users/binil/binil/code/mac/mybooks/docker-04/Code/ch11/ch11-04/target
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.255 s
[INFO] Finished at: 2023-05-20T15:28:47+05:30
[INFO] ------------------------------------------------------------------------
service "springboothelm" deleted
deployment.apps "springboothelm" deleted
(base) binildass-MacBook-Pro:ch11-04 binil$ 


