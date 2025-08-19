# Al Bilad Documentation - Spring PetClinic Application

## Table of Contents
1. [Introduction](#introduction)
2. [System Overview](#system-overview)
3. [Architecture](#architecture)
4. [Domain Model](#domain-model)
5. [Use Case Diagram](#use-case-diagram)
6. [Sequence Diagram](#sequence-diagram)
7. [Key Features](#key-features)
8. [Technical Stack](#technical-stack)
9. [Database Configuration](#database-configuration)
10. [Deployment](#deployment)

## Introduction

The Spring PetClinic application is a sample application designed to demonstrate the use of Spring Boot with Java. It provides a comprehensive example of a web application that manages a veterinary clinic, including owners, pets, veterinarians, and visits.

This documentation provides an overview of the application's architecture, domain model, and key workflows to help developers understand and extend the application.

## System Overview

The PetClinic application is a web-based system that allows:
- Pet owners to register themselves and their pets
- Clinic staff to manage owner and pet records
- Scheduling and tracking of veterinary visits
- Veterinarians to view their appointments and record visit details
- Administrators to manage the system

The application demonstrates Spring Boot capabilities, including:
- Spring MVC for web controllers
- Spring Data JPA for data access
- Thymeleaf for server-side HTML rendering
- Bean Validation for form validation
- Spring Boot's auto-configuration and embedded server

## Architecture

The application follows a layered architecture pattern:

1. **Presentation Layer**: Thymeleaf templates and Spring MVC controllers
2. **Service Layer**: Business logic and transaction management
3. **Data Access Layer**: Spring Data JPA repositories
4. **Domain Layer**: JPA entities representing the business domain

The application is structured into packages by feature:
- `owner`: Classes related to pet owners and their pets
- `vet`: Classes related to veterinarians and their specialties
- `visit`: Classes related to pet visits
- `model`: Common domain model classes
- `system`: System configuration and utilities

## Domain Model

The core domain entities in the PetClinic application are:

1. **Owner**: Represents a pet owner with personal information and a collection of pets
2. **Pet**: Represents an animal owned by an owner, with a name, birth date, and type
3. **PetType**: Represents the type of animal (e.g., dog, cat, bird)
4. **Vet**: Represents a veterinarian with personal information and specialties
5. **Specialty**: Represents a veterinary specialty (e.g., radiology, surgery, dentistry)
6. **Visit**: Represents a visit of a pet to the clinic, with a date and description

Key relationships:
- An Owner can have multiple Pets (one-to-many)
- A Pet can have multiple Visits (one-to-many)
- A Vet can have multiple Specialties (many-to-many)

## Use Case Diagram

The following use case diagram illustrates the main actors and their interactions with the system:

![Use Case Diagram](use_case_diagram.md)

## Sequence Diagram

The following sequence diagram illustrates the workflow for scheduling a pet visit:

![Sequence Diagram](sequence_diagram.md)

## Key Features

1. **Owner Management**
   - Register new owners
   - Update owner information
   - Search for owners by last name
   - View owner details

2. **Pet Management**
   - Add new pets to owners
   - Update pet information
   - Record pet types

3. **Visit Management**
   - Schedule new visits
   - Record visit details
   - View visit history

4. **Veterinarian Management**
   - List all veterinarians
   - View veterinarian specialties

## Technical Stack

- **Java 17**: Programming language
- **Spring Boot**: Application framework
- **Spring MVC**: Web framework
- **Spring Data JPA**: Data access framework
- **Thymeleaf**: Server-side Java template engine
- **Hibernate Validator**: Bean validation
- **H2/MySQL/PostgreSQL**: Database options
- **Maven/Gradle**: Build tools
- **JUnit**: Testing framework
- **Bootstrap**: CSS framework

## Database Configuration

The application supports multiple database configurations:

1. **H2 (Default)**: In-memory database for development and testing
   - Auto-configured with schema and data initialization
   - H2 console available at `http://localhost:8080/h2-console`

2. **MySQL**: For production use
   - Requires MySQL server
   - Configuration in `application-mysql.properties`
   - Can be started with Docker: `docker run -e MYSQL_USER=petclinic -e MYSQL_PASSWORD=petclinic -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:9.2`

3. **PostgreSQL**: For production use
   - Requires PostgreSQL server
   - Configuration in `application-postgres.properties`
   - Can be started with Docker: `docker run -e POSTGRES_USER=petclinic -e POSTGRES_PASSWORD=petclinic -e POSTGRES_DB=petclinic -p 5432:5432 postgres:17.5`

## Deployment

The application can be deployed in several ways:

1. **Standalone JAR**:
   ```bash
   ./mvnw package
   java -jar target/*.jar
   ```

2. **Spring Boot Maven Plugin**:
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Docker Container**:
   ```bash
   ./mvnw spring-boot:build-image
   docker run -p 8080:8080 spring-petclinic:latest
   ```

4. **Docker Compose** (with database):
   ```bash
   docker-compose up
   ```

Once deployed, the application is accessible at `http://localhost:8080`.
