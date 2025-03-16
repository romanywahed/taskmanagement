# Task Management System

A Spring Boot-based Task Management System that allows users to create, update, delete, and view tasks. This system integrates with a MySQL database and provides a simple, secure API for interacting with tasks.

## Features

- **Task CRUD Operations**: 
  - Create tasks
  - View tasks
  - Update tasks
  - Delete tasks
  
- **Authentication**:
  - JWT-based authentication for secure API access.
  
- **Spring Security**: 
  - Protect endpoints using Spring Security.
  
- **API Documentation**:
  - Auto-generated OpenAPI documentation with Springdoc and Swagger UI.

## Technologies Used

- **Spring Boot 2.7.12** for backend development.
- **Spring Data JPA** for database interaction.
- **MySQL** as the database for storing tasks.
- **Spring Security** for handling user authentication and authorization.
- **JWT (JSON Web Tokens)** for secure API access.
- **Spring AOP** for aspect-oriented programming and logging.
- **Lombok** for reducing boilerplate code.
- **Swagger/OpenAPI** for API documentation.

## Setup Instructions

### Prerequisites

- Java 11 or higher
- MySQL Database
- Maven

### Steps to Run the Application

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/taskmanagement.git
   cd taskmanagement
