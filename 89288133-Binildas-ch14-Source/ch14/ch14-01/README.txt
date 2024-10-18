binildass-MacBook-Pro:ch14-01 binil$ terraform init
...
binildass-MacBook-Pro:ch14-01 binil$ terraform validate
Success! The configuration is valid.

binildass-MacBook-Pro:ch14-01 binil$ terraform plan
  ...

binildass-MacBook-Pro:ch14-01 binil$ terraform apply
var.ami_id
  Enter a value: ami-061058b2c8f7fb264

var.ami_key_pair_name
  Enter a value: bdca-key-01

var.ami_name
  Enter a value: bdca-Instance-03
...
Apply complete! Resources: 8 added, 0 changed, 0 destroyed.
binildass-MacBook-Pro:ch14-01 binil$ 

------------------

(base) binildass-MacBook-Pro:AWS binil$ ls
BDCA-01.pem	bdca-key-01.pem
(base) binildass-MacBook-Pro:AWS binil$ chmod 600 ./bdca-key-01.pem 
(base) binildass-MacBook-Pro:AWS binil$ ssh -i "bdca-key-01.pem" ubuntu@ec2-13-251-78-88.ap-southeast-1.compute.amazonaws.com
Welcome to Ubuntu 16.04.4 LTS (GNU/Linux 4.4.0-1114-aws x86_64)
...
ubuntu@ip-10-0-39-239:~$ pwd
/home/ubuntu
ubuntu@ip-10-0-39-239:~$ java -version
java version "1.8.0_161"
Java(TM) SE Runtime Environment (build 1.8.0_161-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.161-b12, mixed mode)
ubuntu@ip-10-0-39-239:~$ 

------------------

binildass-MacBook-Pro:ch14-01 binil$ ssh -i "/Users/binil/AWS/bdca-key-01.pem" ubuntu@ec2-13-228-93-93.ap-southeast-1.compute.amazonaws.com
The authenticity of host 'ec2-13-228-93-93.ap-southeast-1.compute.amazonaws.com (13.228.93.93)' can't be established.
ECDSA key fingerprint is SHA256:TK4sg3Pw8w1crRgI/KebZJ///AavY8uSnKwZmFzqTJw.
Are you sure you want to continue connecting (yes/no)? yes
Warning: Permanently added 'ec2-13-228-93-93.ap-southeast-1.compute.amazonaws.com,13.228.93.93' (ECDSA) to the list of known hosts.
Welcome to Ubuntu 16.04.4 LTS (GNU/Linux 4.4.0-1114-aws x86_64)
...
ubuntu@ip-10-0-53-123:~$ java -version
java version "1.8.0_161"
Java(TM) SE Runtime Environment (build 1.8.0_161-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.161-b12, mixed mode)
ubuntu@ip-10-0-53-123:~$ pwd
/home/ubuntu
ubuntu@ip-10-0-53-123:~$ ls
Nessus-7.0.2-ubuntu1110_amd64.deb
ubuntu@ip-10-0-53-123:~$ 

----------------

binildass-MacBook-Pro:ch14-01 binil$ scp -i "/Users/binil/AWS/bdca-key-01.pem" /Users/binil/binil/code/mac/mybooks/docker-03/ch01/ch01-01/02-ProductWeb/target/Ecom-Product-Web-Microservice-0.0.1-SNAPSHOT.jar ubuntu@ec2-13-228-93-93.ap-southeast-1.compute.amazonaws.com:/home/ubuntu/
Ecom-Product-Web-Microservice-0.0.1-SNAPSHOT.jar                                           100%   20MB   1.6MB/s   00:12    
binildass-MacBook-Pro:ch14-01 binil$

------------------

ubuntu@ip-10-0-53-123:~$ pwd
/home/ubuntu
ubuntu@ip-10-0-53-123:~$ ls
Ecom-Product-Web-Microservice-0.0.1-SNAPSHOT.jar  Nessus-7.0.2-ubuntu1110_amd64.deb
ubuntu@ip-10-0-53-123:~$ java -jar -Dserver.port=8080 ./Ecom-Product-Web-Microservice-0.0.1-SNAPSHOT.jar

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2021-07-03 00:38:41 INFO  StartupInfoLogger.logStarting:55 - Starting EcomProductMicroserviceApplication v0.0.1-SNAPSHOT using Java 1.8.0_161 on ip-10-0-53-123 with PID 4892 (/home/ubuntu/Ecom-Product-Web-Microservice-0.0.1-SNAPSHOT.jar started by ubuntu in /home/ubuntu)
2021-07-03 00:38:41 DEBUG StartupInfoLogger.logStarting:56 - Running with Spring Boot v2.4.4, Spring v5.3.5
2021-07-03 00:38:41 INFO  SpringApplication.logStartupProfileInfo:662 - No active profile set, falling back to default profiles: default
2021-07-03 00:38:46 INFO  InitializationComponent.init:58 - Start
2021-07-03 00:38:46 DEBUG InitializationComponent.init:60 - Doing Nothing...
2021-07-03 00:38:46 INFO  InitializationComponent.init:62 - End
2021-07-03 00:38:49 INFO  StartupInfoLogger.logStarted:61 - Started EcomProductMicroserviceApplication in 9.914 seconds (JVM running for 12.273)

----------------------

Test the Client
---------------
Open below link in Chrome

http://ec2-13-228-93-93.ap-southeast-1.compute.amazonaws.com:8080/product.html

----------------------

ubuntu@ip-10-0-53-123:~$ java -jar -Dserver.port=8080 ./Ecom-Product-Web-Microservice-0.0.1-SNAPSHOT.jar

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2021-07-03 00:38:41 INFO  StartupInfoLogger.logStarting:55 - Starting EcomProductMicroserviceApplication v0.0.1-SNAPSHOT using Java 1.8.0_161 on ip-10-0-53-123 with PID 4892 (/home/ubuntu/Ecom-Product-Web-Microservice-0.0.1-SNAPSHOT.jar started by ubuntu in /home/ubuntu)
2021-07-03 00:38:41 DEBUG StartupInfoLogger.logStarting:56 - Running with Spring Boot v2.4.4, Spring v5.3.5
2021-07-03 00:38:41 INFO  SpringApplication.logStartupProfileInfo:662 - No active profile set, falling back to default profiles: default
2021-07-03 00:38:46 INFO  InitializationComponent.init:58 - Start
2021-07-03 00:38:46 DEBUG InitializationComponent.init:60 - Doing Nothing...
2021-07-03 00:38:46 INFO  InitializationComponent.init:62 - End
2021-07-03 00:38:49 INFO  StartupInfoLogger.logStarted:61 - Started EcomProductMicroserviceApplication in 9.914 seconds (JVM running for 12.273)
2021-07-03 00:41:09 INFO  ProductRestController.getAllProducts:84 - Start
2021-07-03 00:41:09 DEBUG ProductRestController.lambda$getAllProducts$0:94 - Product [productId=1, name=Kamsung D3, code=KAMSUNG-TRIOS, title=Kamsung Trios 12 inch , black , 12 px ...., description=, imgUrl=, price=12000.0, productCategoryName=]
2021-07-03 00:41:09 DEBUG ProductRestController.lambda$getAllProducts$0:94 - Product [productId=2, name=Lokia Pomia, code=LOKIA-POMIA, title=Lokia 12 inch , white , 14px ...., description=, imgUrl=, price=9000.0, productCategoryName=]
2021-07-03 00:41:09 INFO  ProductRestController.getAllProducts:95 - Ending


===============================================================

(base) binildass-MBP:ch14-01 binil$ terraform destroy

===============================================================