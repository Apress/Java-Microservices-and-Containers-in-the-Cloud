eval $(minikube docker-env)
mvn -Dmaven.test.skip=true clean
kubectl delete -f ./k8s/service.yml
kubectl delete -f ./k8s/deployment.yml
