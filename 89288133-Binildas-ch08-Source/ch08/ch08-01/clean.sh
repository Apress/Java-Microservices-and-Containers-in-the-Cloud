mvn -Dmaven.test.skip=true clean
docker stop product-web
docker stop product-server
docker stop mongo
docker rm product-web
docker rm product-server
docker rm mongo
docker rmi -f ecom/product-web
docker rmi -f ecom/product-server