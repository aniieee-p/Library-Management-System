# Library Management System

A full-stack web application for managing library operations built with Spring Boot and MySQL.

🚀 **Live Demo:** [https://library-management-system-production-a2c7.up.railway.app/](https://library-management-system-production-a2c7.up.railway.app/)

## Tech Stack

**Backend:** Java 17, Spring Boot, Spring Data JPA, MySQL, Maven  
**Frontend:** HTML5, CSS3, Bootstrap 5, JavaScript

## Features

- User authentication with role-based access (Admin/User)
- Book management (CRUD operations)
- User management
- Book issue and return tracking
- Automatic fine calculation (₹10/day overdue)
- Search books by title, author, or category
- Dashboard with real-time statistics
- RESTful API endpoints
- Input validation and error handling
- Responsive UI

## Setup

### Prerequisites
- JDK 17+
- MySQL 8.0+
- Maven 3.6+

### Installation

1. Clone the repository
```bash
git clone <repository-url>
cd library-management-system
```

2. Create database
```sql
CREATE DATABASE library_db;
```

3. Update database credentials in `src/main/resources/application.properties`
```properties
spring.datasource.username=root
spring.datasource.password=your_password
```

4. Run the application
```bash
mvn clean install
mvn spring-boot:run
```

5. Open http://localhost:8080

## Live Deployment

**Production URL:** [https://library-management-system-production-a2c7.up.railway.app/](https://library-management-system-production-a2c7.up.railway.app/)

Deployed on Railway with MySQL database.

## Default Login

| Role | Email | Password |
|------|-------|----------|
| Admin | admin@library.com | admin123 |
| User | user@library.com | user123 |

## API Endpoints

See [API_DOCUMENTATION.md](API_DOCUMENTATION.md) for complete API reference.

**Main endpoints:**
- `POST /api/auth/register` - Register user
- `POST /api/auth/login` - Login
- `GET /api/books` - Get all books
- `POST /api/books` - Add book (Admin)
- `POST /api/issues` - Issue book
- `PUT /api/issues/{id}/return` - Return book

## Deployment

See [DEPLOYMENT_GUIDE.md](DEPLOYMENT_GUIDE.md) for deployment instructions.

## License

MIT
