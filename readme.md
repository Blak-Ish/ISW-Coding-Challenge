Online Bookstore Application
Overview
The Online Bookstore is a Java-based web application designed to manage book inventory and facilitate online book sales. It is built using Java 21, Maven 3, and Spring Boot version 3.2.4.

Features
RESTful API: The application provides a RESTful API for managing books, carts, and orders.
OpenAPI (Swagger) Documentation: API documentation is available at {BASEURL}/swagger-ui.html for easy reference.
Embedded Database: The application uses an embeddable H2 database for standalone operation, eliminating external dependencies.
H2 Console: Access the H2 database console at {BASEURL}/h2-ui. Username is "sa" with no password required.
Prerequisites
Before running the application, ensure the following prerequisites are met:

Java Development Kit (JDK) version 21 is installed on your machine.
Maven 3 is installed for building and managing dependencies.
Getting Started
Clone the repository to your local machine:

shell
Copy code
git clone https://github.com/your-repository.git
Navigate to the project directory:

shell
Copy code
cd online-bookstore
Build the application using Maven:

shell
Copy code
mvn clean package
Run the application:

shell
Copy code
java -jar target/online-bookstore.jar
Access the application in your web browser:

arduino
Copy code
http://localhost:8080/
API Documentation
Explore the API endpoints and their usage by accessing the Swagger documentation:

bash
Copy code
{BASEURL}/swagger-ui.html
Database Management
Manage the embedded H2 database using the H2 console:

bash
Copy code
{BASEURL}/h2-ui
Username: sa
Password: (leave empty)
Contributing
Contributions to the Online Bookstore application are welcome! Feel free to fork the repository and submit pull requests with your enhancements or bug fixes.

License
This project is licensed under the MIT License.