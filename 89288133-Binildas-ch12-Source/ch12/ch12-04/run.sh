eval $(minikube docker-env)
kubectl create -f ./k8s/deployment.yml
kubectl create -f ./k8s/service.yml
minikube service springboothelm --url
sleep 3
kubectl get pods
kubectl get services