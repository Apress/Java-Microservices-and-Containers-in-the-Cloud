mvn -Dmaven.test.skip=true clean package
docker build  --build-arg JAR_FILE=01-ProductServer/target/*.jar -t ecom/product-server .
docker build  --build-arg JAR_FILE=02-ProductWeb/target/*.jar -t ecom/product-web .
docker network create ecom-network
docker run -d --net=ecom-network --name postgres-docker -e POSTGRES_DB=productdb -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgre -p 5432:5432 -d postgres:15.3-alpine3.18
docker run -d -p 8081:8081 --net=ecom-network --name product-server -e DB_SERVER=postgres-docker:5432 -e POSTGRES_DB=productdb -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgre ecom/product-server
docker run -d -p 8080:8080 --net=ecom-network --name product-web -e acme.PRODUCT_SERVICE_URL=http://product-server:8081/products ecom/product-web


