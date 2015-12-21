FROM mhart/alpine-node

ADD src /usr/local/node

RUN apk add --update make gcc g++ python && \
    cd /usr/local/node && npm install && \
    apk del make gcc g++ python && \
    rm -rf /tmp/* /var/cache/apk/* /root/.npm /root/.node-gyp

EXPOSE 3000
CMD ["node", "index.js"]