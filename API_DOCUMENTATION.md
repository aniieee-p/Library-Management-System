# Smart Library Management System - API Documentation

## Base URL
```
http://localhost:8080/api
```

## Response Format

All API responses follow this structure:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": { ... }
}
```

## Authentication APIs

### 1. Register User

**Endpoint:** `POST /auth/register`

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "USER"
}
```

**Response:**
```json
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "role": "USER",
    "createdAt": "2024-01-01T10:00:00"
  }
}
```

**Validation Rules:**
- Name: Required, 2-100 characters
- Email: Required, valid email format
- Password: Required, minimum 6 characters
- Role: Optional, defaults to "USER"

---

### 2. Login User

**Endpoint:** `POST /auth/login`

**Request Body:**
```json
{
  "email": "admin@library.com",
  "password": "admin123"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "id": 1,
    "name": "Admin User",
    "email": "admin@library.com",
    "role": "ADMIN",
    "createdAt": "2024-01-01T10:00:00"
  }
}
```

**Error Response:**
```json
{
  "success": false,
  "message": "Invalid email or password",
  "data": null
}
```

---

## Book Management APIs

### 3. Get All Books

**Endpoint:** `GET /books`

**Response:**
```json
{
  "success": true,
  "message": "Books fetched successfully",
  "data": [
    {
      "id": 1,
      "title": "Clean Code",
      "author": "Robert C. Martin",
      "category": "Programming",
      "quantity": 5,
      "availableQuantity": 3,
      "isbn": "978-0132350884",
      "publisher": "Prentice Hall",
      "publishedYear": 2008
    }
  ]
}
```

---

### 4. Get Book by ID

**Endpoint:** `GET /books/{id}`

**Example:** `GET /books/1`

**Response:**
```json
{
  "success": true,
  "message": "Book fetched successfully",
  "data": {
    "id": 1,
    "title": "Clean Code",
    "author": "Robert C. Martin",
    "category": "Programming",
    "quantity": 5,
    "availableQuantity": 3,
    "isbn": "978-0132350884",
    "publisher": "Prentice Hall",
    "publishedYear": 2008
  }
}
```

---

### 5. Add New Book (Admin Only)

**Endpoint:** `POST /books`

**Request Body:**
```json
{
  "title": "Effective Java",
  "author": "Joshua Bloch",
  "category": "Programming",
  "quantity": 5,
  "isbn": "978-0134685991",
  "publisher": "Addison-Wesley",
  "publishedYear": 2017
}
```

**Response:**
```json
{
  "success": true,
  "message": "Book added successfully",
  "data": {
    "id": 11,
    "title": "Effective Java",
    "author": "Joshua Bloch",
    "category": "Programming",
    "quantity": 5,
    "availableQuantity": 5,
    "isbn": "978-0134685991",
    "publisher": "Addison-Wesley",
    "publishedYear": 2017
  }
}
```

**Validation Rules:**
- Title: Required
- Author: Required
- Category: Required
- Quantity: Required, minimum 0

---

### 6. Update Book (Admin Only)

**Endpoint:** `PUT /books/{id}`

**Example:** `PUT /books/1`

**Request Body:**
```json
{
  "title": "Clean Code - Updated",
  "author": "Robert C. Martin",
  "category": "Programming",
  "quantity": 10,
  "isbn": "978-0132350884",
  "publisher": "Prentice Hall",
  "publishedYear": 2008
}
```

**Response:**
```json
{
  "success": true,
  "message": "Book updated successfully",
  "data": { ... }
}
```

---

### 7. Delete Book (Admin Only)

**Endpoint:** `DELETE /books/{id}`

**Example:** `DELETE /books/1`

**Response:**
```json
{
  "success": true,
  "message": "Book deleted successfully",
  "data": null
}
```

---

### 8. Search Books

**Endpoint:** `GET /books/search?query={query}`

**Example:** `GET /books/search?query=java`

**Response:**
```json
{
  "success": true,
  "message": "Search completed",
  "data": [
    {
      "id": 3,
      "title": "Head First Java",
      "author": "Kathy Sierra",
      "category": "Programming",
      "quantity": 4,
      "availableQuantity": 4
    }
  ]
}
```

**Search Criteria:**
- Searches in: Title, Author, Category
- Case-insensitive
- Partial match supported

---

### 9. Get Books by Category

**Endpoint:** `GET /books/category/{category}`

**Example:** `GET /books/category/Programming`

**Response:**
```json
{
  "success": true,
  "message": "Books fetched successfully",
  "data": [ ... ]
}
```

---

## User Management APIs

### 10. Get All Users (Admin Only)

**Endpoint:** `GET /users`

**Response:**
```json
{
  "success": true,
  "message": "Users fetched successfully",
  "data": [
    {
      "id": 1,
      "name": "Admin User",
      "email": "admin@library.com",
      "role": "ADMIN",
      "createdAt": "2024-01-01T10:00:00"
    }
  ]
}
```

---

### 11. Get User by ID

**Endpoint:** `GET /users/{id}`

**Example:** `GET /users/1`

