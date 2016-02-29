FROM yamchaleung/jdk8
MAINTAINER yamchaleung <yamchaleung@gmail.com>

ENV CATALINA_HOME=/usr/local/tomcat PATH=${PATH}:/usr/local/tomcat/bin

RUN cd /tmp && \
  wget -q http://172.17.0.1:8000/apache-tomcat.tar.gz && tar -zxvf apache-tomcat.tar.gz && \
  mv /tmp/apache-tomcat-* ${CATALINA_HOME} && \
  rm -rf ${CATALINA_HOME}/webapps/* && \
  rm ${CATALINA_HOME}/bin/*.bat && \
  mkdir ${CATALINA_HOME}/webapps/ROOT && \
  rm -rf /tmp/* /var/cache/apk/*
  
EXPOSE 8080
CMD ["catalina.sh", "run"]