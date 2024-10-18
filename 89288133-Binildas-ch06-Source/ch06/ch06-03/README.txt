Aggregate Kafka Listener

                                      +-----> ProductServer μS 1
                                      |
                                      |
Browser => ProductWeb μS => Kafka => -+-----> ProductServer μS 2
                                      |
                                      |
                                      +-----> ProductServer μS 3


Start Zookeeper
--------------

kafka_2.12-2.2.1> bin/zookeeper-server-start.sh config/zookeeper.properties

You can stop Zookeeper later:
bin/zookeeper-server-stop.sh

Start Kafka
----------
kafka_2.12-2.2.1> bin/kafka-server-start.sh config/server.properties

You can stop Kafka later:
bin/kafka-server-stop.sh

Note configuration:
-------------------
ch06/ch06-03/02-ProductWeb/src/main/resources/application.properties
product.topic.reply.aggregatingTimeout: 6

Build Product Server
--------------------
(base) binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch06/ch06-03/01-ProductServer
(base) binildass-MacBook-Pro:01-ProductServer binil$ sh make.sh 
[INFO] Scanning for projects...
[INFO]
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.568 s
[INFO] Finished at: 2023-05-16T22:46:11+05:30
[INFO] ------------------------------------------------------------------------
(base) binildass-MacBook-Pro:01-ProductServer binil$ 

Build Product Web
-----------------
(base) binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch06/ch06-03/02-ProductWeb
(base) binildass-MacBook-Pro:02-ProductWeb binil$ sh make.sh 
[INFO] Scanning for projects...
[INFO] 
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.079 s
[INFO] Finished at: 2023-05-16T22:47:15+05:30
[INFO] ------------------------------------------------------------------------
(base) binildass-MacBook-Pro:02-ProductWeb binil$ 

Run Product server 1
--------------------
(base) binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-03/ch06/ch06-03/01-ProductServer
(base) binildass-MacBook-Pro:01-ProductServer binil$ sh run1.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.0.6)

2023-05-16 22:48:05 INFO  org.springframework.boot.StartupInfoLogger.logStarting:51 - Starting EcomProductServerMicroservice...
...
2023-05-16 22:48:06 INFO  com.acme.ecom.product.InitializationComponent.init:45 - Start
2023-05-16 22:48:06 INFO  com.acme.ecom.product.InitializationComponent.init:46 - Doing Nothing...
2023-05-16 22:48:06 INFO  com.acme.ecom.product.InitializationComponent.init:68 - End
2023-05-16 22:48:07 INFO  org.springframework.boot.StartupInfoLogger.logStarted:57 - Started EcomProductServerMicroserviceApplication in 2.189 seconds (process running for 2.952)
2023-05-16 22:48:44 INFO  com.acme.ecom.product.kafka.client.ProductListener.listen:50 - Start
2023-05-16 22:48:44 DEBUG com.acme.ecom.product.kafka.client.ProductListener.listen:51 - Request : All
2023-05-16 22:48:44 INFO  com.acme.ecom.product.kafka.client.ProductListener.listen:56 - Starting Sleep Seconds : 2
2023-05-16 22:48:46 INFO  com.acme.ecom.product.kafka.client.ProductListener.listen:63 - Awakening from Sleep...
2023-05-16 22:48:46 INFO  com.acme.ecom.product.kafka.client.ProductListener.listen:64 - Ending...


Run Product server 2
--------------------
(base) binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-03/ch06/ch06-03/01-ProductServer
(base) binildass-MacBook-Pro:01-ProductServer binil$ sh run2.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.0.6)

2023-05-16 22:48:08 INFO  org.springframework.boot.StartupInfoLogger.logStarting:51 - Starting EcomProductServerMicroservice...
...
2023-05-16 22:48:09 INFO  com.acme.ecom.product.InitializationComponent.init:45 - Start
2023-05-16 22:48:09 INFO  com.acme.ecom.product.InitializationComponent.init:46 - Doing Nothing...
2023-05-16 22:48:09 INFO  com.acme.ecom.product.InitializationComponent.init:68 - End
2023-05-16 22:48:10 INFO  org.springframework.boot.StartupInfoLogger.logStarted:57 - Started EcomProductServerMicroserviceApplication in 2.12 seconds (process running for 2.752)
2023-05-16 22:48:44 INFO  com.acme.ecom.product.kafka.client.ProductListener.listen:50 - Start
2023-05-16 22:48:44 DEBUG com.acme.ecom.product.kafka.client.ProductListener.listen:51 - Request : All
2023-05-16 22:48:44 INFO  com.acme.ecom.product.kafka.client.ProductListener.listen:56 - Starting Sleep Seconds : 10
2023-05-16 22:48:54 INFO  com.acme.ecom.product.kafka.client.ProductListener.listen:63 - Awakening from Sleep...
2023-05-16 22:48:54 INFO  com.acme.ecom.product.kafka.client.ProductListener.listen:64 - Ending...

Run Product server 3
--------------------
(base) binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-03/ch06/ch06-03/01-ProductServer
(base) binildass-MacBook-Pro:01-ProductServer binil$ sh run3.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.0.6)

