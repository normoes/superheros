#!/bin/bash

# case -insensitive names
curl -s -u user:superSecretPasswd1 \
    http://ec2-18-222-85-217.us-east-2.compute.amazonaws.com:8080/superheros/roOT | python -m json.tool
