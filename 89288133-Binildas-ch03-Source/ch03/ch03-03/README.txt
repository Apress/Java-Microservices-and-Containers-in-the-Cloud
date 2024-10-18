
Microservices using MongoDB and Rest Template (Full CRUD)
HTML Browser -----+
                  |
                  +---> ProductWeb μS --> ProductServer μS --> MongoDB
                  |
GraphiQL Browser--+

Start MongoDB
-------------
mongod --dbpath /usr/local/var/mongodb --logpath /usr/local/var/log/mongodb/mongo.log

Build the Product Server
------------------------

(base) binildass-MacBook-Pro:01-ProductServer binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch03/ch03-03/01-ProductServer
(base) binildass-MacBook-Pro:01-ProductServer binil$ sh make.sh 
[INFO] Scanning for projects...
[INFO] 
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.879 s
[INFO] Finished at: 2024-01-21T20:43:20+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:01-ProductServer binil$ 


Run the Product Server
-----------------------

binildass-MacBook-Pro:01-ProductServer binil$ sh run.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-01-21 20:44:22 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProductMicroserviceApplication...
...
2024-01-21 20:44:23 INFO  InitializationComponent.init:47 - Start...
2024-01-21 20:44:23 DEBUG InitializationComponent.init:51 - Deleting all existing data ...
2024-01-21 20:44:23 DEBUG InitializationComponent.init:56 - Creating initial data on start...
2024-01-21 20:44:23 INFO  InitializationComponent.init:105 - End
2024-01-21 20:44:24 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductMicro...


Build the Product Web
---------------------

(base) binildass-MacBook-Pro:02-ProductWeb binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/Code/ch03/ch03-03/02-ProductWeb
(base) binildass-MacBook-Pro:02-ProductWeb binil$ sh make.sh 
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
[INFO] Total time:  2.426 s
[INFO] Finished at: 2024-01-21T20:43:56+05:30
[INFO] ------------------------------------------------------------------------
binildass-MacBook-Pro:02-ProductWeb binil$ 

Run the Product Web
-------------------

binildass-MacBook-Pro:02-ProductWeb binil$ sh run.sh 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-01-21 20:46:04 INFO  StartupInfoLogger.logStarting:50 - Starting EcomProductMicro...
...
2024-01-21 20:46:05 INFO  InitializationComponent.init:37 - Start
2024-01-21 20:46:05 DEBUG InitializationComponent.init:39 - Doing Nothing...
2024-01-21 20:46:05 INFO  InitializationComponent.init:41 - End
2024-01-21 20:46:06 INFO  StartupInfoLogger.logStarted:56 - Started EcomProductMicro...


Test using HTML Browser Client
------------------------------

http://localhost:8080/product.html

graphiql
========
http://localhost:8080/graphiql

Request
{products(count: 2, offset: 0) {productId name code}}

Response
{
  "data": {
    "products": [
      {
        "productId": "61a312a79ca6627a610147db",
        "name": "Kamsung Mobile",
        "code": "KAMSUNG-TRIOS"
      },
      {
        "productId": "61a312a79ca6627a610147dc",
        "name": "Lokia Mobile",
        "code": "LOKIA-POMIA"
      }
    ]
  }
}

-------------

Request
{products(count: 2, offset: 0) {productId code productCategory {id name title}}}

Response
{
  "data": {
    "products": [
      {
        "productId": "61a312a79ca6627a610147db",
        "code": "KAMSUNG-TRIOS",
        "productCategory": {
          "id": "61a312a79ca6627a610147d9",
          "name": "Mobile",
          "title": "Mobiles and Tablet"
        }
      },
      {
        "productId": "61a312a79ca6627a610147dc",
        "code": "LOKIA-POMIA",
        "productCategory": {
          "id": "61a312a79ca6627a610147d9",
          "name": "Mobile",
          "title": "Mobiles and Tablet"
        }
      }
    ]
  }
}

--------------

Request
{products(count: 2, offset: 0) {productId code productCategory {id name products {productId}}}}

