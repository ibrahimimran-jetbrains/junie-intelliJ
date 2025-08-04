# Spring PetClinic Demo Guide

This guide provides step-by-step instructions to build, run, and demo the Spring PetClinic application.

## Prerequisites

- Java 17 or newer (full JDK, not a JRE)
- Git (optional, only if you want to clone the repository)
- Maven (optional, as the project includes Maven wrapper)

## Building and Running the Application

### Option 1: Using Maven Wrapper (Recommended)

1. Open a terminal/command prompt
2. Navigate to the project root directory
3. Build the application:
   ```bash
   ./mvnw package
   ```
4. Run the application:
   ```bash
   java -jar target/*.jar
   ```

### Option 2: Using Maven Directly

If you have Maven installed:

1. Open a terminal/command prompt
2. Navigate to the project root directory
3. Build the application:
   ```bash
   mvn package
   ```
4. Run the application:
   ```bash
   java -jar target/*.jar
   ```

### Option 3: Using Spring Boot Maven Plugin

This method is useful during development as it automatically reloads when code changes:

```bash
./mvnw spring-boot:run
```

## Accessing the Application

Once the application is running:

1. Open a web browser
2. Navigate to [http://localhost:8080](http://localhost:8080)
3. You should see the PetClinic welcome page

## Demo Features

### 1. Find Owners

- Click on "FIND OWNERS" in the navigation bar
- You can search for owners by last name, or leave the field empty to find all owners
- Click on an owner to view their details, including their pets

### 2. Add a New Owner

- Click on "FIND OWNERS" in the navigation bar
- Click on "Add Owner"
- Fill in the form and submit

### 3. Add a New Pet

- Find an owner
- Click on "Add New Pet"
- Fill in the pet details and submit

### 4. Add a Visit

- Find an owner and their pet
- Click on "Add Visit"
- Fill in the visit details and submit

### 5. View Veterinarians

- Click on "VETERINARIANS" in the navigation bar
- View the list of veterinarians and their specialties

### 6. Error Handling Demo

- Click on "ERROR" in the navigation bar to see how the application handles errors

## Database Options

By default, the application uses an in-memory H2 database. You can access the H2 console at:
[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

Connection details:
- JDBC URL: Check the console output for the exact URL (looks like `jdbc:h2:mem:<uuid>`)
- Username: sa
- Password: (leave empty)

### Using MySQL or PostgreSQL

To use MySQL or PostgreSQL instead of H2:

1. Start the database using Docker:

   For MySQL:
   ```bash
   docker run -e MYSQL_USER=petclinic -e MYSQL_PASSWORD=petclinic -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:9.2
   ```

   For PostgreSQL:
   ```bash
   docker run -e POSTGRES_USER=petclinic -e POSTGRES_PASSWORD=petclinic -e POSTGRES_DB=petclinic -p 5432:5432 postgres:17.5
   ```

   Or use docker-compose:
   ```bash
   docker compose up mysql
   # or
   docker compose up postgres
   ```

2. Run the application with the appropriate profile:

   For MySQL:
   ```bash
   java -jar target/*.jar --spring.profiles.active=mysql
   ```

   For PostgreSQL:
   ```bash
   java -jar target/*.jar --spring.profiles.active=postgres
   ```

## Troubleshooting

### Application Won't Start

- Ensure Java 17+ is installed: `java -version`
- Check if port 8080 is already in use: `netstat -ano | findstr 8080` (Windows) or `lsof -i:8080` (Linux/Mac)
- Verify you're in the correct directory with the project files

### Database Connection Issues

- Verify database is running and accessible
- Check connection details in application-mysql.properties or application-postgres.properties
- Ensure database user has appropriate permissions

### Build Failures

- Ensure you have internet access to download dependencies
- Try clearing Maven cache: `./mvnw dependency:purge-local-repository`
- Check Java version compatibility: `java -version`
