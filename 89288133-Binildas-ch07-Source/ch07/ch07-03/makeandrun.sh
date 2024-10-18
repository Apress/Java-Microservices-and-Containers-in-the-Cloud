mvn -Dmaven.test.skip=true clean package
docker build  --build-arg JAR_FILE=02-ProductWeb/target/*.jar -t binildas/product-web .
docker push binildas/product-web
sleep 10
docker rmi binildas/product-web
sleep 10
docker run -p 8080:8080  --name product-web binildas/product-web 