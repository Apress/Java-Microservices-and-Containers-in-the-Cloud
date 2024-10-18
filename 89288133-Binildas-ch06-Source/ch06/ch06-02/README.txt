Async over Sync-over-Async - Full CRUD over Kafka, PostgreSQL

Microservices using PostgreSQL and CompletableFutureReplyingKafkaOperations (Full CRUD)
Browser (Async HATEOAS) => ProductWeb μS (Sync over Async) => Kafka => ProductServer μS => PostgreSQL

Bring up PostgreSQL Server
--------------------------
binildass-MacBook-Pro:~ binil$ pg_ctl -D /Library/PostgreSQL/12/data start

You can stop PostgreSQL later:
binildass-MacBook-Pro:~ binil$ pg_ctl -D /Library/PostgreSQL/12/data stop

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
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch06/ch06-02/kafka-request-reply-util
binildass-MacBook-Pro:kafka-request-reply-util binil$ mvn clean install
...

Build Product Server
--------------------
binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch06/ch06-02/01-ProductServer
binildass-MacBook-Pro:01-ProductServer binil$ sh make.sh
...

Build Product Web
-----------------
binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch06/ch06-02/02-ProductWeb
binildass-MacBook-Pro:02-ProductWeb binil$ sh make.sh
...

Run Product server 1
--------------------
binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch06/ch06-02/01-ProductServer
binildass-MacBook-Pro:01-ProductServer binil$ sh run1.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2023-05-16 22:02:45 INFO  StartupInfoLogger.logStarting:51 - Starting EcomProductServerMicroservice...
...
Running Changeset: db/changelog/initial-schema_inventory.xml::product::Binildas
Running Changeset: db/changelog/initial-schema_inventory.xml::addAutoIncrement...
Running Changeset: db/changelog/initial-schema_inventory.xml::insert-product-01::Binildas
Running Changeset: db/changelog/initial-schema_inventory.xml::insert-product-02::Binildas

UPDATE SUMMARY
Run:                          4
Previously run:               0
Filtered out:                 0
-------------------------------
Total change sets:            4

Liquibase: Update has been successful.
2024-03-04 17:52:57 INFO  InitializationComponent.init:42 - Start
2024-03-04 17:52:57 INFO  InitializationComponent.init:70 - End
2024-03-04 17:52:58 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductServerMicroserviceApplication in 4.665 seconds (process running for 5.577)

Run Product web 1
-----------------
binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch06/ch06-02/02-ProductWeb
binildass-MacBook-Pro:02-ProductWeb binil$ sh run1.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-03-04 17:54:06 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductWebMicroserviceApplication in 2.322 seconds (process running for 2.981)
...

Test the Client
---------------
Open below link in Chrome

http://localhost:8080/product.html

Scale Product web
----------------
binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch06/ch06-02/02-ProductWeb
binildass-MacBook-Pro:02-ProductWeb binil$ java -jar target/Ecom-Product-Microservice-0.0.1-SNAPSHOT.jar  -Dserver.port=8081
...

binildass-MacBook-Pro:02-ProductWeb binil$ java -jar target/Ecom-Product-Microservice-0.0.1-SNAPSHOT.jar  -Dserver.port=8082
...

Scale Product Server
-------------------
binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch06/ch06-02/01-ProductServer
binildass-MacBook-Pro:01-ProductServer binil$ java -jar -Dserver.port=8084 -DDB_SERVER=127.0.0.1:5432 -DPOSTGRES_DB=productdb -DPOSTGRES_USER=postgres -DPOSTGRES_PASSWORD=postgre -Dspring.kafka.consumer.group-id=product-server ./target/Ecom-Product-Server-Microservice-0.0.1-SNAPSHOT.jar
...

binildass-MacBook-Pro:01-ProductServer binil$ java -jar -Dserver.port=8085 -DDB_SERVER=127.0.0.1:5432 -DPOSTGRES_DB=productdb -DPOSTGRES_USER=postgres -DPOSTGRES_PASSWORD=postgre -Dspring.kafka.consumer.group-id=product-server ./target/Ecom-Product-Server-Microservice-0.0.1-SNAPSHOT.jar
...
 

Run multiple product webs
----------------------
http://localhost:8080/product.html
http://localhost:8081/product.html
http://localhost:8082/product.html

or Run multiple product webs using Curl
---------------------------------------

binildass-MacBook-Pro:~ binil$ curl http://localhost:8080/productsweb
binildass-MacBook-Pro:~ binil$ curl http://localhost:8081/productsweb
binildass-MacBook-Pro:~ binil$ curl http://localhost:8082/productsweb

or Continuously Run multiple product webs using Curl
----------------------------------------------------

binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch06/ch06-02/02-ProductWeb
binildass-MacBook-Pro:02-ProductWeb binil$ sh loopcurl1.sh
{"_embedded":{"products":[{"productId":"1","name":"Kamsung D3","code":"KAMSUNG-TRIOS","title":"Kamsung Trios 12 inch , black , 12 px ....","price":12000.0,"_links":{"self":{"href":"/products/1"}}},{"productId":"2","name":"Lokia Pomia","code":"LOKIA-POMIA","title":"Lokia 12 inch , white , 14px ....","price":9000.0,"_links":{"self":{"href":"/products/2"}}}]},"_links":{"self":{"href":"/productsweb"},"getAllProducts":{"href":"/productsweb"}}}
------------------------------------------
Current date: Mon Mar  4 17:32:28 IST 2024
==========================================
^C
binildass-MacBook-Pro:02-ProductWeb binil$ 


