#!/bin/sh
if ! [ -d ./classes/org/jsoup/ ]; then
    jar xvf ./lib/jsoup-1.17.2.jar && rm -rf ./lib/META_INF/
    mv ./lib/org/ ./classes/
fi
javac -d ./classes/ -cp "./lib/jsoup-1.17.2.jar" ./src/*.java

if [ "$1" == "run" ]; then
    cd ./classes/ && java Application
fi

