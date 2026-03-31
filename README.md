# Scalable-Task-Management-APIs
A secure, scalable backend system built using **Spring Boot** that provides authentication, role-based access control, and task management APIs.

---

## 📌 Features

### 🔐 Authentication & Authorization
- User Registration & Login
- JWT-based Authentication
- Role-Based Access Control (USER / ADMIN)

### 📋 Task Management
- Create, Update, Delete Tasks
- Get All Tasks (User-specific)
- Get Task by ID (Ownership enforced)

### 👑 Admin Features
- Get All Users
- Get All Tasks
- Delete Any Task

### ⚙️ Backend Capabilities
- RESTful API design
- Global Exception Handling (JSON responses)
- Input Validation
- Swagger API Documentation
- Secure endpoints with JWT

---

## 🛠️ Tech Stack

- **Backend:** Spring Boot, Spring Security
- **Database:** MySQL
- **Authentication:** JWT
- **Documentation:** Swagger (OpenAPI 3)
- **Build Tool:** Maven

---

## 🔗 API Documentation

Swagger UI available at: [http://localhost:8080/swagger-ui/index.html]
---

## 🔑 Authentication Flow

1. Register user:

POST `/api/v1/auth/register`


2. Login:

POST `/api/v1/auth/login`


3. Copy JWT Token from response

4. Use token in Swagger:

`Authorize → Bearer <your_token>`


---

## 📂 API Endpoints

### 🔐 Auth APIs
| Method | Endpoint | Description |
|--------|---------|------------|
| POST | `/api/v1/auth/register` | Register user |
| POST | `/api/v1/auth/login` | Login user |
| GET  | `/api/v1/auth/health` | Health check |

---

### 📋 Task APIs (User)
| Method | Endpoint | Description |
|--------|---------|------------|
| POST | `/api/v1/tasks` | Create task |
| GET | `/api/v1/tasks` | Get all user tasks |
| GET | `/api/v1/tasks/{id}` | Get task by ID |
| PUT | `/api/v1/tasks/{id}` | Update task |
| DELETE | `/api/v1/tasks/{id}` | Delete task |
| DELETE | `/api/v1/tasks` | Delete all tasks |

---

### 👑 Admin APIs
| Method | Endpoint | Description |
|--------|---------|------------|
| GET | `/api/v1/admin/users` | Get all users |
| GET | `/api/v1/admin/tasks` | Get all tasks |
| DELETE | `/api/v1/admin/tasks/{id}` | Delete any task |

---

## 🔒 Security

- JWT Token required for all protected APIs
- Role-based access:
  - USER → Own tasks only
  - ADMIN → Full access
- Custom JSON responses for:
  - Unauthorized (401)
  - Forbidden (403)

---

## ⚠️ Error Handling

All errors return structured JSON:

json
`{
  "message": "Error message",
  "status": 401,
  "timestamp": 1710000000,
  "path": "/api/v1/tasks"
}`

🚀 How to Run
1. Clone Repository
git clone: `https://github.com/your-username/intern-assignment.git`
cd `intern-assignment`
2. Configure Database

Update application.properties:

`spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=root
spring.datasource.password=your_password`
3. Run Application
  `mvn spring-boot:run`

👨‍💻 Author
Ankit Vishwakarma
Backend Developer | Spring Boot | Java
