# Microservice Project

## Overview
This project is a microservice-based architecture application utilizing Eureka Server, JUnit Test, Mockito, and Docker. The project follows the Model-Controller-Service pattern to ensure separation of concerns and enhance maintainability.
<br>
<br>
***The app doesn't have a front-end so use Postman to run the api***

## Technologies Used
- **Eureka Server**: Service discovery for microservices.
- **JUnit Test**: Unit testing framework.
- **Mockito**: Mocking framework for unit tests.
- **Docker**: Containerization platform.
- **MySQL Server**: Relational database management system.
- **MySQL Workbench**: GUI tool for MySQL.

## Requirements
To run this project, ensure you have the following tools installed on your development environment:
1. **IntelliJ IDEA Community Edition**: [Download](https://www.jetbrains.com/idea/download/)
2. **Postman Desktop**: [Download](https://www.postman.com/downloads/)
3. **MySQL Server and MySQL Workbench**: [Download](https://dev.mysql.com/downloads/)

## Project Setup and Execution

### Prerequisites
1. Install **IntelliJ IDEA Community Edition**.
2. Install **Postman Desktop**.
3. Install **MySQL Server** and **MySQL Workbench**.

### Running the Project
1. **Start the Service Registry (Eureka Server)**
   - Navigate to the `service-registry` directory.
   - Run the Eureka Server application using IntelliJ IDEA.

2. **Start the Config Server and API Gateway**
   - Navigate to the `config-server` directory and run the application.
   - Navigate to the `api-gateway` directory and run the application.

3. **Start Other Microservices**
   - Navigate to each microservice directory and run the respective applications. Ensure the services are registered with Eureka Server.

### Testing the APIs
- Use **Postman Desktop** to test the API endpoints.
- Configure and send requests to the various endpoints provided by the microservices.
- Verify the responses to ensure the services are functioning as expected.

## Project Structure
The project follows the Model-Controller-Service (MCS) pattern. Below is an overview of the structure:

### Model
- Represents the data structure and database interactions.
- Contains entities, repositories, and database configuration files.

### Controller
- Handles incoming HTTP requests and sends responses.
- Contains RESTful API endpoints and request mappings.

### Service
- Contains business logic and service layer code.
- Interfaces with the Model layer and performs necessary operations.

## Testing
- Unit tests are written using **JUnit** and **Mockito**.
- Each service has corresponding test cases to validate the functionality.
- Run tests using the built-in testing tools in IntelliJ IDEA.

## Docker Integration
- Dockerfiles are provided for containerization of each service.
- Use Docker Compose to manage and run multiple containers.
- Ensure Docker is installed and running on your system.

### Building and Running with Docker
1. Navigate to the project root directory.
2. Build Docker images for each service using the provided Dockerfiles.
3. Use Docker Compose to start all services together.
