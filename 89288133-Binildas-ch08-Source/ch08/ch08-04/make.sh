cd ./01-ProductServer
mvn -Dmaven.test.skip=true clean package
cd ../02-ProductWeb
mvn -Dmaven.test.skip=true clean package
cd ..
