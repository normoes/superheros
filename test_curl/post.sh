#!/bin/bash

curl -X POST -i -H 'content-type:application/json' -d '{"name":"root", "pseudonym": "walter", "publisher":"Linux", "firstAppearance":"2018-04-15", "powers": ["may do it all"], "allies": [{"name":"sudoers"}]}' http://localhost:8080/superheros
