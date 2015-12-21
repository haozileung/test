FROM tomcat:8.0.30-jre8
MAINTAINER HaoziLeung <yamchaleung@gmail.com>

RUN rm -rf /usr/local/tomcat/webapps/*
ADD target/test /usr/local/tomcat/webapps/ROOT
ADD conf/tomcat/server.xml /usr/local/tomcat/conf/server.xml

VOLUME /usr/local/tomcat/logs
VOLUME /usr/local/tomcat/webapps