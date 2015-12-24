FROM alpine
MAINTAINER HaoziLeung <yamchaleung@gmail.com>

ADD target/test /usr/local/tomcat/webapps/ROOT

VOLUME /usr/local/tomcat/logs
VOLUME /usr/local/tomcat/webapps