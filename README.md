## Two-Way Anonymous Feedback Platform Backend

- **Name:** DUKUNDIMANA Clovis
- **Student ID:** 27122
- **Project name:** Two-Way Anonymous Feedback Platform

### Project Description

This project is a Spring Boot REST API for a two-way anonymous feedback platform. Employees can have feedback sessions with managers, managers can be assigned to teams and feedback areas, and managers can write replies after a session is reviewed. The API uses Spring Data JPA to persist data in PostgreSQL.

### Technologies

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Maven Wrapper

### Prerequisites

1. Install JDK 21 and make sure `java -version` reports Java 21.
2. Install PostgreSQL and make sure the database server is running.
3. Create the project database:

```sql
CREATE DATABASE "FeedbackPlatform_db";
```

The database name is case-sensitive because it contains uppercase letters and is quoted in PostgreSQL.

### Configure the Database

Open `src/main/resources/application.properties` and set the PostgreSQL username and password for your local machine:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/FeedbackPlatform_db
spring.datasource.username=YOUR_LOCAL_POSTGRES_USERNAME
spring.datasource.password=YOUR_LOCAL_POSTGRES_PASSWORD
```

Keep `spring.jpa.hibernate.ddl-auto=update` enabled for local development so Hibernate can create/update the tables from the entities.

### Run the API

Open PowerShell in the project root, the folder containing `mvnw.cmd`, and run:

```powershell
.\mvnw.cmd spring-boot:run
```

When startup completes successfully, the API is available at:

```text
http://localhost:8000
```

Stop the server with `Ctrl+C` in the terminal.

### Build and Test

Compile the project without running tests:

```powershell
.\mvnw.cmd -q -DskipTests compile
```

Run the test suite:

```powershell
.\mvnw.cmd test
```

### API Overview

The API is organized around these resources:

- `/api/employees`
- `/api/managers`
- `/api/teams`
- `/api/feedback-areas`
- `/api/feedback-sessions`
- `/api/feedback-replies`
- `/api/dashboard`

Use Postman to send requests to `http://localhost:8000` followed by the endpoint path. For example:

```http
GET http://localhost:8000/api/employees/all
```
