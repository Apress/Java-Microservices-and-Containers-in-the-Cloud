Async over Sync-over-Async - Full CRUD over Kafka, MongoDB

Microservices using MongoDB and CompletableFutureReplyingKafkaOperations (Full CRUD)
Browser (Async HATEOAS) => ProductWeb μS (Sync over Async) => Kafka => ProductServer μS => MongoDB

Start MongoDB
-------------
mongod --dbpath /usr/local/var/mongodb --logpath /usr/local/var/log/mongodb/mongo.log

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

 
Build & Run Install dependency
------------------------------

binildass-MacBook-Pro:kafka-request-reply-util binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch06/ch06-01/kafka-request-reply-util
(base) binildass-MacBook-Pro:kafka-request-reply-util binil$ mvn clean install
[INFO] Scanning for projects...
[INFO]
...

Build Product Server
--------------------
binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch06/ch06-01/01-ProductServer
binildass-MacBook-Pro:01-ProductServer binil$ sh make.sh
...
 
Build Product Web
-----------------
binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch06/ch06-01/02-ProductWeb
binildass-MacBook-Pro:02-ProductWeb binil$ sh make.sh
...

Run Product server 1
--------------------
(base) binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch06/ch06-01/01-ProductServer
(base) binildass-MacBook-Pro:01-ProductServer binil$ sh run1.sh 
...

Run Product web 1
-----------------
(base) binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch06/ch06-01/02-ProductWeb
(base) binildass-MacBook-Pro:02-ProductWeb binil$ sh run1.sh 
...

Test the Client
---------------
Open below link in Chrome

http://localhost:8080/product.html

 
Scale Product Server
-------------------
(base) binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch06/ch06-01/01-ProductServer
(base) binildass-MacBook-Pro:01-ProductServer binil$ java -jar target/Ecom-Product-Microservice-0.0.1-SNAPSHOT.jar -Dserver.port=8084
...

(base) binildass-MacBook-Pro:01-ProductServer binil$ java -jar target/Ecom-Product-Microservice-0.0.1-SNAPSHOT.jar -Dserver.port=8085
...

Scale Product web
----------------
(base) binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch06/ch06-01/02-ProductWeb
(base) binildass-MacBook-Pro:02-ProductWeb binil$ java -jar target/Ecom-Product-Microservice-0.0.1-SNAPSHOT.jar  -Dserver.port=8081
...

(base) binildass-MacBook-Pro:02-ProductWeb binil$ java -jar target/Ecom-Product-Microservice-0.0.1-SNAPSHOT.jar  -Dserver.port=8082
...

Run multiple product webs
----------------------
http://localhost:8080/product.html
http://localhost:8081/product.html
http://localhost:8082/product.html

or Run multiple product webs using Curl
---------------------------------------
(base) binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch06/ch06-01/02-ProductWeb
(base) binildass-MacBook-Pro:02-ProductWeb binil$ curl http://localhost:8080/productsweb
...

(base) binildass-MacBook-Pro:02-ProductWeb binil$ curl http://localhost:8081/productsweb
...

(base) binildass-MacBook-Pro:02-ProductWeb binil$ curl http://localhost:8082/productsweb
...

or Continuously Run multiple product webs using Curl
----------------------------------------------------
(base) binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch06/ch06-01/02-ProductWeb
(base) binildass-MacBook-Pro:02-ProductWeb binil$ sh loopcurl1.sh
{"_embedded":{"products":[{"productId":"1","name":"Kamsung D3","code":"KAMSUNG-TRIOS","title":"Kamsung Trios 12 inch , black , 12 px ....","price":12000.0,"_links":{"self":{"href":"/products/1"}}},{"productId":"2","name":"Lokia Pomia","code":"LOKIA-POMIA","title":"Lokia 12 inch , white , 14px ....","price":9000.0,"_links":{"self":{"href":"/products/2"}}}]},"_links":{"self":{"href":"/productsweb"},"getAllProducts":{"href":"/productsweb"}}}
------------------------------------------
Current date: Mon Dec 13 13:37:04 IST 2021
==========================================
...

(base) binildass-MacBook-Pro:02-ProductWeb binil$ sh loopcurl2.sh
...

(base) binildass-MacBook-Pro:02-ProductWeb binil$ sh loopcurl3.sh
...

