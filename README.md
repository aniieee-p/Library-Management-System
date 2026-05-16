# Smart Library Management System

A full-stack Library Management System built with Java Spring Boot, MySQL, and modern web technologies.

## 🚀 Tech Stack

**Backend:** Java 17, Spring Boot 3.x, Spring Data JPA, MySQL, JDBC, Maven  
**Frontend:** HTML5, CSS3, Bootstrap 5, JavaScript  
**Security:** BCrypt Password Encryption  
**Logging:** SLF4J, Logback

## 📋 Features

- **Authentication & Authorization** - Role-based access (Admin/User)
- **Book Management** - Complete CRUD operations
- **User Management** - Add, edit, delete users
- **Issue & Return System** - Track book transactions
- **Fine Calculation** - Automatic overdue fine (₹10/day)
- **Search Functionality** - Search by title, author, category
- **Dashboard Analytics** - Real-time statistics
- **REST APIs** - 21 RESTful endpoints
- **Exception Handling** - Global error handling
- **Input Validation** - Backend + Frontend validation
- **Responsive UI** - Mobile-friendly Bootstrap design

## 🗂️ Project Structure

```
library-management-system/
├── src/main/java/com/library/
│   ├── controller/          # REST API Controllers
│   ├── service/             # Business Logic
│   ├── repository/          # Database Access
│   ├── model/               # Entity Classes
│   ├── dto/                 # Data Transfer Objects
│   ├── exception/           # Exception Handling
│   └── config/              # Configuration
├── src/main/resources/
│   ├── application.properties
│   ├── schema.sql           # Database Schema
│   ├── data.sql             # Sample Data
│   └── static/              # Frontend Files
├── pom.xml
└── README.md
```

## 🛠️ Local Setup

### Prerequisites
- JDK 17+
- MySQL 8.0+
- Maven 3.6+

### Steps

1. **Clone the repository**
```bash
git clone <repository-url>
cd library-management-system
```

2. **Create MySQL database**
```sql
CREATE DATABASE library_db;
```

3. **Configure database**  
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=your_password
```

4. **Build and run**
```bash
mvn clean install
mvn spring-boot:run
```

5. **Access application**
```
http://localhost:8080
```

## 👤 Default Credentials

| Role | Email | Password |
|------|-------|----------|
| Admin | admin@library.com | admin123 |
| User | user@library.com | user123 |

## 📡 API Endpoints

**Authentication**
- `POST /api/auth/register` - Register user
- `POST /api/auth/login` - Login user

**Books**
- `GET /api/books` - Get all books
- `POST /api/books` - Add book (Admin)
- `PUT /api/books/{id}` - Update book (Admin)
- `DELETE /api/books/{id}` - Delete book (Admin)
- `GET /api/books/search?query=` - Search books

**Issues**
- `POST /api/issues` - Issue book
- `PUT /api/issues/{id}/return` - Return book
- `GET /api/issues/user/{userId}` - Get user's books

**Dashboard**
- `GET /api/issues/dashboard/stats` - Get statistics

See [API_DOCUMENTATION.md](API_DOCUMENTATION.md) for complete API reference.

## 🚀 Deployment

See [DEPLOYMENT_GUIDE.md](DEPLOYMENT_GUIDE.md) for deployment instructions.

## 🔒 Security

- BCrypt password encryption
- SQL injection prevention (JPA)
- Input validation
- Role-based access control

## 📄 License

MIT License

## 👨‍💻 Author

Created for Java Full Stack Engineer interview preparation.
