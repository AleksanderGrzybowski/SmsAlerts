#! /bin/sh
export SMSALERTS_APIPASSWORD=$(cat /run/secrets/SMSALERTS_APIPASSWORD)
export SMSALERTS_RECIPIENT=$(cat /run/secrets/SMSALERTS_RECIPIENT)

java -Xmx60m -jar /app.jar
