mvn -Dmaven.test.skip=true clean package
java -jar -Dserver.port=8080 ./02-ProductWeb/target/Ecom-Product-Web-Microservice-0.0.1-SNAPSHOT.jar
