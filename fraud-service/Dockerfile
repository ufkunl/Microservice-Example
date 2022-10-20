FROM openjdk:17
COPY ./target/fraud-service.jar fraud-service.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","fraud-service.jar"]