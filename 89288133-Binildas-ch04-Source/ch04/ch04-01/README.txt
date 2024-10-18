Browser (Sync HTTP)  => ProductWeb μS (Sync over Async) => Kafka => ProductServer μS

Start Zookeeper
--------------

(base) binildass-MacBook-Pro:kafka_2.13-2.5.0 binil$ pwd
/Users/binil/Applns/apache/kafka/kafka_2.13-2.5.0
kafka_2.12-2.2.1> bin/zookeeper-server-start.sh config/zookeeper.properties

Start Kafka
----------

(base) binildass-MacBook-Pro:kafka_2.13-2.5.0 binil$ pwd
/Users/binil/Applns/apache/kafka/kafka_2.13-2.5.0
kafka_2.12-2.2.1> bin/kafka-server-start.sh config/server.properties

Build the Product server
------------------------
(base) binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch04/ch04-01/01-ProductServer
(base) binildass-MacBook-Pro:01-ProductServer binil$ sh make.sh 
[INFO] Scanning for projects...
[INFO] 
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.785 s
[INFO] Finished at: 2024-01-23T11:43:04+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:01-ProductServer binil$ 

Run the Product server
----------------------

binildass-MacBook-Pro:01-ProductServer binil$ sh run.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-01-23 11:43:59 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProductServer...
...
2024-01-23 11:44:00 INFO  InitializationComponent.init:43 - Start
2024-01-23 11:44:00 INFO  InitializationComponent.init:44 - Doing Nothing...
2024-01-23 11:44:00 INFO  InitializationComponent.init:66 - End
2024-01-23 11:44:01 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductServer...
2024-01-23 11:56:03 DEBUG ProductListener.listenWithHeaders:58 - Received request : All
2024-01-23 11:56:03 DEBUG ProductListener.listenWithHeaders:59 - Listen; replyTopic : product-req-reply-topic
2024-01-23 11:56:03 DEBUG ProductListener.listenWithHeaders:60 - Listen; receivedTopic : product-req-topic
2024-01-23 11:56:03 DEBUG ProductListener.listenWithHeaders:61 - Listen; receivedPartitionId : 0
2024-01-23 11:56:03 DEBUG ProductListener.listenWithHeaders:62 - Listen; correlationId : _ƈ3OHc�i��[���
2024-01-23 11:56:03 DEBUG ProductListener.listenWithHeaders:63 - Listen; offset : 0
2024-01-23 11:56:03 INFO  ProductListener.listenWithHeaders:80 - Ending...

Build the Product web
---------------------

(base) binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch04/ch04-01/02-ProductWeb
(base) binildass-MacBook-Pro:02-ProductWeb binil$ sh make.sh 
[INFO] Scanning for projects...
[INFO] 
[INFO] --------< com.acme.ecom.product:Ecom-Product-Web-Microservice >---------
[INFO] Building Ecom-Product-Microservice 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.001 s
[INFO] Finished at: 2024-01-23T11:43:07+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:02-ProductWeb binil$ 

Run the Product web
-------------------

binildass-MacBook-Pro:02-ProductWeb binil$ sh run.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-01-23 11:54:08 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProductWebMicro...
...
2024-01-23 11:54:10 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductWebMicro...
2024-01-23 11:56:03 INFO  ProductRestController.getAllProducts:74 - Start
2024-01-23 11:56:03 DEBUG ProductRestController.getAllProducts:75 - Thread : Thread[http-nio-8080-exec-5,5,main]
2024-01-23 11:56:03 INFO  ProductRestController.lambda$getAllProducts$0:91 - kafka_replyTopic:[B@47838c21
2024-01-23 11:56:03 INFO  ProductRestController.lambda$getAllProducts$0:91 - kafka_correlationId:[B@57eccc15
2024-01-23 11:56:03 INFO  ProductRestController.lambda$getAllProducts$0:91 - __TypeId__:[B@565bfacd
2024-01-23 11:56:03 DEBUG ProductRestController.getAllProducts:96 - Reply success -> ...
2024-01-23 11:56:03 DEBUG ProductRestController.getAllProducts:97 - Thread : Thread[http-nio-...
2024-01-23 11:56:03 INFO  ProductRestController.getAllProducts:98 - Ending...

Test the Client
---------------
Open below link in Chrome

http://localhost:8080/product.html

binildass-MacBook-Pro:~ binil$ curl http://localhost:8080/productsweb
[{"productId":"1","name":"Kamsung D3","code":"KAMSUNG-TRIOS","title":"Kamsung Trios 12 inch , black , 12 px ....","price":12000.0},{"productId":"2","name":"Lokia Pomia","code":"LOKIA-POMIA","title":"Lokia 12 inch , white , 14px ....","price":9000.0}]
binildass-MacBook-Pro:~ binil$





