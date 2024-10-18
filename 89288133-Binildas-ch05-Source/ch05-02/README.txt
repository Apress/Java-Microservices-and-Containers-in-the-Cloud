
Browser => ProductWeb μS => Kafka => ProductServer μS
Browser (Async HTTP) => ProductWeb μS (Sync over Async) => Kafka => ProductServer μS (Parallel)

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
/ch05/ch05-02/01-ProductServer/src/main/resources/application.yml
product:
  topic:
    request:
      numPartitions: 3

Build Product Server
--------------------
binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-02/01-ProductServer
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
[INFO] Total time:  2.510 s
[INFO] Finished at: 2024-02-29T17:49:41+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:01-ProductServer binil$ 

Testing parallel processing within a partition
================================================

Run Product server 1
--------------------
binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-02/01-ProductServer
binildass-MacBook-Pro:01-ProductServer binil$ sh run1.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-02-29 19:51:31 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProductServerMicroserviceApplication v0.0.1-SNAPSHOT using Java 17.0.7 with PID 14572 (/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-02/01-ProductServer/target/Ecom-Product-Server-Microservice-0.0.1-SNAPSHOT.jar started by binil in /Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-02/01-ProductServer)
2024-02-29 19:51:31 DEBUG StartupInfoLogger.logStarting:51 - Running with Spring Boot v3.2.0, Spring v6.1.1
2024-02-29 19:51:31 INFO  SpringApplication.logStartupProfileInfo:653 - No active profile set, falling back to 1 default profile: "default"
2024-02-29 19:51:32 INFO  InitializationComponent.init:43 - Start
2024-02-29 19:51:32 INFO  InitializationComponent.init:44 - Doing Nothing...
2024-02-29 19:51:32 INFO  InitializationComponent.init:66 - End
2024-02-29 19:51:33 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductServerMicroserviceApplication in 2.58 seconds (process running for 3.417)
2024-02-29 19:52:16 INFO  ProductListener.listenWithHeaders:57 - Start
2024-02-29 19:52:16 DEBUG ProductListener.listenWithHeaders:58 - Listen; Request from : All
2024-02-29 19:52:16 DEBUG ProductListener.listenWithHeaders:59 - Listen; replyTopic : product-req-reply-topic
2024-02-29 19:52:16 DEBUG ProductListener.listenWithHeaders:60 - Listen; replyPartitionId : 0
2024-02-29 19:52:16 DEBUG ProductListener.listenWithHeaders:61 - Listen; receivedTopic : product-req-topic
2024-02-29 19:52:16 DEBUG ProductListener.listenWithHeaders:62 - Listen; receivedPartitionId : 0
2024-02-29 19:52:16 DEBUG ProductListener.listenWithHeaders:63 - Listen; correlationId :  E����IC��0
2024-02-29 19:52:16 DEBUG ProductListener.listenWithHeaders:64 - Listen; offset : 0
2024-02-29 19:52:16 DEBUG ProductListener.listenWithHeaders:70 - Starting to Sleep Seconds : 6
2024-02-29 19:52:23 DEBUG ProductListener.listenWithHeaders:77 - Awakening from Sleep...
2024-02-29 19:52:23 DEBUG ProductListener.listenWithHeaders:79 - Thread[org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1,5,main]
2024-02-29 19:52:23 INFO  ProductListener.listenWithHeaders:80 - Ending...

