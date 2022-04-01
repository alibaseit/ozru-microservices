#FROM openjdk:8u322-jre
FROM openjdk:11-bullseye

COPY ./ozru-user-service/target/ozru-user-service-0.0.1-SNAPSHOT.jar /ozru-microservices/

WORKDIR /ozru-microservices

ENTRYPOINT ["java","-jar","./ozru-user-service-0.0.1-SNAPSHOT.jar"]
