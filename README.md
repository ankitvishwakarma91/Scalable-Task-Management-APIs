# Scalable Task Management APIs

> A secure, production-ready backend system built with **Spring Boot** — featuring JWT authentication, role-based access control, and a full-featured task management REST API.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Authentication Flow](#authentication-flow)
- [API Endpoints](#api-endpoints)
- [Security Model](#security-model)
- [Error Handling](#error-handling)
- [Author](#author)

---

## Features

### 🔐 Authentication & Authorization
- User Registration & Login
- JWT-based stateless authentication
- Role-Based Access Control (`USER` / `ADMIN`)

### 📋 Task Management
- Create, Read, Update, and Delete tasks
- Retrieve all tasks scoped to the authenticated user
- Ownership-enforced access on individual tasks

### 👑 Admin Capabilities
- View all registered users
- View and manage all tasks across the system
- Delete any task regardless of ownership

### ⚙️ Backend Capabilities
- RESTful API design following best practices
- Global Exception Handling with structured JSON responses
- Input validation with meaningful error messages
- Swagger / OpenAPI 3 interactive documentation
- Fully secured endpoints via JWT

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend Framework | Spring Boot, Spring Security |
| Database | MySQL |
| Authentication | JWT (JSON Web Tokens) |
| API Documentation | Swagger (OpenAPI 3) |
| Build Tool | Maven |

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8.0+

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/intern-assignment.git
cd intern-assignment
```

### 2. Configure the Database

Update `src/main/resources/application.properties` with your MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The server starts at `http://localhost:8080`.

---

## API Documentation

Interactive Swagger UI is available once the application is running:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Authentication Flow

1. **Register** a new user account:
   ```
   POST /api/v1/auth/register
   ```

2. **Login** with your credentials:
   ```
   POST /api/v1/auth/login
   ```

3. **Copy** the JWT token from the response body.

4. **Authorize** in Swagger UI:
   ```
   Authorize → Bearer <your_token>
   ```

5. All protected endpoints will now accept your token.

---

## API Endpoints

### 🔐 Auth

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `POST` | `/api/v1/auth/register` | Register a new user | ❌ |
| `POST` | `/api/v1/auth/login` | Login and receive JWT | ❌ |
| `GET` | `/api/v1/auth/health` | Health check | ❌ |

---

### 📋 Tasks (User)

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `POST` | `/api/v1/tasks` | Create a new task | ✅ USER |
| `GET` | `/api/v1/tasks` | Get all tasks for the current user | ✅ USER |
| `GET` | `/api/v1/tasks/{id}` | Get a specific task by ID | ✅ USER |
| `PUT` | `/api/v1/tasks/{id}` | Update a task | ✅ USER |
| `DELETE` | `/api/v1/tasks/{id}` | Delete a specific task | ✅ USER |
| `DELETE` | `/api/v1/tasks` | Delete all tasks for the current user | ✅ USER |

---

### 👑 Admin

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `GET` | `/api/v1/admin/users` | Retrieve all registered users | ✅ ADMIN |
| `GET` | `/api/v1/admin/tasks` | Retrieve all tasks in the system | ✅ ADMIN |
| `DELETE` | `/api/v1/admin/tasks/{id}` | Delete any task by ID | ✅ ADMIN |

---

## Security Model

- A valid **JWT token** is required for all protected endpoints.
- Tokens are passed via the `Authorization` header: `Bearer <token>`.
- **Role enforcement:**
  - `USER` — Can only access and manage their own tasks.
  - `ADMIN` — Has full access to all users and tasks.
- Custom JSON responses are returned for all security errors:

| Scenario | HTTP Status |
|---|---|
| Missing or invalid token | `401 Unauthorized` |
| Insufficient permissions | `403 Forbidden` |

---

## Error Handling

All errors return a consistent, structured JSON response:

```json
{
  "message": "Access denied. Insufficient permissions.",
  "status": 403,
  "timestamp": 1710000000,
  "path": "/api/v1/admin/tasks"
}
```

| Field | Type | Description |
|---|---|---|
| `message` | `String` | Human-readable error description |
| `status` | `int` | HTTP status code |
| `timestamp` | `long` | Unix timestamp of the error |
| `path` | `String` | The API path that was requested |

---

## Author

**Ankit Vishwakarma**
Backend Developer — Java · Spring Boot · REST APIs

[![GitHub](https://img.shields.io/badge/GitHub-AnkitVishwakarma-181717?style=flat&logo=github)](https://github.com/ankitvishwakarma91)
