FROM yamchaleung/java
MAINTAINER yamchaleung <yamchaleung@gmail.com>

ENV CATALINA_HOME /usr/local/tomcat

RUN cd /tmp && \
  curl --silent --location --retry 3 --cacert /etc/ssl/certs/ca-certificates.crt "https://archive.apache.org/dist/tomcat/tomcat-8/v8.0.30/bin/apache-tomcat-8.0.30.tar.gz" | gunzip -c - | tar x -C /tmp/ && \
  mv /tmp/apache-tomcat* ${CATALINA_HOME} && \
  rm -rf ${CATALINA_HOME}/webapps/* && \
  rm ${CATALINA_HOME}/bin/*.bat && \
  mkdir ${CATALINA_HOME}/webapps/ROOT && \
  rm -rf /tmp/* /var/cache/apk/*

ENV PATH ${PATH}:${CATALINA_HOME}/bin
  
EXPOSE 8080
CMD ["catalina.sh", "run"]