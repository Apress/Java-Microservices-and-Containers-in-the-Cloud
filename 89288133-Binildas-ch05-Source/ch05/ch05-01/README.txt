
Browser => ProductWeb μS => Kafka => ProductServer μS
Browser (Async HTTP) => ProductWeb μS (Sync over Async) => Kafka => ProductServer μS (Sequential)

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


Build & Install dependency
---------------------------

(base) binildass-MacBook-Pro:kafka-request-reply-util binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-01/kafka-request-reply-util
(base) binildass-MacBook-Pro:kafka-request-reply-util binil$ mvn clean install
[INFO] Scanning for projects...
[INFO]
[INFO] -------< se.callista.blog.synch_kafka:kafka-request-reply-util >--------
[INFO] Building Kafka Request Reply utility 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.335 s
[INFO] Finished at: 2024-02-27T12:35:54+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:kafka-request-reply-util binil$ 

Configure Product Server
-----------------------
/ch05/ch05-01/01-ProductServer/src/main/resources/application.yml
product:
  topic:
    request:
      numPartitions: 2

NOTE: Attempting more than 2 Product Server μS instances will not be assigned to any partition

Build Product Server
--------------------
binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-01/01-ProductServer
binildass-MacBook-Pro:01-ProductServer binil$ sh make.sh 
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
[INFO] Total time:  2.545 s
[INFO] Finished at: 2024-02-28T17:39:45+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:01-ProductServer binil$ 

Testing sequential processing within a partition
================================================

Run Product server 1
--------------------
binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-01/01-ProductServer
binildass-MacBook-Pro:01-ProductServer binil$ sh run1.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-02-28 17:41:08 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProductServerMicroserviceApplication v0.0.1-SNAPSHOT using Java 17.0.7 with PID 17179 (/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-01/01-ProductServer/target/Ecom-Product-Server-Microservice-0.0.1-SNAPSHOT.jar started by binil in /Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-01/01-ProductServer)
2024-02-28 17:41:08 DEBUG StartupInfoLogger.logStarting:51 - Running with Spring Boot v3.2.0, Spring v6.1.1
2024-02-28 17:41:08 INFO  SpringApplication.logStartupProfileInfo:653 - No active profile set, falling back to 1 default profile: "default"
2024-02-28 17:41:09 INFO  InitializationComponent.init:43 - Start
2024-02-28 17:41:09 INFO  InitializationComponent.init:44 - Doing Nothing...
2024-02-28 17:41:09 INFO  InitializationComponent.init:66 - End
2024-02-28 17:41:11 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductServerMicroserviceApplication in 3.527 seconds (process running for 4.605)

Run Product server 2
--------------------
binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-01/01-ProductServer
binildass-MacBook-Pro:01-ProductServer binil$ sh run2.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-02-28 17:41:11 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProductServerMicroserviceApplication v0.0.1-SNAPSHOT using Java 17.0.7 with PID 17181 (/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-01/01-ProductServer/target/Ecom-Product-Server-Microservice-0.0.1-SNAPSHOT.jar started by binil in /Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-01/01-ProductServer)
2024-02-28 17:41:11 DEBUG StartupInfoLogger.logStarting:51 - Running with Spring Boot v3.2.0, Spring v6.1.1
2024-02-28 17:41:11 INFO  SpringApplication.logStartupProfileInfo:653 - No active profile set, falling back to 1 default profile: "default"
2024-02-28 17:41:13 INFO  InitializationComponent.init:43 - Start
2024-02-28 17:41:13 INFO  InitializationComponent.init:44 - Doing Nothing...
2024-02-28 17:41:13 INFO  InitializationComponent.init:66 - End
2024-02-28 17:41:14 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductServerMicroserviceApplication in 3.173 seconds (process running for 4.37)

