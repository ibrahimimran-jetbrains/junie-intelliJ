## 📝 Code Style & Conventions

* **Dependency Injection**: **Always use constructor injection** for Spring components. Avoid `@Autowired` on fields.
    ```java
    // Correct
    private final OwnerRepository owners;

    public OwnerController(OwnerRepository clinicService) {
        this.owners = clinicService;
    }
    ```
* **Entities**: All database entities must be located in the `org.springframework.samples.petclinic.model` package and use JPA annotations.
* **Controllers**: Standard web controllers in the `web` package should return a `String` corresponding to the Thymeleaf view name.

---

## ✅ Testing & Validation

* **Unit Tests**: All new or modified business logic in a `service` or `web` class **must** be accompanied by unit tests in `src/test/java`.
* **Execution**: Before submitting any changes, ensure all tests pass by running the project's Maven wrapper command: `./mvnw test`.

---
