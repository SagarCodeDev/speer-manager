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
- [Postman collection json](#json)

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

  ## POSTMAN JSON
 ```bash
  {
	"info": {
		"_postman_id": "ad8992c0-16f6-48bb-80d0-fc54b191abfc",
		"name": "speer",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
		"_exporter_id": "26942106",
		"_collection_link": "https://galactic-water-70629.postman.co/workspace/New-Team-Workspace~01f622ea-4c1e-4a79-ac40-785b292c49bb/collection/26942106-ad8992c0-16f6-48bb-80d0-fc54b191abfc?action=share&source=collection_link&creator=26942106"
	},
	"item": [
		{
			"name": "Create User",
			"request": {
				"method": "POST",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": "{\r\n    \"firstName\": \"Sagar\",\r\n    \"lastName\": \"Sheoran\",\r\n    \"emailId\": \"sagarsheoran79@gmail.com\",\r\n    \"password\": \"pass123\",\r\n    \"dob\": 170000100,\r\n    \"contactNumber\": \"7727839857\",\r\n    \"country\": \"India\"\r\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "http://localhost:8085/speer-assessment/v1/api/auth/signup",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8085",
					"path": [
						"speer-assessment",
						"v1",
						"api",
						"auth",
						"signup"
					]
				}
			},
			"response": []
		},
		{
			"name": "get notes",
			"request": {
				"auth": {
					"type": "bearer",
					"bearer": [
						{
							"key": "token",
							"value": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3NjQ0YzA3Yi1hYTBlLTQ4M2UtYWY1MS0yOGQ0YWE1ZjdhNjUiLCJpYXQiOjE3MDQ0NTczNjksImV4cCI6MTcwNDQ2MDk2OX0._d92wBG8P7JSAbWJVJ0kBvxGjzRg-a6sgg5M-gvnue8",
							"type": "string"
						}
					]
				},
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://localhost:8085/speer-assessment/v1/notes/v1/api/notes",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8085",
					"path": [
						"speer-assessment",
						"v1",
						"notes",
						"v1",
						"api",
						"notes"
					]
				}
			},
			"response": []
		},
		{
			"name": "login user",
			"request": {
				"method": "POST",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": "{\r\n    \"email\": \"sagarsheoran79@gmail.com\",\r\n    \"password\": \"pass123\"\r\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "http://localhost:8085/speer-assessment/v1/api/auth/login",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8085",
					"path": [
						"speer-assessment",
						"v1",
						"api",
						"auth",
						"login"
					]
				}
			},
			"response": []
		},
		{
			"name": "post notes",
			"request": {
				"method": "GET",
				"header": []
			},
			"response": []
		},
		{
			"name": "Share Notes",
			"request": {
				"method": "GET",
				"header": []
			},
			"response": []
		}
	]
}
 ```
