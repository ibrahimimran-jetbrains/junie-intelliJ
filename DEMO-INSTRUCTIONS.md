# Spring PetClinic Demo Instructions

## Running the Application

To run the Spring PetClinic application:

```bash
# Option 1: Using the pre-built JAR file
java -jar target/spring-petclinic-3.5.0-SNAPSHOT.jar

# Option 2: Using Maven Wrapper
./mvnw spring-boot:run
```

## Accessing the Application

Once the application is running:
1. Open a web browser
2. Navigate to http://localhost:8080
3. You should see the PetClinic welcome page

## Demo Features

### 1. Find Owners
- Click on "FIND OWNERS" in the navigation bar
- Search for owners by last name, or leave empty to find all
- Click on an owner to view their details and pets

### 2. Add a New Owner
- Click on "FIND OWNERS" → "Add Owner"
- Fill in the form and submit

### 3. Add a New Pet
- Find an owner → "Add New Pet"
- Fill in the pet details and submit

### 4. Add a Visit
- Find an owner and pet → "Add Visit"
- Fill in the visit details and submit

### 5. View Veterinarians
- Click on "VETERINARIANS" to see the list of vets and specialties

### 6. Error Handling Demo
- Click on "ERROR" to see how the application handles errors

## Database Information

By default, the application uses an in-memory H2 database.
- H2 console: http://localhost:8080/h2-console
- JDBC URL: Check console output (format: jdbc:h2:mem:<uuid>)
- Username: sa
- Password: (leave empty)

## Stopping the Application

Press `Ctrl+C` in the terminal where the application is running.
