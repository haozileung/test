#!/usr/bin/env bash
docker build -t test-data-img .

docker run --name mysql -e MYSQL_ROOT_PASSWORD=root -d mysql