Run Product server 3
--------------------
binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-01/01-ProductServer
binildass-MacBook-Pro:01-ProductServer binil$ sh run3.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-02-28 17:41:17 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProductServerMicroserviceApplication v0.0.1-SNAPSHOT using Java 17.0.7 with PID 17184 (/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-01/01-ProductServer/target/Ecom-Product-Server-Microservice-0.0.1-SNAPSHOT.jar started by binil in /Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-01/01-ProductServer)
2024-02-28 17:41:17 DEBUG StartupInfoLogger.logStarting:51 - Running with Spring Boot v3.2.0, Spring v6.1.1
2024-02-28 17:41:17 INFO  SpringApplication.logStartupProfileInfo:653 - No active profile set, falling back to 1 default profile: "default"
2024-02-28 17:41:19 INFO  InitializationComponent.init:43 - Start
2024-02-28 17:41:19 INFO  InitializationComponent.init:44 - Doing Nothing...
2024-02-28 17:41:19 INFO  InitializationComponent.init:66 - End
2024-02-28 17:41:20 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductServerMicroserviceApplication in 3.161 seconds (process running for 4.047)
2024-02-28 17:52:23 INFO  ProductListener.listenWithHeaders:57 - Start
2024-02-28 17:52:23 DEBUG ProductListener.listenWithHeaders:58 - Listen; Request from : All
2024-02-28 17:52:23 DEBUG ProductListener.listenWithHeaders:59 - Listen; replyTopic : product-req-reply-topic
2024-02-28 17:52:23 DEBUG ProductListener.listenWithHeaders:60 - Listen; replyPartitionId : 0
2024-02-28 17:52:23 DEBUG ProductListener.listenWithHeaders:61 - Listen; receivedTopic : product-req-topic
2024-02-28 17:52:23 DEBUG ProductListener.listenWithHeaders:62 - Listen; receivedPartitionId : 0
2024-02-28 17:52:23 DEBUG ProductListener.listenWithHeaders:63 - Listen; correlationId : �<Ϊ��ED��K��^7	
2024-02-28 17:52:23 DEBUG ProductListener.listenWithHeaders:64 - Listen; offset : 0
2024-02-28 17:52:23 DEBUG ProductListener.listenWithHeaders:70 - Starting to Sleep Seconds : 6
2024-02-28 17:52:29 DEBUG ProductListener.listenWithHeaders:77 - Awakening from Sleep...
2024-02-28 17:52:29 DEBUG ProductListener.listenWithHeaders:79 - Thread[org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1,5,main]
2024-02-28 17:52:29 INFO  ProductListener.listenWithHeaders:80 - Ending...
2024-02-28 17:52:29 INFO  ProductListener.listenWithHeaders:57 - Start
2024-02-28 17:52:29 DEBUG ProductListener.listenWithHeaders:58 - Listen; Request from : All
2024-02-28 17:52:29 DEBUG ProductListener.listenWithHeaders:59 - Listen; replyTopic : product-req-reply-topic
2024-02-28 17:52:29 DEBUG ProductListener.listenWithHeaders:60 - Listen; replyPartitionId : 1
2024-02-28 17:52:29 DEBUG ProductListener.listenWithHeaders:61 - Listen; receivedTopic : product-req-topic
2024-02-28 17:52:29 DEBUG ProductListener.listenWithHeaders:62 - Listen; receivedPartitionId : 0
2024-02-28 17:52:29 DEBUG ProductListener.listenWithHeaders:63 - Listen; correlationId : ���N�A�z++Ӻ
2024-02-28 17:52:29 DEBUG ProductListener.listenWithHeaders:64 - Listen; offset : 1
2024-02-28 17:52:29 DEBUG ProductListener.listenWithHeaders:70 - Starting to Sleep Seconds : 6
2024-02-28 17:52:35 DEBUG ProductListener.listenWithHeaders:77 - Awakening from Sleep...
2024-02-28 17:52:35 DEBUG ProductListener.listenWithHeaders:79 - Thread[org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1,5,main]
2024-02-28 17:52:35 INFO  ProductListener.listenWithHeaders:80 - Ending...

Configure Product web
---------------------
/ch05/ch05-01/02-ProductWeb/src/main/resources/application.yml
kafka:
  topic:
    product:
      pinnedToPartition: true
product:
  topic:
    request:
      numPartitions: 2

NOTE: Attempt only 2 Product Web μS instances

Build Product Web
-----------------
binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-01/02-ProductWeb
binildass-MacBook-Pro:02-ProductWeb binil$ sh make.sh 
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
[INFO] Total time:  2.659 s
[INFO] Finished at: 2024-02-28T17:45:56+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:02-ProductWeb binil$