**Response:**
```json
{
  "success": true,
  "message": "User fetched successfully",
  "data": {
    "id": 1,
    "name": "Admin User",
    "email": "admin@library.com",
    "role": "ADMIN"
  }
}
```

---

### 12. Update User

**Endpoint:** `PUT /users/{id}`

**Request Body:**
```json
{
  "name": "Updated Name",
  "email": "newemail@example.com",
  "password": "newpassword123",
  "role": "USER"
}
```

**Response:**
```json
{
  "success": true,
  "message": "User updated successfully",
  "data": { ... }
}
```

---

### 13. Delete User (Admin Only)

**Endpoint:** `DELETE /users/{id}`

**Response:**
```json
{
  "success": true,
  "message": "User deleted successfully",
  "data": null
}
```

---

## Issue & Return APIs

### 14. Issue Book

**Endpoint:** `POST /issues`

**Request Body:**
```json
{
  "userId": 2,
  "bookId": 1
}
```

**Response:**
```json
{
  "success": true,
  "message": "Book issued successfully",
  "data": {
    "id": 1,
    "user": {
      "id": 2,
      "name": "John Doe",
      "email": "john@example.com"
    },
    "book": {
      "id": 1,
      "title": "Clean Code",
      "author": "Robert C. Martin"
    },
    "issueDate": "2024-01-01",
    "dueDate": "2024-01-15",
    "status": "ISSUED",
    "fine": 0.00
  }
}
```

**Business Rules:**
- Book must be available (availableQuantity > 0)
- Due date is 14 days from issue date
- Available quantity is automatically decreased

---

### 15. Return Book

**Endpoint:** `PUT /issues/{id}/return`

**Example:** `PUT /issues/1/return`

**Response:**
```json
{
  "success": true,
  "message": "Book returned successfully",
  "data": {
    "id": 1,
    "returnDate": "2024-01-20",
    "status": "RETURNED",
    "fine": 50.00
  }
}
```

**Fine Calculation:**
- Fine rate: ₹10 per day
- Calculated only if returned after due date
- Formula: (Return Date - Due Date) × ₹10

---

### 16. Get All Issued Books (Admin Only)

**Endpoint:** `GET /issues`

**Response:**
```json
{
  "success": true,
  "message": "Issued books fetched successfully",
  "data": [ ... ]
}
```

---

### 17. Get User's Issued Books

**Endpoint:** `GET /issues/user/{userId}`

**Example:** `GET /issues/user/2`

**Response:**
```json
{
  "success": true,
  "message": "User's issued books fetched successfully",
  "data": [ ... ]
}
```

---

### 18. Get Currently Issued Books

**Endpoint:** `GET /issues/current`

**Response:**
```json
{
  "success": true,
  "message": "Currently issued books fetched successfully",
  "data": [
    {
      "id": 1,
      "user": { ... },
      "book": { ... },
      "issueDate": "2024-01-01",
      "dueDate": "2024-01-15",
      "status": "ISSUED"
    }
  ]
}
```

---

### 19. Get Overdue Books

**Endpoint:** `GET /issues/overdue`

**Response:**
```json
{
  "success": true,
  "message": "Overdue books fetched successfully",
  "data": [ ... ]
}
```

---

## Dashboard APIs

### 20. Get Dashboard Statistics

**Endpoint:** `GET /issues/dashboard/stats`

**Response:**
```json
{
  "success": true,
  "message": "Dashboard stats fetched successfully",
  "data": {
    "totalBooks": 10,
    "availableBooks": 25,
    "issuedBooks": 5,
    "totalUsers": 15,
    "overdueBooks": 2
  }
}
```

---

## Error Responses

### 404 Not Found
```json
{
  "success": false,
  "message": "Book not found with id: '999'",
  "data": null
}
```

### 400 Bad Request
```json
{
  "success": false,
  "message": "Book is not available: Clean Code",
  "data": null
}
```

### 401 Unauthorized
```json
{
  "success": false,
  "message": "Invalid email or password",
  "data": null
}
```

### 409 Conflict
```json
{
  "success": false,
  "message": "Email already registered: john@example.com",
  "data": null
}
```

### 422 Validation Error
```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "email": "Invalid email format",
    "password": "Password must be at least 6 characters"
  }
}
```

### 500 Internal Server Error
```json
{
  "success": false,
  "message": "An unexpected error occurred: ...",
  "data": null
}
```

---

## Testing with cURL

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@library.com","password":"admin123"}'
```

### Get All Books
```bash
curl http://localhost:8080/api/books
```

### Add Book
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title":"Test Book",
    "author":"Test Author",
    "category":"Programming",
    "quantity":5
  }'
```

### Issue Book
```bash
curl -X POST http://localhost:8080/api/issues \
  -H "Content-Type: application/json" \
  -d '{"userId":2,"bookId":1}'
```

---

## Rate Limiting

Currently, no rate limiting is implemented. For production:
- Implement rate limiting per IP/user
- Suggested: 100 requests per minute per user

## CORS

CORS is enabled for all origins (`*`). For production:
- Restrict to specific domains
- Configure in `WebConfig.java`

---

**For more information, refer to the source code in the `controller` package.**
