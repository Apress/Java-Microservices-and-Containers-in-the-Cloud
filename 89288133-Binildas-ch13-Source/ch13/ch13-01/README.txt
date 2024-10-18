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

Build the Application (Local Docker Daemon not required)
-------------------------------------------------------

(base) binildass-MacBook-Pro:ch13-01 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch13/ch13-01
(base) binildass-MacBook-Pro:ch13-01 binil$ sh build.sh 
[INFO] Scanning for projects...
[INFO] 
[INFO] ---------< com.acme.ecom.product:spring-boot-docker-k8s-helm >----------
[INFO] Building Spring Boot µS 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
...
[INFO] 
[INFO] Built and pushed image as binildas/spring-boot-docker-k8s-helm
[INFO] Executing tasks:
[INFO] [============================  ] 91.7% complete
[INFO] > launching layer pushers
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  33.855 s
[INFO] Finished at: 2023-05-23T15:43:14+05:30
[INFO] ------------------------------------------------------------------------
(base) binildass-MacBook-Pro:ch13-01 binil$ 

Bring up the Application Serrver
--------------------------------

(base) binildass-MacBook-Pro:ch13-01 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch13/ch13-01
(base) binildass-MacBook-Pro:ch13-01 binil$ eval $(minikube docker-env)
(base) binildass-MacBook-Pro:ch13-01 binil$ skaffold dev --trigger notify -v debug
DEBU[0000] skaffold API not starting as it's not requested  subtask=-1 task=DevLoop
...
[INFO] 
[INFO] Built image to Docker daemon as binildas/spring-boot-docker-k8s-helm
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
...
Press Ctrl+C to exit
DEBU[0017] Change detected<nil>                          subtask=-1 task=DevLoop
Watching for changes...
...
[springboothelm] 
[springboothelm]   .   ____          _            __ _ _
[springboothelm]  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
[springboothelm] ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
[springboothelm]  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
[springboothelm]   '  |____| .__|_| |_|_| |_\__, | / / / /
[springboothelm]  =========|_|==============|___/=/_/_/_/
[springboothelm]  :: Spring Boot ::                (v3.2.0)
[springboothelm] 
...
[springboothelm] 2023-06-01 12:55:42 INFO  StartupInfoLogger.logStarted:57 - Started Application ...
[springboothelm] 2023-06-01 12:55:42 INFO  Application.main:51 - Started...
...


Inspect the Application
-----------------------

(base) binildass-MacBook-Pro:~ binil$ kubectl get all
NAME                                  READY   STATUS    RESTARTS   AGE
pod/springboothelm-6886bdb9d9-7cwv4   1/1     Running   0          3m29s

NAME                     TYPE           CLUSTER-IP       EXTERNAL-IP   PORT(S)          AGE
service/kubernetes       ClusterIP      10.96.0.1        <none>        443/TCP          15d
service/springboothelm   LoadBalancer   10.103.181.111   <pending>     8080:31831/TCP   3m29s

NAME                             READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/springboothelm   1/1     1            1           3m29s

NAME                                        DESIRED   CURRENT   READY   AGE
replicaset.apps/springboothelm-6886bdb9d9   1         1         1       3m29s
(base) binildass-MacBook-Pro:~ binil$ 

Test the Client
----------------
(base) binildass-MacBook-Pro:~ binil$ minikube service springboothelm --url
http://192.168.64.6:31831
(base) binildass-MacBook-Pro:~ binil$ curl http://192.168.64.6:31831
Hello Docker World : Counted 1 times by App Version: 1
(base) binildass-MacBook-Pro:~ binil$

Watch the Application Console
-----------------------------
[springboothelm] 
[springboothelm]   .   ____          _            __ _ _
[springboothelm]  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
[springboothelm] ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
[springboothelm]  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
[springboothelm]   '  |____| .__|_| |_|_| |_\__, | / / / /
[springboothelm]  =========|_|==============|___/=/_/_/_/
[springboothelm]  :: Spring Boot ::                (v3.2.0)
[springboothelm] 
...
[springboothelm] 2023-06-01 12:55:42 INFO  StartupInfoLogger.logStarted:57 - Started Application ...
[springboothelm] 2023-06-01 12:55:42 INFO  Application.main:51 - Started...
...
[springboothelm] 2023-06-01 12:59:50 INFO  Application.home:41 - Start
[springboothelm] 2023-06-01 12:59:50 DEBUG Application.home:43 - Inside hello.Application.home() : Counted 1 times by App Version: 1
[springboothelm] 2023-06-01 12:59:50 INFO  Application.home:44 - Returning...
...

Make some change in source code and save the file, while watching Application Console
-------------------------------------------------------------------------------------

[springboothelm] 
[springboothelm]   .   ____          _            __ _ _
[springboothelm]  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
[springboothelm] ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
[springboothelm]  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
[springboothelm]   '  |____| .__|_| |_|_| |_\__, | / / / /
[springboothelm]  =========|_|==============|___/=/_/_/_/
[springboothelm]  :: Spring Boot ::                (v3.2.0)
[springboothelm] 
...
[springboothelm] 2023-06-01 13:01:23 INFO  Application.main:51 - Started...

Test the Client again
---------------------

(base) binildass-MacBook-Pro:~ binil$ curl http://192.168.64.6:31831
Hello Docker World : Counted 1 times by App Version: 2
(base) binildass-MacBook-Pro:~ binil$ 

Watch the Application Console
-----------------------------
[springboothelm] 
[springboothelm]   .   ____          _            __ _ _
[springboothelm]  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
[springboothelm] ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
[springboothelm]  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
[springboothelm]   '  |____| .__|_| |_|_| |_\__, | / / / /
[springboothelm]  =========|_|==============|___/=/_/_/_/
[springboothelm]  :: Spring Boot ::                (v3.2.0)
[springboothelm] 
...
[springboothelm] 2023-06-01 13:01:23 INFO  Application.main:51 - Started...
[springboothelm] 2023-06-01 13:04:58 INFO  Application.home:41 - Start
[springboothelm] 2023-06-01 13:04:58 DEBUG Application.home:43 - Inside hello.Application.home() : Counted 1 times by App Version: 2
[springboothelm] 2023-06-01 13:04:58 INFO  Application.home:44 - Returning...

Stop the Application using Ctrl+C to exit
-----------------------------------------

^CCleaning up...
...
DEBU[0972] Running command: [kubectl --context minikube delete --ignore-not-found=true -f -] 
 - deployment.apps "springboothelm" deleted
 - service "springboothelm" deleted
INFO[0972] Cleanup complete in 273.772602ms             
(base) binildass-MacBook-Pro:ch13-01 binil$ 




