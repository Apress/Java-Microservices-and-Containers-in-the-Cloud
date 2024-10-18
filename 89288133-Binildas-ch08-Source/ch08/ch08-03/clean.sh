mvn -Dmaven.test.skip=true clean
docker stop product-web
docker stop product-server
docker rm product-web
docker rm product-server
docker rmi -f ecom/product-web
docker rmi -f ecom/product-server
docker network rm ecom-network