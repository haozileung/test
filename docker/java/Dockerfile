FROM alpine
MAINTAINER yamchaleung <yamchaleung@gmail.com>

ENV JAVA_HOME /usr/local/java

RUN apk add --update curl ca-certificates && \
  cd /tmp && \
  curl -o glibc-2.21-r2.apk "https://circle-artifacts.com/gh/andyshinn/alpine-pkg-glibc/6/artifacts/0/home/ubuntu/alpine-pkg-glibc/packages/x86_64/glibc-2.21-r2.apk" && \
  apk add --allow-untrusted glibc-2.21-r2.apk && \
  curl -o glibc-bin-2.21-r2.apk "https://circle-artifacts.com/gh/andyshinn/alpine-pkg-glibc/6/artifacts/0/home/ubuntu/alpine-pkg-glibc/packages/x86_64/glibc-bin-2.21-r2.apk" && \
  apk add --allow-untrusted glibc-bin-2.21-r2.apk && \
  /usr/glibc/usr/bin/ldconfig /lib /usr/glibc/usr/lib && \
  curl -jksSLH "Cookie: oraclelicense=accept-securebackup-cookie" \
  http://download.oracle.com/otn-pub/java/jdk/8u66-b17/server-jre-8u66-linux-x64.tar.gz | gunzip -c - | tar -xf - && \
  mv jdk1.8.0_66/jre ${JAVA_HOME} && \
  rm ${JAVA_HOME}/bin/jjs && \
  rm ${JAVA_HOME}/bin/keytool && \
  rm ${JAVA_HOME}/bin/orbd && \
  rm ${JAVA_HOME}/bin/pack200 && \
  rm ${JAVA_HOME}/bin/policytool && \
  rm ${JAVA_HOME}/bin/rmid && \
  rm ${JAVA_HOME}/bin/rmiregistry && \
  rm ${JAVA_HOME}/bin/servertool && \
  rm ${JAVA_HOME}/bin/tnameserv && \
  rm ${JAVA_HOME}/bin/unpack200 && \
  rm ${JAVA_HOME}/lib/ext/nashorn.jar && \
  rm ${JAVA_HOME}/lib/jfr.jar && \
  rm -rf ${JAVA_HOME}/lib/jfr && \
  rm -rf ${JAVA_HOME}/lib/oblique-fonts && \
  echo 'hosts: files mdns4_minimal [NOTFOUND=return] dns mdns4' >> /etc/nsswitch.conf && \
  rm -rf /tmp/* /var/cache/apk/*

ENV PATH ${PATH}:${JAVA_HOME}/bin