
eval $(minikube docker-env)
mvn -Dmaven.test.skip=true clean
kubectl delete -f product-web-service.yml
kubectl delete -f product-web-deployment.yml
kubectl delete -f product-server-service.yml
kubectl delete -f product-server-deployment.yml
kubectl delete -f mongo-service.yml
kubectl delete -f mongo-deployment.yml 
kubectl delete -f mongo-volume-claim.yml 
kubectl delete -f mongo-volume.yml
kubectl delete -f kafka-svc.yml 
kubectl delete -f kafka-deployment.yml
docker rmi -f ecom/product-web
docker rmi -f ecom/product-server
