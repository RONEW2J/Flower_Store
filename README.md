# 🌺 Flower Store E-commerce Application

This project is a full-featured e-commerce platform designed for a flower store, built with Java and Spring Boot. It offers both RESTful and GraphQL APIs to manage flowers, orders, and users, along with a robust security framework, advanced data persistence, and modern logging. Future plans include integrating asynchronous messaging via RabbitMQ or Kafka and enhancing logging with the ELK stack.

## ✨ Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Setup and Running the Application](#setup-and-running-the-application)
- [Future Enhancements](#future-enhancements)
- [License](#license)

## 🌟 Features

- **Flower Management**: Complete CRUD operations for flower catalog management.
- **Order Processing**: Seamless creation and management of customer orders.
- **User & Security**:
  - JWT-based authentication for secure, stateless sessions.
  - OAuth2 login for third-party authentication integration.
  - Role-based access control using Spring Security.
- **GraphQL API**: Flexible query capabilities in addition to REST endpoints.
- **Logging**: Structured JSON logging configured for future ELK stack integration.
- **Asynchronous Messaging (Planned)**: Future support for RabbitMQ or Kafka to enable event-driven communication.
- **Data Persistence**: Leveraging Spring Data JPA and Hibernate for efficient ORM.

## 🛠️ Technology Stack

### Core Frameworks
- **Java**: The main programming language for the application.
- **Spring Boot**: Enables rapid development and configuration.
- **Spring MVC**: Powers the web layer with REST controllers.

### Data Persistence
- **Spring Data JPA**: Simplifies data access and repository creation.
- **Hibernate**: Provides robust object-relational mapping.

### Security
- **Spring Security**: Manages authentication and authorization.
- **JWT (JSON Web Tokens)**: Implements secure, stateless user authentication.
- **OAuth2 Login**: Supports third-party login and social authentication.

### Additional Tools & Libraries
- **Lombok**: Minimizes boilerplate code.
- **FlowerMapper**: Handles entity-to-DTO conversions.
- **GraphQL**: Offers a flexible API layer for querying data.
- **Maven**: Manages project dependencies and build lifecycle.

### Logging & Monitoring
- **Logback with Logstash Encoder**: Provides structured JSON logging, ready for ELK integration.

### Messaging (Planned)
- **RabbitMQ / Kafka**: Future integration for facilitating asynchronous, event-driven communication.

## 🌐 Project Structure

```
├── src/main/java
│   └── com/example/flowerstore
│       ├── configuration      # Configurations for MVC, security, messaging, etc.
│       ├── controller         # REST and GraphQL controllers
│       ├── domain             # JPA entities representing the database
│       ├── dto                # Data Transfer Objects
│       ├── mapper             # Classes for converting between entities and DTOs
│       ├── repository         # Spring Data repositories for data access
│       ├── security           # JWT filters, providers, and OAuth2 integrations
│       ├── service            # Business logic implementation
│       └── ...                # Additional packages and utilities
├── src/main/resources         # Application configuration & static resources
│   ├── application.properties # Configurations for database, messaging, etc.
│   └── logback-spring.xml     # Logging configuration prepared for ELK
└── pom.xml                    # Maven build configuration file
```

## 🔄 Setup and Running the Application

1. **Clone the Repository**

   ```bash
   git clone https://github.com/your-username/flowerstore.git
   cd flowerstore
   ```

2. **Configure Application Properties**

   Update `src/main/resources/application.properties` with your specific database and messaging configurations.

3. **Build the Project**

   ```bash
   mvn clean install
   ```

4. **Run the Application**

   ```bash
   mvn spring-boot:run
   ```

   The API endpoints are accessible at `http://localhost:8080/api/v1/`.

## 🔎 Future Enhancements

- **ELK Stack Integration**: Enhance logging by integrating ElasticSearch, Logstash, and Kibana.
- **Asynchronous Messaging**: Implement RabbitMQ or Kafka for event-driven communication.

## 🏛️ License

This project is licensed under the MIT License.
