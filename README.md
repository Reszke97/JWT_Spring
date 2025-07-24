# 🔐 JWT Authentication API with Spring Boot

A secure backend authentication system built with **Spring Boot** and **JWT (JSON Web Tokens)**. This project demonstrates a typical login/registration flow, role-based access control, and protected endpoints.

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot 3**
- **Spring Security**
- **JWT (JSON Web Token)**
- **Spring Data JPA**
- **H2 Database (for testing, easily replaceable with MySQL/PostgreSQL)**
- **Lombok**

## 🔐 Features

- ✅ User registration & login
- ✅ JWT token generation and validation
- ✅ Role-based access (e.g., USER, ADMIN)
- ✅ Token filter with `OncePerRequestFilter`
- ✅ Password encryption using BCrypt
- ✅ Global exception handling
- ✅ Clean, modular code structure (Controller, Service, Repository, DTO, Config)

## 📂 Project Structure

```plaintext
└── src
    ├── config              # Security configuration, filters
    ├── controller          # Auth and test endpoints
    ├── dto                # Request/response data transfer objects
    ├── model              # User, Role entities
    ├── repository         # JPA repositories
    ├── service            # Business logic layer
    └── JwtBackendApplication.java
