mvn -Dmaven.test.skip=true clean package
docker build  --build-arg JAR_FILE=02-ProductWeb/target/*.jar -t ecom/product-web .
docker build  --build-arg JAR_FILE=01-ProductServer/target/*.jar -t ecom/product-server .
docker network create ecom-network
docker pull mongo:5.0.25
docker run  -d -it -p 27017:27017 --name mongo  --net=ecom-network -v /Users/binil/dockerbook/ch08-05/mongodata:/data/db  mongo:5.0.25
docker run -d -p 8081:8081  --net=ecom-network --name product-server ecom/product-server
docker run -d -p 8080:8080  --net=ecom-network --name product-web ecom/product-web