2023-05-16 22:48:11 INFO  org.springframework.boot.StartupInfoLogger.logStarting:51 - Starting EcomProductServerMicroservice...
...
2023-05-16 22:48:12 INFO  com.acme.ecom.product.InitializationComponent.init:45 - Start
2023-05-16 22:48:12 INFO  com.acme.ecom.product.InitializationComponent.init:46 - Doing Nothing...
2023-05-16 22:48:12 INFO  com.acme.ecom.product.InitializationComponent.init:68 - End
2023-05-16 22:48:13 INFO  org.springframework.boot.StartupInfoLogger.logStarted:57 - Started EcomProductServerMicroserviceApplication in 2.334 seconds (process running for 2.978)
2023-05-16 22:48:44 INFO  com.acme.ecom.product.kafka.client.ProductListener.listen:50 - Start
2023-05-16 22:48:44 DEBUG com.acme.ecom.product.kafka.client.ProductListener.listen:51 - Request : All
2023-05-16 22:48:44 INFO  com.acme.ecom.product.kafka.client.ProductListener.listen:56 - Starting Sleep Seconds : 3
2023-05-16 22:48:47 INFO  com.acme.ecom.product.kafka.client.ProductListener.listen:63 - Awakening from Sleep...
2023-05-16 22:48:47 INFO  com.acme.ecom.product.kafka.client.ProductListener.listen:64 - Ending...

Run Product web
--------------
(base) binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-03/ch06/ch06-03/02-ProductWeb
(base) binildass-MacBook-Pro:02-ProductWeb binil$ sh run.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.0.6)

2023-05-16 22:48:16 INFO  org.springframework.boot.StartupInfoLogger.logStarting:51 - Starting EcomProductWebMicroservice... 
...
2023-05-16 22:48:18 INFO  org.springframework.boot.StartupInfoLogger.logStarted:57 - Started EcomProductWebMicroservice...
2023-05-16 22:48:44 INFO  com.acme.ecom.product.controller.ProductRestController.getAllProducts:71 - Start
2023-05-16 22:48:44 INFO  com.acme.ecom.product.controller.ProductRestController.getAllProducts:72 - Thread : Thread[http-nio-8080-exec-5,5,main]
2023-05-16 22:48:46 DEBUG com.acme.ecom.product.KafkaConfig.lambda$replyKafkaTemplate$0:129 - list.size() : 1; timeout : false
2023-05-16 22:48:47 DEBUG com.acme.ecom.product.KafkaConfig.lambda$replyKafkaTemplate$0:129 - list.size() : 2; timeout : false
2023-05-16 22:48:50 DEBUG com.acme.ecom.product.KafkaConfig.lambda$replyKafkaTemplate$0:129 - list.size() : 2; timeout : true
2023-05-16 22:48:50 DEBUG com.acme.ecom.product.controller.ProductRestController.getAllProducts:83 - Reply success ->[ConsumerRecord...
2023-05-16 22:48:50 INFO  com.acme.ecom.product.controller.ProductRestController.getAllProducts:84 - Thread : Thread[http-nio-8080-exec-5,5,main]


Test the Client in Browser
--------------------------
Open below link:

http://localhost:8080/product.html

OR

Test the Client in cURL
-----------------------

(base) binildass-MBP:~ binil$ curl http://localhost:8080/productsweb
[{"productId":"1","name":"Kamsung D3","code":"KAMSUNG-TRIOS","title":"Kamsung Trios 12 inch , black , 12 px ....","price":12000.0},{"productId":"2","name":"Lokia Pomia","code":"LOKIA-POMIA","title":"Lokia 12 inch , white , 14px ....","price":9000.0},{"productId":"1","name":"Kamsung D3","code":"KAMSUNG-TRIOS","title":"Kamsung Trios 12 inch , black , 12 px ....","price":12000.0},{"productId":"2","name":"Lokia Pomia","code":"LOKIA-POMIA","title":"Lokia 12 inch , white , 14px ....","price":9000.0}]
(base) binildass-MBP:~ binil$ 


Make below change in:
---------------------
ch06/ch06-03/02-ProductWeb/src/main/resources/application.properties
product.topic.reply.aggregatingTimeout: 15

Re-Build Product Web
-------------------

(base) binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-03/ch06/ch06-03/02-ProductWeb
(base) binildass-MacBook-Pro:02-ProductWeb binil$ sh make.sh

ch06\ch06-03\ProductWeb>mvn -Dmaven.test.skip=true clean package

Run Product web
---------------

(base) binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-03/ch06/ch06-03/02-ProductWeb
(base) binildass-MacBook-Pro:02-ProductWeb binil$ sh run.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.0.6)

2023-05-16 22:54:25 INFO  org.springframework.boot.StartupInfoLogger.logStarting:51 - Starting EcomProductWebMicroservice...
...
2023-05-16 22:54:27 INFO  org.springframework.boot.StartupInfoLogger.logStarted:57 - Started EcomProductWebMicroservice...
2023-05-16 22:54:45 INFO  com.acme.ecom.product.controller.ProductRestController.getAllProducts:71 - Start
2023-05-16 22:54:45 INFO  com.acme.ecom.product.controller.ProductRestController.getAllProducts:72 - Thread : Thread[http-nio-8080-exec-5,5,main]
2023-05-16 22:54:47 DEBUG com.acme.ecom.product.KafkaConfig.lambda$replyKafkaTemplate$0:129 - list.size() : 1; timeout : false
2023-05-16 22:54:48 DEBUG com.acme.ecom.product.KafkaConfig.lambda$replyKafkaTemplate$0:129 - list.size() : 2; timeout : false
2023-05-16 22:54:55 DEBUG com.acme.ecom.product.KafkaConfig.lambda$replyKafkaTemplate$0:129 - list.size() : 3; timeout : false
2023-05-16 22:54:55 DEBUG com.acme.ecom.product.controller.ProductRestController.getAllProducts:83 - Reply success ->[ConsumerRecord...
2023-05-16 22:54:55 INFO  com.acme.ecom.product.controller.ProductRestController.getAllProducts:84 - Thread : Thread[http-nio-8080-exec-5,5,main]


Test the Client in Browser Again
--------------------------------
Open below link:

http://localhost:8080/product.html