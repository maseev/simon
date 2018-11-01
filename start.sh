#!/usr/bin/env bash

docker rm -f -v simon

docker run -d -p 8080:80 --name simon simon