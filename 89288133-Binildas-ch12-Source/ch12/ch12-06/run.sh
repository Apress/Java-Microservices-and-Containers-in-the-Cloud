mvn -Dmaven.test.skip=true clean package
eval $(minikube docker-env)
docker build  --build-arg JAR_FILE=02-ProductWeb/target/*.jar -t ecom/product-web .
docker build  --build-arg JAR_FILE=01-ProductServer/target/*.jar -t ecom/product-server .
helm install -f acme-postgres.yaml postgres ./postgres
helm install -f adminer.yaml adminer ./app
helm install -f acme-product-server.yaml product-server ./app
helm install -f acme-product-web.yaml product-web ./app
helm install -f ingress.yaml ingress ./ingress
minikube service product-web --url
sleep 3
helm list
kubectl get deployments