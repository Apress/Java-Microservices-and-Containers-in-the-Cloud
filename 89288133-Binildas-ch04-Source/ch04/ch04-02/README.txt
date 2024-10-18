Browser (Async HTTP) => ProductWeb μS (Sync over Async) => Kafka => ProductServer μS

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


Build the Product server
------------------------
binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch04/ch04-02/01-ProductServer
binildass-MacBook-Pro:01-ProductServer binil$ sh make.sh 
[INFO] Scanning for projects...
[INFO] 
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.010 s
[INFO] Finished at: 2024-02-20T18:55:37+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:01-ProductServer binil$ 

Run the Product server
----------------------
binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch04/ch04-02/01-ProductServer
binildass-MacBook-Pro:01-ProductServer binil$ sh run.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

...
2024-02-20 18:57:48 INFO  InitializationComponent.init:45 - Start
2024-02-20 18:57:48 INFO  InitializationComponent.init:46 - Doing Nothing...
2024-02-20 18:57:48 INFO  InitializationComponent.init:68 - End
2024-02-20 18:57:49 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductServerMicroserviceApplication in 2.749 seconds (process running for 3.528)
2024-02-20 18:57:59 INFO  ProductListener.listenConsumerRecord:50 - Start
2024-02-20 18:57:59 DEBUG ProductListener.lambda$listenConsumerRecord$0:53 - kafka_replyTopic:product-req-reply-topic
2024-02-20 18:57:59 DEBUG ProductListener.lambda$listenConsumerRecord$0:53 - kafka_correlationId::3%
                    :�O:�:ɔ��
2024-02-20 18:57:59 DEBUG ProductListener.listenConsumerRecord:57 - Listen; brand : null
2024-02-20 18:57:59 DEBUG ProductListener.listenConsumerRecord:58 - Listen; request : All
2024-02-20 18:57:59 DEBUG ProductListener.listenConsumerRecord:63 - Starting to Sleep Seconds : 6
2024-02-20 18:58:05 DEBUG ProductListener.listenConsumerRecord:70 - Awakening from Sleep...
2024-02-20 18:58:05 INFO  ProductListener.listenConsumerRecord:71 - Ending...

Build the Product web
---------------------
binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch04/ch04-02/02-ProductWeb
binildass-MacBook-Pro:02-ProductWeb binil$ sh make.sh 
[INFO] Scanning for projects...
[INFO] 
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.050 s
[INFO] Finished at: 2024-02-20T18:55:42+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:02-ProductWeb binil$ 

Run the Product web
-------------------
binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch04/ch04-02/02-ProductWeb
binildass-MacBook-Pro:02-ProductWeb binil$ sh run.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

...
2024-02-20 18:57:52 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductWebMicroserviceApplication in 2.827 seconds (process running for 3.829)
2024-02-20 18:57:59 INFO  ProductRestController.getAllProducts:70 - Start
2024-02-20 18:57:59 DEBUG ProductRestController.getAllProducts:71 - Thread : Thread[http-nio-8080-exec-5,5,main]
2024-02-20 18:57:59 DEBUG ProductRestController.lambda$getAllProducts$0:75 - kafka_replyTopic:[B@2a373dd3
2024-02-20 18:57:59 DEBUG ProductRestController.getAllProducts:78 - Request success -> SendResult [producerRecord=ProducerRecord(topic=product-req-topic, partition=null, headers=RecordHeaders(headers = [RecordHeader(key = kafka_replyTopic, value = [112, 114, 111, 100, 117, 99, 116, 45, 114, 101, 113, 45, 114, 101, 112, 108, 121, 45, 116, 111, 112, 105, 99]), RecordHeader(key = kafka_correlationId, value = [58, 51, 37, 11, 58, -14, 79, 58, -116, 58, -55, -108, -34, -51, 27, 74]), RecordHeader(key = __TypeId__, value = [106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 83, 116, 114, 105, 110, 103])], isReadOnly = true), key=null, value=All, timestamp=null), recordMetadata=product-req-topic-0@1]
2024-02-20 18:57:59 DEBUG ProductRestController.lambda$getAllProducts$1:79 - kafka_replyTopic : product-req-reply-topic
2024-02-20 18:57:59 DEBUG ProductRestController.lambda$getAllProducts$1:79 - kafka_correlationId : :3%
                      :�O:�:ɔ��
2024-02-20 18:57:59 DEBUG ProductRestController.lambda$getAllProducts$1:79 - __TypeId__ : java.lang.String
2024-02-20 18:57:59 DEBUG ProductRestController.lambda$getAllProducts$2:85 - Thread[ForkJoinPool.commonPool-worker-1,5,main]
2024-02-20 18:57:59 DEBUG ProductRestController.getAllProducts:141 - Thread : Thread[http-nio-8080-exec-5,5,main]
2024-02-20 18:57:59 INFO  ProductRestController.getAllProducts:142 - Ending...
2024-02-20 18:58:05 DEBUG ProductRestController.lambda$getAllProducts$2:87 - Thread[ForkJoinPool.commonPool-worker-1,5,main]
2024-02-20 18:58:05 DEBUG ProductRestController.lambda$getAllProducts$3:97 - Starting to Sleep Seconds: 2
2024-02-20 18:58:07 DEBUG ProductRestController.lambda$getAllProducts$3:104 - Awakening from Sleep...
2024-02-20 18:58:07 DEBUG ProductRestController.lambda$getAllProducts$3:105 - Thread[ForkJoinPool.commonPool-worker-2,5,main]

Test the Client
---------------
Open below link in Chrome

http://localhost:8080/product.html

binildass-MacBook-Pro:~ binil$ curl http://localhost:8080/productsweb
[{"productId":"1","name":"Kamsung D3","code":"KAMSUNG-TRIOS","title":"Kamsung Trios 12 inch , black , 12 px ....","price":12000.0},{"productId":"2","name":"Lokia Pomia","code":"LOKIA-POMIA","title":"Lokia 12 inch , white , 14px ....","price":9000.0}]
binildass-MacBook-Pro:~ binil$

