#!/bin/bash

curl -s -u user:superSecretPasswd1 \
    http://18.222.85.217:8080/superheros | python -m json.tool
