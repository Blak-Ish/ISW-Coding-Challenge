# Online Bookstore Application

## Overview

The Online Bookstore is a Java-based web application designed to manage book inventory and facilitate online book sales. It is built using Java 21, Maven 3, and Spring Boot version 3.2.4.

## Features

- **RESTful API**: The application provides a RESTful API for managing books, carts, and orders.
- **OpenAPI (Swagger) Documentation**: API documentation is available at `{BASEURL}/swagger-ui.html` for easy reference.
- **Embedded Database**: The application uses an embeddable H2 database for standalone operation, eliminating external dependencies.
- **H2 Console**: Access the H2 database console at `{BASEURL}/h2-ui`. Username is "sa" with no password required.
- **Spring Security**: Equipped with Spring Security for authentication and authorization.
- **JWT-Based Token**: Uses JWT-based token authentication. Sample token can be obtained under the login endpoint `/login` with test credentials: `{ "username":"test", "password":"test" }`.
- **Architectural Diagram**: An architectural diagram of the application is available in the resource folder.

## Prerequisites

Before running the application, ensure the following prerequisites are met:

- Java Development Kit (JDK) version 21 is installed on your machine.
- Maven 3 is installed for building and managing dependencies.

## Getting Started

1. Clone the repository to your local machine:

   ```shell
   git clone https://github.com/Blak-Ish/ISW-Coding-Challenge.git

2. Navigate to the project directory
   
   ```shell
   cd bookstore

3. Build the application using Maven

   ```shell
   mvn clean package

4. Run the application

   ```shell
   java -jar target/bookstore.jar

## API Documentation

Explore the API endpoints and their usage by accessing the Swagger documentation:

   ```shell
   http://localhost:8080/swagger-ui.html
```

## Database Management

Manage the embedded H2 database using the H2 console:
   ```shell
   http://localhost:8080/h2-ui
```
- **Uername**: Sa
- **Password**: (Leave empty)

## Sample Token Retrieval

Retrieve a sample JWT-based token by sending a POST request to the login endpoint:
   ```shell
   {
    "username":"test",
    "password":"test"
  }
  
  Response :
  
  {
    "expires": 3400000,
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJpZFwiOjEsXCJ1c2VybmFtZVwiOlwidGVzdFwiLFwicGFzc3dvcmRcIjpcIiQyYSQxMCQ0MnkucXhmb2N4UHdtRVRPOFl6enh1WkttU0dBZFZROVdoNHBGa0NEZmRKMVo2NTI2bkozR1wifSIsImV4cCI6MTcxMjg2OTYwMn0.X0wBPC8L41VCw4NzzoY5cOW1Js_53TrnYFs_P5nSFfQ"
}
```