Run Product web 1
-----------------
binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-01/02-ProductWeb
binildass-MacBook-Pro:02-ProductWeb binil$ sh run1.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-02-28 17:47:59 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProductWebMicroserviceApplication v0.0.1-SNAPSHOT using Java 17.0.7 with PID 17597 (/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-01/02-ProductWeb/target/Ecom-Product-Web-Microservice-0.0.1-SNAPSHOT.jar started by binil in /Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-01/02-ProductWeb)
2024-02-28 17:47:59 DEBUG StartupInfoLogger.logStarting:51 - Running with Spring Boot v3.2.0, Spring v6.1.1
2024-02-28 17:47:59 INFO  SpringApplication.logStartupProfileInfo:653 - No active profile set, falling back to 1 default profile: "default"
2024-02-28 17:48:02 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductWebMicroserviceApplication in 3.02 seconds (process running for 4.082)
2024-02-28 17:52:23 INFO  ProductRestController.getAllProducts:69 - Start
2024-02-28 17:52:23 DEBUG ProductRestController.getAllProducts:70 - Application Name : product-web-1
2024-02-28 17:52:23 DEBUG ProductRestController.getAllProducts:71 - PinnedToPartition? : true
2024-02-28 17:52:23 DEBUG ProductRestController.getAllProducts:72 - Thread : Thread[http-nio-8080-exec-1,5,main]
2024-02-28 17:52:23 DEBUG LogAccessor.debug:191 - Using Request partition : null
2024-02-28 17:52:23 DEBUG LogAccessor.debug:191 - Using Request key : product-web-1
2024-02-28 17:52:23 DEBUG LogAccessor.debug:191 - Using Reply partition : 0
2024-02-28 17:52:23 DEBUG LogAccessor.debug:313 - Sending: ProducerRecord(topic=product-req-topic...
2024-02-28 17:52:23 DEBUG ProductRestController.getAllProducts:88 - Starting to Sleep Seconds : 4
2024-02-28 17:52:27 DEBUG ProductRestController.getAllProducts:95 - Awakening from Sleep...
2024-02-28 17:52:27 DEBUG ProductRestController.getAllProducts:97 - Thread[http-nio-8080-exec-1,5,main]
2024-02-28 17:52:27 INFO  ProductRestController.getAllProducts:98 - Ending
2024-02-28 17:52:29 DEBUG LogAccessor.debug:313 - Received: product-req-reply-topic-0@0 with correlationId: ec3cceaa-d7e4-4544-add2-4b98ce5e3709

Run Product web 2
-----------------
binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-01/02-ProductWeb
binildass-MacBook-Pro:02-ProductWeb binil$ sh run2.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-02-28 17:50:25 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProductWebMicroserviceApplication v0.0.1-SNAPSHOT using Java 17.0.7 with PID 17619 (/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-01/02-ProductWeb/target/Ecom-Product-Web-Microservice-0.0.1-SNAPSHOT.jar started by binil in /Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-01/02-ProductWeb)
2024-02-28 17:50:25 DEBUG StartupInfoLogger.logStarting:51 - Running with Spring Boot v3.2.0, Spring v6.1.1
2024-02-28 17:50:25 INFO  SpringApplication.logStartupProfileInfo:653 - No active profile set, falling back to 1 default profile: "default"
2024-02-28 17:50:27 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductWebMicroserviceApplication in 2.998 seconds (process running for 4.008)
2024-02-28 17:52:23 INFO  ProductRestController.getAllProducts:69 - Start
2024-02-28 17:52:23 DEBUG ProductRestController.getAllProducts:70 - Application Name : product-web-1
2024-02-28 17:52:23 DEBUG ProductRestController.getAllProducts:71 - PinnedToPartition? : true
2024-02-28 17:52:23 DEBUG ProductRestController.getAllProducts:72 - Thread : Thread[http-nio-8081-exec-1,5,main]
2024-02-28 17:52:23 DEBUG LogAccessor.debug:191 - Using Request partition : null
2024-02-28 17:52:23 DEBUG LogAccessor.debug:191 - Using Request key : product-web-1
2024-02-28 17:52:23 DEBUG LogAccessor.debug:191 - Using Reply partition : 1
2024-02-28 17:52:23 DEBUG LogAccessor.debug:313 - Sending: ProducerRecord(topic=product-req-topic...
2024-02-28 17:52:23 DEBUG ProductRestController.getAllProducts:88 - Starting to Sleep Seconds : 4
2024-02-28 17:52:27 DEBUG ProductRestController.getAllProducts:95 - Awakening from Sleep...
2024-02-28 17:52:27 DEBUG ProductRestController.getAllProducts:97 - Thread[http-nio-8081-exec-1,5,main]
2024-02-28 17:52:27 INFO  ProductRestController.getAllProducts:98 - Ending
2024-02-28 17:52:35 DEBUG LogAccessor.debug:313 - Received: product-req-reply-topic-1@0 with correlationId: 8403edb7-e702-4e05-8341-ed7a2b2bd3ba

List Queues created in Kafka
----------------------------
binildass-MacBook-Pro:kafka_2.13-2.5.0 binil$ sh bin/kafka-topics.sh --zookeeper localhost:2181 --list
__consumer_offsets
product-req-reply-topic
product-req-topic
binildass-MacBook-Pro:kafka_2.13-2.5.0 binil$ 

Verify Product Server μS Assignment to Kafka Partitions
-------------------------------------------------------
binildass-MacBook-Pro:kafka_2.13-2.5.0 binil$ bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group product-server

GROUP           TOPIC             PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID                                                    HOST            CLIENT-ID
product-server  product-req-topic 2          0               0               0               consumer-product-server-1-f2ae14f5-3964-4a6f-897e-954d3c447669 /127.0.0.1      consumer-product-server-1
product-server  product-req-topic 1          0               0               0               consumer-product-server-1-865f46aa-a4fe-4031-830d-8c182f74cb42 /127.0.0.1      consumer-product-server-1
product-server  product-req-topic 0          0               0               0               consumer-product-server-1-436a6f12-2073-4c43-a201-0981ed94f286 /127.0.0.1      consumer-product-server-1
binildass-MacBook-Pro:kafka_2.13-2.5.0 binil$ 

Verify Product Web μS Assignment to Kafka Partitions
-----------------------------------------------------
binildass-MacBook-Pro:kafka_2.13-2.5.0 binil$ bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group product-web

GROUP           TOPIC                   PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID                                                 HOST            CLIENT-ID
product-web     product-req-reply-topic 1          0               0               0               consumer-product-web-1-c457cc3e-cf76-4788-9230-19c89f016333 /127.0.0.1      consumer-product-web-1
product-web     product-req-reply-topic 0          0               0               0               consumer-product-web-1-347b84e6-d51a-4561-b5d1-3ec54378a47c /127.0.0.1      consumer-product-web-1
binildass-MacBook-Pro:kafka_2.13-2.5.0 binil$ 

Run 2 Curl clients to 2 Product Web μS instance, CONCURRENTLY
-------------------------------------------------------------

Curl Client 1
-------------
binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-01/02-ProductWeb
binildass-MacBook-Pro:02-ProductWeb binil$ sh curlrun1.sh 
Firing http://127.0.0.1:8080/productsweb and waiting...
------------------------------------------
Current date: Wed Feb 28 17:52:23 IST 2024
==========================================
{"products":[{"productId":"1","name":"Kamsung D3","code":"KAMSUNG-TRIOS","title":"Kamsung Trios 12 inch , black , 12 px ....","price":12000.0},{"productId":"2","name":"Lokia Pomia","code":"LOKIA-POMIA","title":"Lokia 12 inch , white , 14px ....","price":9000.0}]}
------------------------------------------
Current date: Wed Feb 28 17:52:29 IST 2024
==========================================
Response from http://127.0.0.1:8080/productsweb received.
binildass-MacBook-Pro:02-ProductWeb binil$ 


Curl Client 2
-------------
binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-01/02-ProductWeb
binildass-MacBook-Pro:02-ProductWeb binil$ sh curlrun2.sh 
Firing http://127.0.0.1:8081/productsweb and waiting...
------------------------------------------
Current date: Wed Feb 28 17:52:23 IST 2024
==========================================
{"products":[{"productId":"1","name":"Kamsung D3","code":"KAMSUNG-TRIOS","title":"Kamsung Trios 12 inch , black , 12 px ....","price":12000.0},{"productId":"2","name":"Lokia Pomia","code":"LOKIA-POMIA","title":"Lokia 12 inch , white , 14px ....","price":9000.0}]}
------------------------------------------
Current date: Wed Feb 28 17:52:35 IST 2024
==========================================
Response from http://127.0.0.1:8081/productsweb received.
binildass-MacBook-Pro:02-ProductWeb binil$ 

You could also test like:
=========================

http://localhost:8080/product.html
http://localhost:8081/product.html

or Run multiple product webs using Curl
---------------------------------------

binildass-MacBook-Pro:~ binil$ curl http://localhost:8080/productsweb
binildass-MacBook-Pro:~ binil$ curl http://localhost:8081/productsweb

