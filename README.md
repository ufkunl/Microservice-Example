# Microservice Example

![alt text](https://github.com/ufkunl/microservice-example-BE/blob/master/Diagram.png?raw=true)

The project is based on a web service which uses the following technologies:

* Java 17
* Spring Boot
* Postgres SQL
* Maven
* Apache Kafka Queue
* Microservices

#REST APIs

* /customer (POST)
* /customer/complain (POST)
* /fraud/check (POST)
* /fraud/status (POST)

Uses feign client for communication of services

## Customer(8081)
It can add or complain a customer. it can control by mail for customer is fraud when a customer add. it calls frand change service when complain a customer.


uri: http://localhost:8080/customer <br />
method: POST
```request
{
    "firstName":"Ufuk",
    "lastName":"Ünal",
    "email":"ufukx@gmail.com"
}
```
<br /> 

uri: http://localhost:8080/customer/complain <br />
method: POST
```request
{
    "customerId":"7abb1512-7a72-4dc8-baec-faa36dfb890d"
}
```

## Fraud(8082)
it can control for customer is fraud.

uri: http://localhost:8080/fraud/check <br />
method: POST
```request
{
    "email":"ufukx@gmail.com"
}
```

## Notification(8083)
it can listener to kafka queue and sends message when come a message to queue. it sends message when add a customer.

## Api Gateway (8080)
it can route reqests to the services

# Deploy with docker compose

For deploy postgres and kafka without services in docker

* docker-compose -f docker-compose-main.yml up

* you can run services by manual in localhost

For deploy postgres, kafka and services in docker

* docker-compose -f docker-compose-main.yml up

* docker-compose -f docker-compose-services.yml up

