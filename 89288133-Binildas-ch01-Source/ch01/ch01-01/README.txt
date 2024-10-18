Sample using Docker Repository
Browser App => ProductWeb μS ==> In-Memory-Data

Build Product Web
-----------------
(base) binildass-MacBook-Pro:ch01-01 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch01/ch01-01
binildass-MacBook-Pro:ch01-01 binil$ sh makeandrun.sh 
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO] 
[INFO] Ecom-Product-Web-Microservice                                      [jar]
[INFO] Ecom                                                               [pom]
[INFO] 
[INFO] --------< com.acme.ecom.product:Ecom-Product-Web-Microservice >---------
[INFO] Building Ecom-Product-Web-Microservice 0.0.1-SNAPSHOT              [1/2]
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
...
[INFO] 
[INFO] Ecom-Product-Web-Microservice ...................... SUCCESS [  1.705 s]
[INFO] Ecom ............................................... SUCCESS [  0.019 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.902 s
[INFO] Finished at: 2023-12-13T11:32:38+05:30
[INFO] ------------------------------------------------------------------------

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2023-12-13 11:32:39 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProductMicroservice...
2023-12-13 11:32:39 DEBUG StartupInfoLogger.logStarting:51 - Running with Spring Boot v3.2.0, Spring v6.1.1
2023-12-13 11:32:39 INFO  SpringApplication.logStartupProfileInfo:653 - No active ...
2023-12-13 11:32:40 INFO  InMemoryDB.initDB:71 - Start
2023-12-13 11:32:40 INFO  InMemoryDB.initDB:92 - Ending.
2023-12-13 11:32:40 INFO  InitializationComponent.init:35 - Start
2023-12-13 11:32:40 DEBUG InitializationComponent.init:37 - Doing Nothing...
2023-12-13 11:32:40 INFO  InitializationComponent.init:39 - End
2023-12-13 11:32:40 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductMicroservice...
2023-12-13 11:32:46 INFO  ProductRestController.getAllProducts:64 - Start
2023-12-13 11:32:46 DEBUG ProductRestController.lambda$getAllProducts$0:74 - Product [productId=1, ...
2023-12-13 11:32:46 DEBUG ProductRestController.lambda$getAllProducts$0:74 - Product [productId=2, ...
2023-12-13 11:32:46 INFO  ProductRestController.getAllProducts:75 - Ending


Test the Client Using Browser App
---------------------------------

http://localhost:8080/product.html

Test the Client Using cURL
--------------------------

List All Products:
------------------
(base) binildass-MacBook-Pro:ch01-01 binil$ curl http://localhost:8080/productsweb
[{"productId":"1","name":"Kamsung D3","code":"KAMSUNG-TRIOS","title":"Kamsung Trios 12 inch , black , 12 px ....","price":12000.0},{"productId":"2","name":"Lokia Pomia","code":"LOKIA-POMIA","title":"Lokia 12 inch , white , 14px ....","price":9000.0}]
(base) binildass-MacBook-Pro:ch01-01 binil$ 

Add a New Product
-----------------
(base) binildass-MacBook-Pro:ch01-01 binil$ curl -i -X POST -H "Content-Type:application/json" -d '{"productId":"3","name":"Mapple J-Phone","code":"MAPPLE-J-PHONE","title":"Mapple Smart Phone, black , 20 px ....","price":30000.0}' http://localhost:8080/productsweb
HTTP/1.1 200 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 09 May 2023 04:13:17 GMT

{"productId":"3","name":"Mapple J-Phone","code":"MAPPLE-J-PHONE","title":"Mapple Smart Phone, black , 20 px ....","price":30000.0}
(base) binildass-MacBook-Pro:ch01-01 binil$

List All Products:
------------------
(base) binildass-MacBook-Pro:ch01-01 binil$ curl http://localhost:8080/productsweb
[{"productId":"1","name":"Kamsung D3","code":"KAMSUNG-TRIOS","title":"Kamsung Trios 12 inch , black , 12 px ....","price":12000.0},{"productId":"2","name":"Lokia Pomia","code":"LOKIA-POMIA","title":"Lokia 12 inch , white , 14px ....","price":9000.0},{"productId":"3","name":"Mapple J-Phone","code":"MAPPLE-J-PHONE","title":"Mapple Smart Phone, black , 20 px ....","price":30000.0}]
(base) binildass-MacBook-Pro:ch01-01 binil$

Replace A Product
-----------------

(base) binildass-MacBook-Pro:ch01-01 binil$ curl -i -X PUT -H "Content-Type:application/json" -d '{"productId":"3", "name":"Giomi-New", "code":"GIOME-KL-NEW", "title":"Giome New 10 inch gold", "price":33000.0}' http://localhost:8080/productsweb/3
HTTP/1.1 200 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 09 May 2023 05:41:55 GMT

{"productId":"3","name":"Giomi-New","code":"GIOME-KL-NEW","title":"Giome New 10 inch gold","price":33000.0}
(base) binildass-MacBook-Pro:ch01-01 binil$ 

List All Products:
------------------
(base) binildass-MacBook-Pro:ch01-01 binil$ curl http://localhost:8080/productsweb
[{"productId":"1","name":"Kamsung D3","code":"KAMSUNG-TRIOS","title":"Kamsung Trios 12 inch , black , 12 px ....","price":12000.0},{"productId":"2","name":"Lokia Pomia","code":"LOKIA-POMIA","title":"Lokia 12 inch , white , 14px ....","price":9000.0},{"productId":"3","name":"Giomi-New","code":"GIOME-KL-NEW","title":"Giome New 10 inch gold","price":33000.0}]
(base) binildass-MacBook-Pro:ch01-01 binil$ 

Modify A Product
----------------

(base) binildass-MacBook-Pro:ch01-01 binil$ curl -i -X PATCH -H "Content-Type:application/json" -d '{"productId":"3", "price":33300.0}' http://localhost:8080/productsweb/3
HTTP/1.1 200 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 09 May 2023 11:08:41 GMT

{"productId":"3","name":"Giomi-New","code":"GIOME-KL-NEW","title":"Giome New 10 inch gold","price":33300.0}
(base) binildass-MacBook-Pro:ch01-01 binil$

List the modified Product:
--------------------------
(base) binildass-MacBook-Pro:ch01-01 binil$ curl http://localhost:8080/productsweb/3
{"productId":"3","name":"Giomi-New","code":"GIOME-KL-NEW","title":"Giome New 10 inch gold","price":33300.0}
(base) binildass-MacBook-Pro:ch01-01 binil$

Modify more fields of the Product
--------------------------------

(base) binildass-MacBook-Pro:ch01-01 binil$ curl -i -X PATCH -H "Content-Type:application/json" -d '{"productId":"3", "name":"Giomi-Newest", "price":33330.0}' http://localhost:8080/productsweb/3
HTTP/1.1 200 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 09 May 2023 11:12:05 GMT

{"productId":"3","name":"Giomi-Newest","code":"GIOME-KL-NEW","title":"Giome New 10 inch gold","price":33330.0}
(base) binildass-MacBook-Pro:ch01-01 binil$

List the modified Product:
--------------------------
(base) binildass-MacBook-Pro:ch01-01 binil$ curl http://localhost:8080/productsweb/3
{"productId":"3","name":"Giomi-Newest","code":"GIOME-KL-NEW","title":"Giome New 10 inch gold","price":33330.0}
(base) binildass-MacBook-Pro:ch01-01 binil$

Delete the newly added Product:
------------------------------

(base) binildass-MacBook-Pro:ch01-01 binil$ curl -i -X DELETE http://localhost:8080/productsweb/3
HTTP/1.1 204 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Date: Tue, 09 May 2023 11:14:05 GMT

(base) binildass-MacBook-Pro:ch01-01 binil$ 

List All Products:
------------------
(base) binildass-MacBook-Pro:ch01-01 binil$ curl http://localhost:8080/productsweb
[{"productId":"1","name":"Kamsung D3","code":"KAMSUNG-TRIOS","title":"Kamsung Trios 12 inch , black , 12 px ....","price":12000.0},{"productId":"2","name":"Lokia Pomia","code":"LOKIA-POMIA","title":"Lokia 12 inch , white , 14px ....","price":9000.0}]
(base) binildass-MacBook-Pro:ch01-01 binil$ 