Run Product server 2
--------------------
binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-02/01-ProductServer
binildass-MacBook-Pro:01-ProductServer binil$ sh run2.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-02-29 19:51:35 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProductServerMicroserviceApplication v0.0.1-SNAPSHOT using Java 17.0.7 with PID 14574 (/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-02/01-ProductServer/target/Ecom-Product-Server-Microservice-0.0.1-SNAPSHOT.jar started by binil in /Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-02/01-ProductServer)
2024-02-29 19:51:35 DEBUG StartupInfoLogger.logStarting:51 - Running with Spring Boot v3.2.0, Spring v6.1.1
2024-02-29 19:51:35 INFO  SpringApplication.logStartupProfileInfo:653 - No active profile set, falling back to 1 default profile: "default"
2024-02-29 19:51:37 INFO  InitializationComponent.init:43 - Start
2024-02-29 19:51:37 INFO  InitializationComponent.init:44 - Doing Nothing...
2024-02-29 19:51:37 INFO  InitializationComponent.init:66 - End
2024-02-29 19:51:39 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductServerMicroserviceApplication in 4.171 seconds (process running for 4.85)
2024-02-29 19:52:17 INFO  ProductListener.listenWithHeaders:57 - Start
2024-02-29 19:52:17 DEBUG ProductListener.listenWithHeaders:58 - Listen; Request from : All
2024-02-29 19:52:17 DEBUG ProductListener.listenWithHeaders:59 - Listen; replyTopic : product-req-reply-topic
2024-02-29 19:52:17 DEBUG ProductListener.listenWithHeaders:60 - Listen; replyPartitionId : 1
2024-02-29 19:52:17 DEBUG ProductListener.listenWithHeaders:61 - Listen; receivedTopic : product-req-topic
2024-02-29 19:52:17 DEBUG ProductListener.listenWithHeaders:62 - Listen; receivedPartitionId : 1
2024-02-29 19:52:17 DEBUG ProductListener.listenWithHeaders:63 - Listen; correlationId : ;�Pw�F��|5�܊��
2024-02-29 19:52:17 DEBUG ProductListener.listenWithHeaders:64 - Listen; offset : 0
2024-02-29 19:52:17 DEBUG ProductListener.listenWithHeaders:70 - Starting to Sleep Seconds : 6
2024-02-29 19:52:23 DEBUG ProductListener.listenWithHeaders:77 - Awakening from Sleep...
2024-02-29 19:52:23 DEBUG ProductListener.listenWithHeaders:79 - Thread[org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1,5,main]
2024-02-29 19:52:23 INFO  ProductListener.listenWithHeaders:80 - Ending...

