FROM openjdk:8-jdk

COPY . /SmsAlerts
WORKDIR /SmsAlerts

RUN ./gradlew clean test bootRepackage

FROM openjdk:8-jre-alpine

COPY --from=0 /SmsAlerts/build/libs/SmsAlerts-0.0.1-SNAPSHOT.jar /app.jar
COPY docker-entrypoint.sh /
EXPOSE 8080

CMD ["/docker-entrypoint.sh"]

