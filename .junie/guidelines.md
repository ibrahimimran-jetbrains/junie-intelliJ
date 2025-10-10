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

## 📋 Task List Management

* **Location**: All project tasks are tracked in `docs/tasksCompleted.md` with enumerated checkboxes.
* **Status Updates**: Mark completed tasks by changing `[ ]` to `[x]` and update the progress summary section.
* **Task References**: Reference specific commit hashes or pull request numbers in task comments for traceability.
* **Progress Tracking**: Update completion percentages after each work session to maintain accurate project status.
* **Task Dependencies**: Complete tasks in sequential order within each phase unless explicitly noted as independent.
* **Documentation**: Add implementation notes or deviations directly in the task list for future reference.

---
