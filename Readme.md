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

    ```bash
    ./gradlew run
    ```

## Usage

1. Create an account:

    ```bash
    POST /speer-assessment/v1/api/auth/signup
    ```

2. Log in to get a token:

    ```bash
    POST /speer-assessment/v1/api/auth/login
    ```

3. Perform CRUD operations using the obtained token.

## Project Structure

- **src/main/java/resources**: Configuration files, including `base.xml` with IP, port, rate limit, DB, collection, etc.
- **src/main/java/services**: Business logic for the application.
- **src/main/java/repositories**: Database interaction logic.
- **src/main/java/controllers**: API endpoint controllers.

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