Response
{
  "data": {
    "products": [
      {
        "productId": "61a312a79ca6627a610147db",
        "code": "KAMSUNG-TRIOS",
        "productCategory": {
          "id": "61a312a79ca6627a610147d9",
          "name": "Mobile",
          "products": [
            {
              "productId": "61a312a79ca6627a610147db"
            },
            {
              "productId": "61a312a79ca6627a610147dc"
            },
            {
              "productId": "61a312a79ca6627a610147dd"
            }
          ]
        }
      },
      {
        "productId": "61a312a79ca6627a610147dc",
        "code": "LOKIA-POMIA",
        "productCategory": {
          "id": "61a312a79ca6627a610147d9",
          "name": "Mobile",
          "products": [
            {
              "productId": "61a312a79ca6627a610147db"
            },
            {
              "productId": "61a312a79ca6627a610147dc"
            },
            {
              "productId": "61a312a79ca6627a610147dd"
            }
          ]
        }
      }
    ]
  }
}

-------------

Request
mutation writeProduct {
  writeProduct(
    name: "EmI Tablet"
    code: "EMI-PAD"
    title: "EmI 14 inch , grey, 150px ...."
    price: 20000
    category: "Tablet"
  ) {
    productId
  }
}

Response
{
  "data": {
    "writeProduct": {
      "productId": "6460f4eef1019127b960740d"
    }
  }
}


Test using cURL
---------------

Test: 1

(base) binildass-MacBook-Pro:~ binil$ curl --request POST 'localhost:8080/graphql' --header 'Content-Type: application/json' --data-raw '{"query":"query {products(count: 2, offset: 0) {productId name code}}"}'

{"data":{"products":[{"productId":"6461017a4757545da0174286","name":"Kamsung Mobile","code":"KAMSUNG-TRIOS"},{"productId":"6461017a4757545da0174287","name":"Lokia Mobile","code":"LOKIA-POMIA"}]}}
(base) binildass-MacBook-Pro:~ binil$

Test: 2

(base) binildass-MacBook-Pro:~ binil$ curl --request POST 'localhost:8080/graphql' --header 'Content-Type: application/json' --data-raw '{"query":"query {products(count: 2, offset: 0) {productId code productCategory {id name title}}}"}'

{"data":{"products":[{"productId":"6461017a4757545da0174286","code":"KAMSUNG-TRIOS","productCategory":{"id":"6461017a4757545da0174284","name":"Mobile","title":"Mobiles and Tablet"}},{"productId":"6461017a4757545da0174287","code":"LOKIA-POMIA","productCategory":{"id":"6461017a4757545da0174284","name":"Mobile","title":"Mobiles and Tablet"}}]}}
(base) binildass-MacBook-Pro:~ binil$

Test: 3

(base) binildass-MacBook-Pro:~ binil$ curl --request POST 'localhost:8080/graphql' --header 'Content-Type: application/json' --data-raw '{"query":"query {products(count: 2, offset: 0) {productId code productCategory {id name products {productId}}}}"}'

{"data":{"products":[{"productId":"6461017a4757545da0174286","code":"KAMSUNG-TRIOS","productCategory":{"id":"6461017a4757545da0174284","name":"Mobile","products":[{"productId":"6461017a4757545da0174286"},{"productId":"6461017a4757545da0174287"},{"productId":"6461017a4757545da0174288"}]}},{"productId":"6461017a4757545da0174287","code":"LOKIA-POMIA","productCategory":{"id":"6461017a4757545da0174284","name":"Mobile","products":[{"productId":"6461017a4757545da0174286"},{"productId":"6461017a4757545da0174287"},{"productId":"6461017a4757545da0174288"}]}}]}}
(base) binildass-MacBook-Pro:~ binil$ 

Test: 4

(base) binildass-MacBook-Pro:~ binil$ curl --request POST 'localhost:8080/graphql' --header 'Content-Type: application/json' --data-raw '{"query": "mutation writeProduct { writeProduct( name: \"EmI Tablet\" code: \"EMI-PAD\" title: \"EmI 14 inch , grey, 150px ....\" price: 20000 category: \"Tablet\" ) { productId }}"}'

{"data":{"writeProduct":{"productId":"6461078b4757545da017428a"}}}
(base) binildass-MacBook-Pro:~ binil$ 
