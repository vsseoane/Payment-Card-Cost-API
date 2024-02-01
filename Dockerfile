FROM maven:3.8.5-openjdk-17 AS build

COPY target/paymentCardsCost-0.0.1-SNAPSHOT.jar java-app.jar

ENTRYPOINT ["java", "-jar", "java-app.jar"]
