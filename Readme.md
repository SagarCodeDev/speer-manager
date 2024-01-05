# Speer Assessment Project

Welcome to the Speer Assessment Project! This project is built using Kotlin, Java, Dagger, Gradle, Grizzly, MongoDB, and Redis. It provides APIs for user authentication, CRUD operations, and implements various technologies for a robust backend system.

## Table of Contents

- [Features](#features)
- [Setup](#setup)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [API Endpoints](#api-endpoints)
- [Dependencies](#dependencies)

## Features

- User authentication with JWT and Bcrypt password hashing.
- CRUD operations for managing user data.
- Token-based authentication with a default expiration of 1 hour.
- Rate limiting with a maximum of 100 API calls per user.

## Setup

1. Clone the repository:

    ```bash
    git clone <repository_url>
    cd speer-assessment
    ```

2. Install dependencies:

    ```bash
    ./gradlew build
    ```

3. Run the application:

   Go to Main.kt file, add base.xml as program argument and run it

## Usage

1. Create an account:

    ```bash
    POST /speer-assessment/v1/api/auth/signup
    ```

2. Log in to get a token:

    ```bash
    POST /speer-assessment/v1/api/auth/login
    ```

3. Perform CRUD operations using the obtained token by using the token as a Bearer token.
   Sample curl for getting the Notes:
   curl --location 'http://localhost:8085/speer-assessment/v1/notes/v1/api/notes' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlNzE0YzU0OS0zOGU3LTQ1MGQtYThlMy05MDMwM2E3Zjc1NDgiLCJpYXQiOjE3MDQ0NjcyOTEsImV4cCI6MTcwNDQ3MDg5MX0._zx2iQaYv0hwlLIWZLtqXTTcZ91PcGgJ1tvTeTqu9bo'

## Project Structure

- **src/main/java/resources**:API endpoint controllers.
- **src/main/java/services**: Business logic for the application.
- **src/main/java/repositories**: Database interaction logic.
-  Configuration files,  `base.xml` with IP, port, rate limit, DB name, collection name, etc.
-  build.gradle contains all the project dependencies
-  **di/** directory contains all the files used for dependency injection
-  Main.kt is the the start file.

## Configuration

- All configuration details can be found in `src/main/resources/base.xml`. Adjust IP, port, rate limit, database settings, etc., as needed.

## API Endpoints

1. **Create an account:**
    - Endpoint: `POST /speer-assessment/v1/api/auth/signup`

2. **Login:**
    - Endpoint: `POST /speer-assessment/v1/api/auth/login`

3. **CRUD Operations:**
    - CRUD endpoints can be found in `src/main/java/resources`.

## Dependencies

- Kotlin
- Java
- Dagger (Dependency Injection)
- Gradle (Build Tool)
- Grizzly (HTTP Server)
- MongoDB (Database)
- Redis (Session Management)
- JWT (Authentication)
- Bcrypt (Password Hashing)

  ## Software requirements to run the project
- JDK 17
- Mongo Server(on port 271017 by default)
- Redis server(on port 6379 by default)
- Gradle 8.0
- IntelliJ
