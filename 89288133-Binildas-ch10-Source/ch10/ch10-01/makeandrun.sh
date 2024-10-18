mvn -Dmaven.test.skip=true clean package
docker build  --build-arg JAR_FILE=02-ProductWeb/target/*.jar -t ecom/product-web .
docker build  --build-arg JAR_FILE=01-ProductServer/target/*.jar -t ecom/product-server .
# docker network create ecom-network
# docker pull mongo:4.2.24
kubectl create -f mongo-volume.yml
kubectl create -f mongo-volume-claim.yml 
kubectl create -f mongo-deployment.yml 
kubectl create -f mongo-service.yml
kubectl create -f product-server-deployment.yml
kubectl create -f product-server-service.yml
kubectl create -f product-web-deployment.yml
kubectl create -f product-web-service.yml
minikube service product-web --url
sleep 3
kubectl get pods
kubectl get services