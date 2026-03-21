FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY flight-booking-common/pom.xml flight-booking-common/pom.xml
COPY geography-service/pom.xml geography-service/pom.xml

COPY flight-booking-common/src flight-booking-common/src
COPY geography-service/src geography-service/src

RUN mvn -pl geography-service -am clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/geography-service/target/geography-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]