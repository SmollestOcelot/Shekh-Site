# Shekh's Portfolio Backend

A production-ready RESTful API built with **Spring Boot 3** for managing stories and doodles (artwork). This backend demonstrates best practices in enterprise Java development, including layered architecture, comprehensive testing, API documentation, and modern Spring features.

## 🚀 Features

### Core Functionality
- **Full CRUD Operations** - Create, Read, Update, Delete for Stories and Doodles
- **Advanced Search** - Search by title, keyword, tags, and category
- **Pagination & Sorting** - Handle large datasets efficiently
- **Input Validation** - Bean Validation with custom error messages
- **Global Exception Handling** - Centralized error responses
- **API Documentation** - Interactive Swagger/OpenAPI UI

### Technical Highlights
- **Layered Architecture** - Controller → Service → Repository pattern
- **DTO Pattern** - Separation between API and domain models
- **Transaction Management** - Proper @Transactional boundaries
- **H2 In-Memory Database** - Easy development and testing
- **Comprehensive Testing** - Unit tests and integration tests
- **CORS Configuration** - Ready for Angular frontend integration

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.4.1**
- **Spring Data JPA**
- **H2 Database**
- **Bean Validation**
- **SpringDoc OpenAPI 3** (Swagger UI)
- **JUnit 5 & Mockito**
- **Maven**

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+

## 🏃 Getting Started

### 1. Clone the repository
```bash
git clone <your-repo-url>
cd backend
```

### 2. Build the project
```bash
./mvnw clean install
```

### 3. Run the application
```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Access the API Documentation
Open your browser and navigate to:
```
http://localhost:8080/swagger-ui.html
```

### 5. Access the H2 Console (Optional)
```
URL: http://localhost:8080/h2
JDBC URL: jdbc:h2:mem:shekh
Username: sa
Password: (leave blank)
```

## 📡 API Endpoints

### Stories API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/stories` | Get all stories |
| GET | `/api/stories/{id}` | Get story by ID |
| POST | `/api/stories` | Create new story |
| PUT | `/api/stories/{id}` | Update existing story |
| DELETE | `/api/stories/{id}` | Delete story |
| GET | `/api/stories/search?title={title}` | Search by title |
| GET | `/api/stories/search?keyword={keyword}` | Search by keyword |
| GET | `/api/stories/filter/category/{category}` | Filter by category |
| GET | `/api/stories/filter/tag/{tag}` | Filter by tag |
| GET | `/api/stories/recent` | Get 5 most recent stories |
| GET | `/api/stories/paginated?page=0&size=10` | Get paginated stories |

### Doodles API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/doodles` | Get all doodles |
| GET | `/api/doodles/{id}` | Get doodle by ID |
| POST | `/api/doodles` | Create new doodle |
| PUT | `/api/doodles/{id}` | Update existing doodle |
| DELETE | `/api/doodles/{id}` | Delete doodle |
| GET | `/api/doodles/search?title={title}` | Search by title |
| GET | `/api/doodles/search?keyword={keyword}` | Search by keyword |
| GET | `/api/doodles/filter/tag/{tag}` | Filter by tag |
| GET | `/api/doodles/recent` | Get 6 most recent doodles |
| GET | `/api/doodles/paginated?page=0&size=12` | Get paginated doodles |

## 📝 Example Requests

### Create a Story
```bash
curl -X POST http://localhost:8080/api/stories \
  -H "Content-Type: application/json" \
  -d '{
    "title": "My First Story",
    "body": "This is the content of my story...",
    "tags": "fiction,adventure",
    "category": "fiction"
  }'
```

### Search Stories
```bash
curl http://localhost:8080/api/stories/search?keyword=adventure
```

### Get Paginated Doodles
```bash
curl "http://localhost:8080/api/doodles/paginated?page=0&size=12&sortBy=createdAt&direction=DESC"
```

## 🧪 Running Tests

### Run all tests
```bash
./mvnw test
```

### Run only unit tests
```bash
./mvnw test -Dtest=*Test
```

### Run only integration tests
```bash
./mvnw test -Dtest=*IntegrationTest
```

## 📦 Project Structure

```
src/main/java/com/shekhsite/backend/
├── BackendApplication.java           # Main application class
├── common/
│   ├── error/
│   │   └── ApiError.java            # Error response model
│   └── exception/
│       ├── GlobalExceptionHandler.java
│       ├── NotFoundException.java
│       └── ResourceNotFoundException.java
├── config/
│   ├── DataInitializer.java         # Sample data loader
│   └── OpenAPIConfig.java           # Swagger configuration
├── controller/
│   ├── DoodleController.java        # Doodle REST endpoints
│   ├── StoryController.java         # Story REST endpoints
│   └── WebConfig.java               # CORS configuration
├── DTO/
│   ├── DoodleDTO.java               # Doodle data transfer object
│   └── StoryDTO.java                # Story data transfer object
├── model/
│   ├── Doodle.java                  # Doodle entity
│   └── Story.java                   # Story entity
├── repository/
│   ├── DoodleRepository.java        # Doodle data access
│   └── StoryRepository.java         # Story data access
└── service/
    ├── IDoodleService.java          # Doodle service interface
    ├── IStoryService.java           # Story service interface
    └── impl/
        ├── DoodleServiceImpl.java   # Doodle business logic
        └── StoryServiceImpl.java    # Story business logic
```

## 🔧 Configuration

Key configuration in `application.properties`:

```properties
# Database
spring.datasource.url=jdbc:h2:mem:shekh
spring.jpa.hibernate.ddl-auto=update

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2

# Logging
spring.jpa.show-sql=false
```

## 🚀 Deployment

### Package for production
```bash
./mvnw clean package -DskipTests
```

The JAR file will be in `target/backend-0.0.1-SNAPSHOT.jar`

### Run the JAR
```bash
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

## 🎯 Future Enhancements

- [ ] PostgreSQL integration for production
- [ ] Image upload service (AWS S3/Cloudinary)
- [ ] User authentication & authorization (Spring Security)
- [ ] Rate limiting
- [ ] Caching (Redis)
- [ ] Search engine integration (Elasticsearch)
- [ ] Email notifications
- [ ] WebSocket support for real-time updates

## 👤 Author

**Your Name**
- GitHub: [@smollestocelot](https://github.com/smollestocelot)
- LinkedIn: [Your LinkedIn](https://linkedin.com/in/lonatomartinez)

## 📄 License

This project is licensed under the MIT License.

## 🙏 Acknowledgments

- Built as a portfolio project to demonstrate full-stack development skills
- Special thanks to the Spring Boot community