Run Product server 3
--------------------
binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-02/01-ProductServer
binildass-MacBook-Pro:01-ProductServer binil$ sh run3.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-02-29 19:51:37 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProductServerMicroserviceApplication v0.0.1-SNAPSHOT using Java 17.0.7 with PID 14576 (/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-02/01-ProductServer/target/Ecom-Product-Server-Microservice-0.0.1-SNAPSHOT.jar started by binil in /Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-02/01-ProductServer)
2024-02-29 19:51:38 DEBUG StartupInfoLogger.logStarting:51 - Running with Spring Boot v3.2.0, Spring v6.1.1
2024-02-29 19:51:38 INFO  SpringApplication.logStartupProfileInfo:653 - No active profile set, falling back to 1 default profile: "default"
2024-02-29 19:51:41 INFO  InitializationComponent.init:43 - Start
2024-02-29 19:51:41 INFO  InitializationComponent.init:44 - Doing Nothing...
2024-02-29 19:51:41 INFO  InitializationComponent.init:66 - End
2024-02-29 19:51:45 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductServerMicroserviceApplication in 7.885 seconds (process running for 8.939)
2024-02-29 19:52:16 INFO  ProductListener.listenWithHeaders:57 - Start
2024-02-29 19:52:16 DEBUG ProductListener.listenWithHeaders:58 - Listen; Request from : All
2024-02-29 19:52:16 DEBUG ProductListener.listenWithHeaders:59 - Listen; replyTopic : product-req-reply-topic
2024-02-29 19:52:16 DEBUG ProductListener.listenWithHeaders:60 - Listen; replyPartitionId : 2
2024-02-29 19:52:16 DEBUG ProductListener.listenWithHeaders:61 - Listen; receivedTopic : product-req-topic
2024-02-29 19:52:16 DEBUG ProductListener.listenWithHeaders:62 - Listen; receivedPartitionId : 2
2024-02-29 19:52:16 DEBUG ProductListener.listenWithHeaders:63 - Listen; correlationId : �}�у�G���p�`;H�
2024-02-29 19:52:16 DEBUG ProductListener.listenWithHeaders:64 - Listen; offset : 0
2024-02-29 19:52:16 DEBUG ProductListener.listenWithHeaders:70 - Starting to Sleep Seconds : 6
2024-02-29 19:52:22 DEBUG ProductListener.listenWithHeaders:77 - Awakening from Sleep...
2024-02-29 19:52:22 DEBUG ProductListener.listenWithHeaders:79 - Thread[org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1,5,main]
2024-02-29 19:52:22 INFO  ProductListener.listenWithHeaders:80 - Ending...

Configure Product web
---------------------
/ch05/ch05-02/02-ProductWeb/src/main/resources/application.yml
kafka:
  topic:
    product:
      pinnedToPartition: false
product:
  topic:
    request:
      numPartitions: 3

Build Product Web
-----------------
binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-02/02-ProductWeb
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
[INFO] Total time:  1.962 s
[INFO] Finished at: 2024-02-29T17:51:52+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:02-ProductWeb binil$ 

Run Product web 1
-----------------
binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-02/02-ProductWeb
binildass-MacBook-Pro:02-ProductWeb binil$ sh run1.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-02-29 19:51:41 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProductWebMicroserviceApplication v0.0.1-SNAPSHOT using Java 17.0.7 with PID 14583 (/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-02/02-ProductWeb/target/Ecom-Product-Web-Microservice-0.0.1-SNAPSHOT.jar started by binil in /Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-02/02-ProductWeb)
2024-02-29 19:51:41 DEBUG StartupInfoLogger.logStarting:51 - Running with Spring Boot v3.2.0, Spring v6.1.1
2024-02-29 19:51:41 INFO  SpringApplication.logStartupProfileInfo:653 - No active profile set, falling back to 1 default profile: "default"
2024-02-29 19:51:47 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductWebMicroserviceApplication in 8.294 seconds (process running for 10.251)
2024-02-29 19:52:16 INFO  ProductRestController.getAllProducts:69 - Start
2024-02-29 19:52:16 DEBUG ProductRestController.getAllProducts:70 - Application Name : product-web-1
2024-02-29 19:52:16 DEBUG ProductRestController.getAllProducts:71 - PinnedToPartition? : false
2024-02-29 19:52:16 DEBUG ProductRestController.getAllProducts:72 - Thread : Thread[http-nio-8080-exec-1,5,main]
2024-02-29 19:52:16 DEBUG LogAccessor.debug:191 - Using Request partition : null
2024-02-29 19:52:16 DEBUG LogAccessor.debug:191 - Using Request key : null
2024-02-29 19:52:16 DEBUG LogAccessor.debug:191 - Using Reply partition : 2
2024-02-29 19:52:16 DEBUG LogAccessor.debug:313 - Sending: ProducerRecord(topic=product-req-topic, partition=null, headers=RecordHeaders(headers = [RecordHeader(key = kafka_replyTopic, value = [112, 114, 111, 100, 117, 99, 116, 45, 114, 101, 113, 45, 114, 101, 112, 108, 121, 45, 116, 111, 112, 105, 99]), RecordHeader(key = kafka_replyPartition, value = [0, 0, 0, 2]), RecordHeader(key = kafka_correlationId, value = [-58, 125, -42, -47, -125, -19, 71, -100, -103, -76, 112, -113, 96, 59, 72, -36])], isReadOnly = false), key=null, value=All, timestamp=null) with correlationId: c67dd6d1-83ed-479c-99b4-708f603b48dc
2024-02-29 19:52:16 DEBUG ProductRestController.getAllProducts:88 - Starting to Sleep Seconds : 4
2024-02-29 19:52:20 DEBUG ProductRestController.getAllProducts:95 - Awakening from Sleep...
2024-02-29 19:52:20 DEBUG ProductRestController.getAllProducts:97 - Thread[http-nio-8080-exec-1,5,main]
2024-02-29 19:52:20 INFO  ProductRestController.getAllProducts:98 - Ending
2024-02-29 19:52:22 DEBUG LogAccessor.debug:313 - Received: product-req-reply-topic-2@0 with correlationId: c67dd6d1-83ed-479c-99b4-708f603b48dc

Run Product web 2
-----------------
binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-02/02-ProductWeb
binildass-MacBook-Pro:02-ProductWeb binil$ sh run2.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-02-29 19:51:42 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProductWebMicroserviceApplication v0.0.1-SNAPSHOT using Java 17.0.7 with PID 14585 (/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-02/02-ProductWeb/target/Ecom-Product-Web-Microservice-0.0.1-SNAPSHOT.jar started by binil in /Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-02/02-ProductWeb)
2024-02-29 19:51:42 DEBUG StartupInfoLogger.logStarting:51 - Running with Spring Boot v3.2.0, Spring v6.1.1
2024-02-29 19:51:42 INFO  SpringApplication.logStartupProfileInfo:653 - No active profile set, falling back to 1 default profile: "default"
2024-02-29 19:51:48 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductWebMicroserviceApplication in 7.421 seconds (process running for 9.932)
2024-02-29 19:52:16 INFO  ProductRestController.getAllProducts:69 - Start
2024-02-29 19:52:16 DEBUG ProductRestController.getAllProducts:70 - Application Name : product-web-2
2024-02-29 19:52:16 DEBUG ProductRestController.getAllProducts:71 - PinnedToPartition? : false
2024-02-29 19:52:16 DEBUG ProductRestController.getAllProducts:72 - Thread : Thread[http-nio-8081-exec-1,5,main]
2024-02-29 19:52:16 DEBUG LogAccessor.debug:191 - Using Request partition : null
2024-02-29 19:52:16 DEBUG LogAccessor.debug:191 - Using Request key : null
2024-02-29 19:52:16 DEBUG LogAccessor.debug:191 - Using Reply partition : 0
2024-02-29 19:52:16 DEBUG LogAccessor.debug:313 - Sending: ProducerRecord(topic=product-req-topic, partition=null, headers=RecordHeaders(headers = [RecordHeader(key = kafka_replyTopic, value = [112, 114, 111, 100, 117, 99, 116, 45, 114, 101, 113, 45, 114, 101, 112, 108, 121, 45, 116, 111, 112, 105, 99]), RecordHeader(key = kafka_replyPartition, value = [0, 0, 0, 0]), RecordHeader(key = kafka_correlationId, value = [32, 69, -55, 4, -74, -56, 70, 8, -94, 27, 104, 73, 67, -12, -85, 48])], isReadOnly = false), key=null, value=All, timestamp=null) with correlationId: 2045c904-b6c8-4608-a21b-684943f4ab30
2024-02-29 19:52:16 DEBUG ProductRestController.getAllProducts:88 - Starting to Sleep Seconds : 4
2024-02-29 19:52:20 DEBUG ProductRestController.getAllProducts:95 - Awakening from Sleep...
2024-02-29 19:52:20 DEBUG ProductRestController.getAllProducts:97 - Thread[http-nio-8081-exec-1,5,main]
2024-02-29 19:52:20 INFO  ProductRestController.getAllProducts:98 - Ending
2024-02-29 19:52:23 DEBUG LogAccessor.debug:313 - Received: product-req-reply-topic-0@0 with correlationId: 2045c904-b6c8-4608-a21b-684943f4ab30

Run Product web 3
-----------------
binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-02/02-ProductWeb
binildass-MacBook-Pro:02-ProductWeb binil$ sh run3.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-02-29 19:51:44 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProductWebMicroserviceApplication v0.0.1-SNAPSHOT using Java 17.0.7 with PID 14587 (/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-02/02-ProductWeb/target/Ecom-Product-Web-Microservice-0.0.1-SNAPSHOT.jar started by binil in /Users/binil/binil/code/mac/mybooks/docker-04/Code/ch05/ch05-02/02-ProductWeb)
2024-02-29 19:51:44 DEBUG StartupInfoLogger.logStarting:51 - Running with Spring Boot v3.2.0, Spring v6.1.1
2024-02-29 19:51:44 INFO  SpringApplication.logStartupProfileInfo:653 - No active profile set, falling back to 1 default profile: "default"
2024-02-29 19:51:49 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductWebMicroserviceApplication in 6.554 seconds (process running for 9.255)
2024-02-29 19:52:17 INFO  ProductRestController.getAllProducts:69 - Start
2024-02-29 19:52:17 DEBUG ProductRestController.getAllProducts:70 - Application Name : product-web-3
2024-02-29 19:52:17 DEBUG ProductRestController.getAllProducts:71 - PinnedToPartition? : false
2024-02-29 19:52:17 DEBUG ProductRestController.getAllProducts:72 - Thread : Thread[http-nio-8082-exec-1,5,main]
2024-02-29 19:52:17 DEBUG LogAccessor.debug:191 - Using Request partition : null
2024-02-29 19:52:17 DEBUG LogAccessor.debug:191 - Using Request key : null
2024-02-29 19:52:17 DEBUG LogAccessor.debug:191 - Using Reply partition : 1
2024-02-29 19:52:17 DEBUG LogAccessor.debug:313 - Sending: ProducerRecord(topic=product-req-topic, partition=null, headers=RecordHeaders(headers = [RecordHeader(key = kafka_replyTopic, value = [112, 114, 111, 100, 117, 99, 116, 45, 114, 101, 113, 45, 114, 101, 112, 108, 121, 45, 116, 111, 112, 105, 99]), RecordHeader(key = kafka_replyPartition, value = [0, 0, 0, 1]), RecordHeader(key = kafka_correlationId, value = [59, -37, 80, 119, -113, 20, 70, -96, -82, 124, 53, -123, -36, -118, -97, -50])], isReadOnly = false), key=null, value=All, timestamp=null) with correlationId: 3bdb5077-8f14-46a0-ae7c-3585dc8a9fce
2024-02-29 19:52:17 DEBUG ProductRestController.getAllProducts:88 - Starting to Sleep Seconds : 4
2024-02-29 19:52:21 DEBUG ProductRestController.getAllProducts:95 - Awakening from Sleep...
2024-02-29 19:52:21 DEBUG ProductRestController.getAllProducts:97 - Thread[http-nio-8082-exec-1,5,main]
2024-02-29 19:52:21 INFO  ProductRestController.getAllProducts:98 - Ending
2024-02-29 19:52:23 DEBUG LogAccessor.debug:313 - Received: product-req-reply-topic-1@0 with correlationId: 3bdb5077-8f14-46a0-ae7c-3585dc8a9fce

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
binildass-MacBook-Pro:02-ProductWeb binil$ sh curlrun1.sh 
Firing http://127.0.0.1:8080/productsweb and waiting...
------------------------------------------
Current date: Thu Feb 29 19:52:16 IST 2024
==========================================
{"products":[{"productId":"1","name":"Kamsung D3","code":"KAMSUNG-TRIOS","title":"Kamsung Trios 12 inch , black , 12 px ....","price":12000.0},{"productId":"2","name":"Lokia Pomia","code":"LOKIA-POMIA","title":"Lokia 12 inch , white , 14px ....","price":9000.0}]}
------------------------------------------
Current date: Thu Feb 29 19:52:22 IST 2024
==========================================
Response from http://127.0.0.1:8080/productsweb received.
binildass-MacBook-Pro:02-ProductWeb binil$ 

Curl Client 2
-------------
binildass-MacBook-Pro:02-ProductWeb binil$ sh curlrun2.sh 
Firing http://127.0.0.1:8081/productsweb and waiting...
------------------------------------------
Current date: Thu Feb 29 19:52:16 IST 2024
==========================================
{"products":[{"productId":"1","name":"Kamsung D3","code":"KAMSUNG-TRIOS","title":"Kamsung Trios 12 inch , black , 12 px ....","price":12000.0},{"productId":"2","name":"Lokia Pomia","code":"LOKIA-POMIA","title":"Lokia 12 inch , white , 14px ....","price":9000.0}]}
------------------------------------------
Current date: Thu Feb 29 19:52:23 IST 2024
==========================================
Response from http://127.0.0.1:8081/productsweb received.
binildass-MacBook-Pro:02-ProductWeb binil$ 

Curl Client 3
-------------
binildass-MacBook-Pro:02-ProductWeb binil$ sh curlrun3.sh 
Firing http://127.0.0.1:8082/productsweb and waiting...
------------------------------------------
Current date: Thu Feb 29 19:52:17 IST 2024
==========================================
{"products":[{"productId":"1","name":"Kamsung D3","code":"KAMSUNG-TRIOS","title":"Kamsung Trios 12 inch , black , 12 px ....","price":12000.0},{"productId":"2","name":"Lokia Pomia","code":"LOKIA-POMIA","title":"Lokia 12 inch , white , 14px ....","price":9000.0}]}
------------------------------------------
Current date: Thu Feb 29 19:52:23 IST 2024
==========================================
Response from http://127.0.0.1:8082/productsweb received.
binildass-MacBook-Pro:02-ProductWeb binil$ 

You could also test like:
=========================

http://localhost:8080/product.html
http://localhost:8081/product.html
http://localhost:8082/product.html

or Run multiple product webs using Curl
---------------------------------------

binildass-MacBook-Pro:~ binil$ curl http://localhost:8080/productsweb
binildass-MacBook-Pro:~ binil$ curl http://localhost:8081/productsweb
binildass-MacBook-Pro:~ binil$ curl http://localhost:8082/productsweb


