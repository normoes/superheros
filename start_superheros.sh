#!/bin/bash

set -euo pipefail
echo "removing running/existing superheros container"
container=$(docker container ls -a -q --filter name=payworks-superheros$)
if [ ! -z "$container" ]; then
  docker rm -f "$container"
fi
unset container

echo "creating superheros container"
docker run -d \
    --log-driver=json-file --log-opt max-size=10m --log-opt max-file=3 \
    --net host \
    --name payworks-superheros \
    --restart unless-stopped \
    payworks/superheros
