docker run -d --name test-data test-data-img
docker run -d -p 8080:8080 --name test-app --volumes-from=test-data -e JAVA_OPTS='-server -Xms1024m -Xmx1024m' yamchaleung/tomcat
docker stop test-data