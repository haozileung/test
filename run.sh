docker run -d --name test-data test-data
docker run -d -p 8080:8080 --name test-app --volumes-from=test-data -e JAVA_OPTS='-server -Xms1024m -Xmx1024m' tomcat:8.0.30-jre8
docker stop test-data