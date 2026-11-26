# Job Application Tracker

A full-stack web application for tracking job applications, built with **Java 17**, **Spring Boot** and a vanilla **HTML/CSS/JavaScript** frontend.

I built this to manage my own job search: it records every role I apply for, tracks its status from application to offer, and flags roles that require security clearance.

![Screenshot](C:\Users\pamarthi srinivasu\OneDrive\Documents\Pictures\Screenshots)

## Features

- Add, view, edit and delete job applications
- Track status: `APPLIED`, `INTERVIEW`, `OFFER`, `REJECTED`
- Filter applications by status
- Flag roles that require security clearance
- Input validation with clear, field-level error messages
- Consistent JSON error responses through a global exception handler

## Tech stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 4, Spring Web MVC |
| Persistence | Spring Data JPA, Hibernate, H2 (in-memory SQL database) |
| Validation | Jakarta Bean Validation (`@NotBlank`, `@Valid`) |
| Frontend | HTML5, CSS3, JavaScript (`fetch` API) |
| Build | Maven |
| Testing | JUnit, Postman |

## Architecture

The backend follows a layered architecture:

```
Browser (HTML/CSS/JS)
        │  HTTP + JSON
        ▼
Controller   → handles HTTP requests and responses
        ▼
Service      → business logic
        ▼
Repository   → database access (Spring Data JPA)
        ▼
H2 Database
```

```
src/main/java/com/srinivasu/job_tracker/
├── model/        JobApplication entity, Status enum
├── repository/   JobApplicationRepository
├── service/      JobApplicationService, ApplicationNotFoundException
└── controller/   JobApplicationController, GlobalExceptionHandler

src/main/resources/
├── static/       index.html, style.css, app.js (frontend)
└── application.properties
```

## REST API

Base URL: `http://localhost:8080/api/applications`

| Method | Endpoint | Description | Success |
|---|---|---|---|
| GET | `/api/applications` | List all applications | 200 |
| GET | `/api/applications?status=INTERVIEW` | Filter by status | 200 |
| GET | `/api/applications/{id}` | Get one application | 200 |
| POST | `/api/applications` | Create an application | 201 |
| PUT | `/api/applications/{id}` | Update an application | 200 |
| DELETE | `/api/applications/{id}` | Delete an application | 204 |

### Example request

```http
POST /api/applications
Content-Type: application/json

{
  "company": "Example Ltd",
  "role": "Junior Java Developer",
  "location": "London",
  "status": "APPLIED",
  "requiresClearance": false,
  "notes": "Applied via LinkedIn"
}
```

### Example error responses

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Job application not found with id 99"
}
```

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "errors": { "company": "Company is required" }
}
```

## Running locally

**Requirements:** Java 17 or later. Maven is included through the wrapper.

```bash
git clone https://github.com/YOUR-USERNAME/job-tracker.git
cd job-tracker
./mvnw spring-boot:run        # Windows: .\mvnw.cmd spring-boot:run
```

Then open **http://localhost:8080** in your browser.

The H2 database console is available at `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:jobtracker`, user `sa`, no password).

> Data is stored in memory and resets when the application restarts.

## What I learned

- Designing a REST API with proper HTTP methods and status codes
- Structuring a Spring Boot application into controller, service and repository layers
- Mapping Java objects to database tables with JPA and Hibernate
- Validating input and centralising error handling with `@RestControllerAdvice`
- Connecting a JavaScript frontend to a backend API with `fetch`
- Debugging from stack traces and application logs

## Planned improvements

- Switch to MySQL/PostgreSQL for persistent storage
- Add unit and integration tests with JUnit and Mockito
- Add search and sorting
- Deploy to a cloud platform

## Author

**Pamarthi Srinivasu**: [GitHub](https://github.com/YOUR-USERNAME) · [LinkedIn](https://www.linkedin.com/in/YOUR-PROFILE)
