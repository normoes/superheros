#!/bin/bash

curl -s -X POST  \
    -u user:superSecretPasswd1 \
    -H 'content-type:application/json' \
    -d '{"name":"root", "pseudonym": "walter", "publisher":"Linux", "firstAppearance":"2018-04-15", "powers": ["may do it all"], "allies": ["sudoer"]}' \
    http://ec2-18-222-85-217.us-east-2.compute.amazonaws.com:8080/superheros | python -m json.tool

curl -s -X POST  \
    -u user:superSecretPasswd1 \
    -H 'content-type:application/json' \
    -d '{"name":"sudoer", "pseudonym": "terwal", "publisher":"Ubuntu", "firstAppearance":"2018-04-15", "powers": ["may do it all - almost"]}' \
    http://ec2-18-222-85-217.us-east-2.compute.amazonaws.com:8080/superheros | python -m json.tool

curl -s -X POST  \
    -u user:superSecretPasswd1 \
    -H 'content-type:application/json' \
    -d '{"name":"batman", "pseudonym": "bruce wayne", "publisher":"DC Comics", "firstAppearance":"1939-03-01", "powers": ["has a nice car"], "allies": ["robin"]}' \
    http://ec2-18-222-85-217.us-east-2.compute.amazonaws.com:8080/superheros | python -m json.tool

curl -s -X POST  \
    -u user:superSecretPasswd1 \
    -H 'content-type:application/json' \
    -d '{"name":"robin", "pseudonym": "dick grayson", "publisher":"time warner", "firstAppearance":"1940-01-01", "powers": ["batman talks to him"]}' \
    http://ec2-18-222-85-217.us-east-2.compute.amazonaws.com:8080/superheros | python -m json.tool
