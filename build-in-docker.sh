#!/usr/bin/env bash

docker run --rm -v `pwd`:/SmsAlerts -u `stat -c "%u:%g" build.gradle` java:8-jdk /bin/bash -c "cd /SmsAlerts && ./gradlew clean test bootRepackage"
