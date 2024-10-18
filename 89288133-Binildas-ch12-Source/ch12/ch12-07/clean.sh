eval $(minikube docker-env)
helm delete adminer
helm delete product-web
helm delete product-server
helm delete postgres
helm delete ingress-backend
helm delete ingress-controller
mvn -Dmaven.test.skip=true clean
docker rmi -f ecom/product-web
docker rmi -f ecom/product-server
