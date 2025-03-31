# 🌺 Flower Store E-commerce Application

This project is an e-commerce application for managing a flower store developed using Java and Spring Boot. It offers both RESTful and GraphQL APIs along with a robust security model, data persistence, logging, and planned messaging functionality.

## ✨ Table of Contents

- [🌟 Features](#features)
- [🛠️ Technology Stack](#technology-stack)
- [🌐 Project Structure](#project-structure)
- [🔄 Setup and Running the Application](#setup-and-running-the-application)
- [🔎 Future Enhancements](#future-enhancements)
- [🏛️ License](#license)

## 🌟 Features

- **🌸 Flower Management**: CRUD operations for managing flowers.
- **🛍️ Order Processing**: Handles creation and retrieval of orders.
- **🔒 User & Security**: 
  - ⛏️ JWT-based authentication for stateless security.
  - 🔗 OAuth2 login for third-party authentication.
  - ⚖️ Role-based access control using Spring Security.
- **🧐 GraphQL API**: Flexible endpoint for querying data.
- **📝 Logging**: Configured for structured JSON logging to integrate with an ELK stack.
- **🎯 Messaging (Planned)**: RabbitMQ or Kafka integration for asynchronous communication.
- **📚 Data Management**: Uses Spring Data JPA and Hibernate for ORM.

## 🛠️ Technology Stack

### ⚡ Core Frameworks
- **💻 Java**  
  The primary programming language used in the application.
- **🚀 Spring Boot**  
  Provides rapid application development.
- **🚶‍♂️ Spring MVC**  
  Implements the web layer using REST controllers.

### 🌐 Data Persistence
- **💾 Spring Data JPA**  
  Simplifies data access and reduces boilerplate code.
- **⚙️ Hibernate**  
  Manages object-relational mapping (ORM).

### 🔒 Security
- **⚖️ Spring Security**  
  Provides authentication, authorization, and protection against attacks.
- **🛡️ JWT (JSON Web Tokens)**  
  Implements stateless authentication.
- **🔗 OAuth2 Login**  
  Enables third-party authentication.

### 💡 Additional Libraries & Tools
- **🛠️ Lombok**  
  Reduces boilerplate code.
- **🌸 Model Mapping/FlowerMapper**  
  Converts between domain entities and DTOs.
- **🎮 GraphQL**  
  Provides a flexible API interface.
- **🎨 Maven**  
  Manages dependencies and builds the project.

### 🔄 Logging & Monitoring
- **📊 Logback with Logstash Encoder**  
  Uses structured JSON logging for ELK integration.

### 🌀 Messaging (Planned)
- **📢 RabbitMQ / Kafka**  
  Intended for asynchronous messaging.

## 🌐 Project Structure

```
├── src/main/java
│   └── com/example/flowerstore
│       ├── configuration      # Configurations (MVC, Security, Messaging, etc.)
│       ├── controller         # REST and GraphQL controllers
│       ├── domain             # JPA Entities representing the database
│       ├── dto                # Data Transfer Objects
│       ├── mapper             # Mapper classes for converting between domain and DTOs
│       ├── repository         # Spring Data repositories for data access
│       ├── security           # JWT filter, providers, and OAuth2 integration
│       ├── service            # Service layer with business logic
│       └── ...                # Additional packages
├── src/main/resources         # Application configurations & logging (logback-spring.xml)
│   └── application.properties # Application properties (RabbitMQ, Kafka, etc.)
└── pom.xml                    # Maven build file
```

## 🔄 Setup and Running the Application

1. **🔄 Clone the Repository**

   ```bash
   git clone https://github.com/your-username/flowerstore.git
   cd flowerstore
   ```

2. **🔧 Configure Properties**

   Update `src/main/resources/application.properties` with your database credentials and messaging settings.

3. **♻️ Build the Project**

   ```bash
   mvn clean install
   ```

4. **🔋 Run the Application**

   ```bash
   mvn spring-boot:run
   ```
   
   Access the API endpoints via `http://localhost:8080/api/v1/`.

## 🔎 Future Enhancements

- **🔄 ELK Stack Integration**  
  - Improve logging with ElasticSearch, Logstash, and Kibana.
- **🌐 Messaging System**  
  - Integrate RabbitMQ or Kafka for event-driven communication.

## 🏛️ License

This project is licensed under the MIT License.
