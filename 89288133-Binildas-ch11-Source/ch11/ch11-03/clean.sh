eval $(minikube docker-env)
mvn -Dmaven.test.skip=true clean
kubectl delete -f product-web-service.yml
kubectl delete -f product-web-deployment.yml
kubectl delete -f product-server-service.yml
kubectl delete -f product-server-deployment.yml
kubectl delete -f adminer-svc.yaml
kubectl delete -f adminer-deployment.yaml
kubectl delete -f postgres-svc.yml
kubectl delete -f postgres-deployment.yml 
kubectl delete -f postgres-pvc.yml 
kubectl delete -f postgres-config.yml
kubectl delete -f ingress-controller.yaml
docker rmi -f ecom/product-web
docker rmi -f ecom/product-server
