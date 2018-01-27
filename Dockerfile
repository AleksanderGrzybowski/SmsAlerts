FROM openjdk:8-jdk

COPY . /SmsAlerts
WORKDIR /SmsAlerts

RUN ./gradlew clean test bootRepackage

FROM openjdk:8-jre

COPY --from=0 /SmsAlerts/build/libs/SmsAlerts-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8080

CMD ["java", "-Xmx40m", "-jar", "/app.jar"]

