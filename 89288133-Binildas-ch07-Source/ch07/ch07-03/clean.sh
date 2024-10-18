cd ./01-ProductServer
mvn -Dmaven.test.skip=true clean
cd ../02-ProductWeb
mvn -Dmaven.test.skip=true clean
cd ..
docker stop product-web
docker stop product-server
docker stop mongo
docker rm product-web
docker rm product-server
docker rm mongo
docker rmi -f ecom/product-web
docker rmi -f ecom/product-server