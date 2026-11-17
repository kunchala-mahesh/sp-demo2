# Spring Boot Basic APIs

A Spring Boot 3 RESTful web service built with Java 21, Spring Data JPA, H2 In-Memory Database, Jakarta Validation, and SpringDoc OpenAPI (Swagger UI).

---

## Features

- **RESTful Endpoints**: Full CRUD for `/posts`, `/products`, and `/users`.
- **In-Memory H2 Database**: Preloaded with sample data on application startup.
- **Input Validation**: Request validation using Jakarta Validation (`@NotBlank`, `@Email`, `@DecimalMin`, etc.).
- **Global Error Handling**: Centralized exception handling returning standardized error responses with HTTP status codes.
- **Interactive Documentation**: Swagger UI available out-of-the-box.

---

## Tech Stack

- **Java**: 21
- **Framework**: Spring Boot 3.3.4
- **Modules**:
  - Spring Web MVC
  - Spring Data JPA
  - Spring Boot Validation
  - H2 Database (in-memory)
  - Springdoc OpenAPI (Swagger UI)

---

## How to Run

From the project root:

```bash
mvn spring-boot:run
```

The application starts on port `8085` by default.

---

## Interactive Documentation & Tools

- **Swagger UI**: [http://localhost:8085/swagger-ui.html](http://localhost:8085/swagger-ui.html)
- **OpenAPI JSON Spec**: [http://localhost:8085/api-docs](http://localhost:8085/api-docs)
- **H2 Web Console**: [http://localhost:8085/h2-console](http://localhost:8085/h2-console)
  - **JDBC URL**: `jdbc:h2:mem:basicapidb`
  - **User Name**: `sa`
  - **Password**: *(leave blank)*

---

## API Reference

### 1. Posts API (`/posts`)

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/posts` | List all posts (supports `?category=` and `?search=`) |
| `GET` | `/posts/{id}` | Get post details by ID |
| `POST` | `/posts` | Create a new post |
| `PUT` | `/posts/{id}` | Update existing post |
| `DELETE` | `/posts/{id}` | Remove a post |
| `PATCH` | `/posts/{id}/like` | Increment like count |

**Example POST `/posts` Payload:**
```json
{
  "title": "Getting Started with Spring Boot",
  "content": "A detailed guide on modern Spring development.",
  "author": "alice_w",
  "category": "Engineering"
}
```

---

### 2. Products API (`/products`)

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/products` | List all products (supports `?category=`, `?search=`, `?minPrice=`, `?maxPrice=`) |
| `GET` | `/products/{id}` | Get single product by ID |
| `POST` | `/products` | Add a new product |
| `PUT` | `/products/{id}` | Update an existing product |
| `DELETE` | `/products/{id}` | Remove a product |

**Example POST `/products` Payload:**
```json
{
  "name": "Mechanical Keyboard",
  "description": "Ergonomic keyboard with hot-swappable switches",
  "price": 89.99,
  "category": "Peripherals",
  "stockQuantity": 50
}
```

---

### 3. Users API (`/users`)

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/users` | List all users |
| `GET` | `/users/{id}` | Get user by ID |
| `POST` | `/users` | Register a new user |
| `PUT` | `/users/{id}` | Update user information |
| `DELETE` | `/users/{id}` | Remove a user |

**Example POST `/users` Payload:**
```json
{
  "username": "johndoe",
  "email": "johndoe@example.com",
  "fullName": "John Doe",
  "role": "USER"
}
```

---

## Project Structure

```
spring-boot-basic-apis/
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java/com/example/api
    │   │   ├── Application.java
    │   │   ├── config
    │   │   │   ├── DataInitializer.java
    │   │   │   └── OpenApiConfig.java
    │   │   ├── controller
    │   │   │   ├── HomeController.java
    │   │   │   ├── PostController.java
    │   │   │   ├── ProductController.java
    │   │   │   └── UserController.java
    │   │   ├── dto
    │   │   │   ├── ErrorResponse.java
    │   │   │   ├── PostRequest.java
    │   │   │   ├── ProductRequest.java
    │   │   │   └── UserRequest.java
    │   │   ├── exception
    │   │   │   ├── GlobalExceptionHandler.java
    │   │   │   └── ResourceNotFoundException.java
    │   │   ├── model
    │   │   │   ├── Post.java
    │   │   │   ├── Product.java
    │   │   │   └── User.java
    │   │   ├── repository
    │   │   │   ├── PostRepository.java
    │   │   │   ├── ProductRepository.java
    │   │   │   └── UserRepository.java
    │   │   └── service
    │   │       ├── PostService.java
    │   │       ├── ProductService.java
    │   │       └── UserService.java
    │   └── resources
    │       └── application.properties
    └── test
        └── java/com/example/api
            └── ApplicationTests.java
```
