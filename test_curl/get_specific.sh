#!/bin/bash

# case -insensitive names
curl -s -u user:superSecretPasswd1 \
    http://18.222.85.217:8080/superheros/roOT | python -m json.tool
