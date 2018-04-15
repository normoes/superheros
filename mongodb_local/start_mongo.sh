#!/bin/bash

set -euo pipefail
echo "removing running/existing mongo container"
# do not exit with error code if no container should be available
container=$(docker container ls -a -q --filter name=payworks-db$)
if [ ! -z "$container" ]; then
  docker rm -f "$container"
fi
unset container

store=$(docker volume ls -q --filter name=^superheros-store$)
if [ -z "$store" ]; then
  echo "create volume $store"
  docker volume create "$store"
fi
unset store

echo "creating mongo container"
docker run -d \
    --log-driver=json-file --log-opt max-size=10m --log-opt max-file=3 \
    --net host \
    -e HOME="/data" \
    -v superheros-store:/data/db \
    --name payworks-db \
    --restart unless-stopped \
    mongo:3.4
