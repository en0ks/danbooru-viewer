#!/bin/sh
if ! [ -d ./classes/org/jsoup/ ]; then
    jar xvf ./lib/jsoup-1.17.2.jar && mv ./lib/org/jsoup/ ./classes/org/
fi

if ! [ -d ./classes/org/json/ ]; then
    jar xvf ./lib/org.json-chargebee-1.0.jar && mv ./lib/org/json ./classes/org/
fi
cd ./lib/ && find . -type f ! -name '*.jar' -delete

javac -d ./classes/ -cp "./lib/jsoup-1.17.2.jar" ./src/*.java

if [ "$1" == "run" ]; then
    cd ./classes/ && java Application
fi
