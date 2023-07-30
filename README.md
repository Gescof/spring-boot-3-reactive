# spring-boot-3-reactive

## Description
spring-boot-3-reactive is a reactive Spring Boot 3 project built with Java 17. It utilizes WebFlux to provide asynchronous and non-blocking handling of requests. The project also integrates Docker Compose to set up a MariaDB database for persistent storage and uses Flyway for managing database migrations. Additionally, MapStruct is employed to simplify the mapping between Data Transfer Objects (DTOs) and Data Access Objects (DAOs).

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Features](#features)
- [License](#license)

## Installation
Follow these steps to set up the project locally:

### Prerequisites
- Java 17: Install Java 17 on your system. You can find the installation instructions [here](https://openjdk.java.net/projects/jdk/17/).
- Maven: Install Maven for dependency management and building. Instructions can be found [here](https://maven.apache.org/install.html).
- Docker Compose: Set up Docker Compose to run the MariaDB database. Refer to the official documentation for [installation instructions](https://docs.docker.com/compose/install/).

### Steps
1. Clone the repository:
`git clone https://github.com/Gescof/spring-boot-3-reactive.git`

2. Navigate to the project directory:
`cd spring-boot-3-reactive`

3. Build the project:
`mvn clean package`

4. Set up the MariaDB database using Docker Compose:
`docker-compose up -d`

5. Apply initial database schema using Flyway:
`mvn flyway:migrate`

6. Run the Spring Boot application:
`mvn spring-boot:run`

7. The application will be accessible at: 
`http://localhost:8080`

## Usage
The "spring-boot-3-reactive" project provides a RESTful API that follows reactive principles using Spring WebFlux. The API offers basic CRUD (Create, Read, Update, Delete) operations for managing data.
The API is designed to be reactive, allowing for asynchronous and non-blocking handling of requests. This ensures efficient resource utilization and scalability, making it suitable for high-concurrency scenarios.

Please note that this usage guide provides a basic overview of the API's CRUD operations. For more detailed information about API endpoints, request and response formats, and any additional functionalities, refer to the API documentation or explore the codebase.

## Features
- Spring Boot 3 with Java 17
- WebFlux for reactive programming
- Maven for dependency management and building
- Docker Compose to set up a MariaDB database
- Flyway for managing database migrations and initial schema setup
- MapStruct for easing mapping between DTOs and DAOs

## License
MIT License
