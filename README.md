**Architectural Choices:**

Framework: 

Utilized Spring Boot framework for building the standalone Java application. Spring Boot simplifies application development by providing default configurations and reducing setup overhead.

Database: 
Employed PostgreSQL as the database management system for persisting recipe data. PostgreSQL offers reliability, scalability, and support for complex queries, making it suitable for data storage in production environments.

Data Access Layer: 
Implemented data access using Spring Data JPA. Spring Data JPA provides repositories and CRUD operations, abstracting away boilerplate code for interacting with the database.

API Documentation: 
Integrated Swagger for documenting the REST API endpoints. Swagger generates interactive API documentation from annotations in the code, making it easier for users to understand and test the API functionalities.

Testing:
Included both unit tests and integration tests for ensuring the reliability and correctness of the application. JUnit 4 framework was used for writing unit tests, and integration tests were performed using an embedded H2 database.



**How to Run the Application:**

Clone Repository: 
https://github.com/shahawar-cyber/recipe_management_system.git

Database Setup: 
PostgreSQL installed and running locally 
CREATE DATABASE recipe_management_system;
psql -U your_username -d recipe_management_system
CREATE SCHEMA recipe_management_system;

Build and Run: 
Use Maven to build the application. 
Access API Documentation: Once the application is running, access the Swagger API documentation at http://localhost:8080/swagger-ui/index.html. Here, you can explore and test the available API endpoints.




