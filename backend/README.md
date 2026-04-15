# Todo Application - Backend (Spring Boot)

This is the backend service for the FullStack Todo Application. It provides REST APIs for managing todos, authentication and application health.


## Features

- RESTful API for Todo management (Create, Read, Update, Delete)
- User authentication (login/register)
- Protected routes using token-based authentication
- H2 in-memory database
- Spring Boot Actuator (health endpoints)
- Unit and integration testing


```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/example/todo/
│   │   │   ├── TodoApplication.java        # Main app
│   │   │   ├── controller/                 # REST controllers
│   │   │   ├── service/                    # Business logic
│   │   │   ├── repository/                 # JPA repositories
│   │   │   ├── entity/                     # JPA entities
│   │   │   └── config/                     # Security / CORS config
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql / schema.sql
│   └── test/                              # Unit & integration tests
├── pom.xml
└── README.md
```


## Prerequisites

- Java 17+
- Maven
- IDE (VS Code / IntelliJ)


## Running the Application

```bash
cd backend
mvn spring-boot:run

## Getting Started

### Prerequisites

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd backend
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run tests**
   ```bash
   mvn test
   ```

4. **Start the application**
   ```

   Or run the JAR file:
   ```bash
   mvn package
   java -jar target/demo-0.0.1-SNAPSHOT.jar
   ```

5. **Access the application**
   - API Base URL: `http://localhost:8084/api`
   - H2 Console: `http://localhost:8084/h2-console`
   - Health Check: `http://localhost:8084/api/health`

### H2 Database Connection

When accessing the H2 console:
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: `password`

## Configuration

The application is configured via [`application.properties`](src/main/resources/application.properties):

```properties
# Server runs on port 8080
server.port=808o

# H2 in-memory database
spring.datasource.url=jdbc:h2:file:./data/todoapp
spring.h2.console.enabled=true

# JPA settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Actuator endpoints
management.endpoints.web.exposure.include=health,info,metrics
```

## API Examples

### Get All Todos
```bash
curl http://localhost:8084/api/students/getAllTodos
```

### Create a todo
```bash
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{
  "title": "Builda TODO API",
  "status": "OPEN",
  "priority": "HIGH",
  "dueDate": "2026-03-25"
}'
```

### Health Check
```bash
curl http://localhost:8080/api/health
```

## Testing

The project includes:
- **Unit tests** for controllers using MockMvc
- **Integration tests** for the Spring Boot application context
- **Test coverage** for health endpoints and todo operations

Run tests with:
```bash
./mvnw test
```

View test reports in `target/surefire-reports/`

## Development


### CORS Configuration
