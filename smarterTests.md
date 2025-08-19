### Short answer
Yes. Below is a practical playbook to create smarter tests in a Spring Boot (PetClinic) project and reuse examples across unit, web, and data tests. It includes ready-to-copy JUnit 5 patterns, reusable fixtures, and test-slice setups.

---

### Core principles for smarter tests
- Test behavior, not implementation: verify outputs, state changes, and interactions at stable boundaries (controllers, services, repositories, validators).
- Prefer fast, focused tests: unit tests first; complement with slice tests (@WebMvcTest, @DataJpaTest) and a few end-to-end paths.
- Reuse examples centrally: define inputs/expected outcomes once and consume them via @MethodSource, custom ArgumentsProvider, or shared fixture builders.
- Make data setup obvious: use Test Data Builders (or Object Mother) to construct valid domain objects and then override specifics per test.
- Parameterize whenever rules repeat: validators, mapping rules, and controller branching are perfect for @ParameterizedTest.

---

### Reusable example sources (one place, many tests)
Create a test-only class that exports domain examples for reuse.

```java
// src/test/java/org/springframework/samples/petclinic/testing/Examples.java
package org.springframework.samples.petclinic.testing;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.samples.petclinic.owner.*;

public final class Examples {
  private Examples() {}

  // Example inputs and expected validation outcome for PetValidator
  public static Stream<Arguments> validPets() {
    return Stream.of(
        arguments(pet().name("Leo").birthDate(LocalDate.now().minusYears(2)).build()),
        arguments(pet().name("Milo").birthDate(LocalDate.now().minusDays(10)).build())
    );
  }

  public static Stream<Arguments> invalidPets() {
    return Stream.of(
        arguments(pet().name(null).birthDate(LocalDate.now().minusYears(1)).build(), "name"),
        arguments(pet().name("").birthDate(LocalDate.now().minusYears(1)).build(), "name"),
        arguments(pet().name("Nina").birthDate(null).build(), "birthDate")
    );
  }

  // Builder shortcuts (see Test Data Builder below)
  public static PetBuilder pet() { return new PetBuilder(); }
  public static OwnerBuilder owner() { return new OwnerBuilder(); }
}
```

---

### Test Data Builders (concise, valid-by-default objects)
Use small builders so tests read like stories and avoid brittle constructors.

```java
// src/test/java/org/springframework/samples/petclinic/testing/OwnerBuilder.java
package org.springframework.samples.petclinic.testing;

import java.util.HashSet;
import org.springframework.samples.petclinic.owner.*;

public class OwnerBuilder {
  private String firstName = "George";
  private String lastName = "Franklin";
  private String address = "110 W. Liberty St.";
  private String city = "Madison";
  private String telephone = "6085551023";

  public OwnerBuilder firstName(String v) { this.firstName = v; return this; }
  public OwnerBuilder lastName(String v) { this.lastName = v; return this; }
  public OwnerBuilder telephone(String v) { this.telephone = v; return this; }
  // … other setters

  public Owner build() {
    Owner o = new Owner();
    o.setFirstName(firstName);
    o.setLastName(lastName);
    o.setAddress(address);
    o.setCity(city);
    o.setTelephone(telephone);
    o.setPetsInternal(new HashSet<>());
    return o;
  }
}
```

```java
// src/test/java/org/springframework/samples/petclinic/testing/PetBuilder.java
package org.springframework.samples.petclinic.testing;

import java.time.LocalDate;
import org.springframework.samples.petclinic.owner.*;

public class PetBuilder {
  private String name = "Leo";
  private LocalDate birthDate = LocalDate.now().minusYears(1);
  private PetType type;
  private Owner owner;

  public PetBuilder name(String v) { this.name = v; return this; }
  public PetBuilder birthDate(LocalDate v) { this.birthDate = v; return this; }
  public PetBuilder type(PetType v) { this.type = v; return this; }
  public PetBuilder owner(Owner v) { this.owner = v; return this; }

  public Pet build() {
    Pet p = new Pet();
    p.setName(name);
    p.setBirthDate(birthDate);
    if (type != null) p.setType(type);
    if (owner != null) p.setOwner(owner);
    return p;
  }
}
```

---

### Parameterized tests that reuse the examples
Perfect for validators and branching logic.

```java
// src/test/java/org/springframework/samples/petclinic/owner/PetValidatorTests.java
package org.springframework.samples.petclinic.owner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.samples.petclinic.testing.Examples.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

class PetValidatorTests {

  private final PetValidator validator = new PetValidator();

  @ParameterizedTest
  @MethodSource("org.springframework.samples.petclinic.testing.Examples#validPets")
  void accepts_valid_pets(Pet pet) {
    Errors errors = new BeanPropertyBindingResult(pet, "pet");
    validator.validate(pet, errors);
    assertThat(errors.hasErrors()).isFalse();
  }

  @ParameterizedTest
  @MethodSource("org.springframework.samples.petclinic.testing.Examples#invalidPets")
  void rejects_invalid_pets(Pet pet, String field) {
    Errors errors = new BeanPropertyBindingResult(pet, "pet");
    validator.validate(pet, errors);
    assertThat(errors.hasFieldErrors(field)).isTrue();
  }
}
```

Benefits:
- The same example sets can be referenced by other validator tests.
- When a rule changes, update the example source once.

---

### Reusable MVC test base with helper methods
Abstract base class consolidates MockMvc, JSON mapping, common headers, and helper methods for requests.

```java
// src/test/java/org/springframework/samples/petclinic/web/WebMvcTestBase.java
package org.springframework.samples.petclinic.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.http.MediaType;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
abstract class WebMvcTestBase {
  @Autowired protected MockMvc mockMvc;
  @Autowired protected ObjectMapper objectMapper;

  protected MockHttpServletRequestBuilder json(MockHttpServletRequestBuilder builder, Object body) throws Exception {
    return builder
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body));
  }
}
```

Then a focused controller test that reuses builders and examples:

```java
// src/test/java/org/springframework/samples/petclinic/owner/OwnerControllerWebTests.java
package org.springframework.samples.petclinic.owner;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.samples.petclinic.testing.*;

@WebMvcTest(OwnerController.class)
class OwnerControllerWebTests {

  @MockBean OwnerRepository owners; // or service used by controller

  @Test
  void show_owner_details() throws Exception {
    var owner = new OwnerBuilder().build();
    owner.setId(1);
    given(owners.findById(1)).willReturn(Optional.of(owner));

    ResultActions res = mockMvc.perform(get("/owners/1"));

    res.andExpect(status().isOk())
       .andExpect(view().name("owners/ownerDetails"))
       .andExpect(model().attributeExists("owner"));
  }
}
```

---

### Data slice tests that reuse builders
Use @DataJpaTest for repositories and map the same example data.

```java
// src/test/java/org/springframework/samples/petclinic/owner/OwnerRepositoryTests.java
package org.springframework.samples.petclinic.owner;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.testing.OwnerBuilder;

@DataJpaTest
class OwnerRepositoryTests {

  @Autowired OwnerRepository repo;

  @Test
  void save_and_find_by_last_name() {
    var owner = new OwnerBuilder().lastName("Skywalker").build();
    repo.save(owner);

    var results = repo.findByLastName("Skywalker");
    assertThat(results).extracting(Owner::getLastName).contains("Skywalker");
  }
}
```

Tip: If you need real DB behavior (e.g., with Postgres), consider Testcontainers and reuse the same builders; keep the slice tests fast by default.

---

### Extracting reusable expectations
If you repeatedly assert the same shape, wrap assertions with AssertJ custom conditions or small helpers.

```java
// src/test/java/org/springframework/samples/petclinic/testing/AssertionsEx.java
package org.springframework.samples.petclinic.testing;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.samples.petclinic.owner.Owner;

public final class AssertionsEx {
  private AssertionsEx() {}

  public static void assertValidOwner(Owner o) {
    assertThat(o.getFirstName()).isNotBlank();
    assertThat(o.getLastName()).isNotBlank();
    assertThat(o.getTelephone()).matches("\\d{10}");
  }
}
```

Use in multiple tests to avoid duplicated assertions.

---

### Custom ArgumentsProvider for complex cases
When scenarios need preparation logic, use a dedicated provider.

```java
// src/test/java/org/springframework/samples/petclinic/testing/OwnerScenarios.java
package org.springframework.samples.petclinic.testing;

import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class OwnerScenarios implements ArgumentsProvider {
  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
    return Stream.of(
      arguments(Examples.owner().lastName("Valid").build(), true),
      arguments(Examples.owner().telephone("bad").build(), false)
    );
  }
}
```

```java
@ParameterizedTest
@org.junit.jupiter.params.provider.ArgumentsSource(OwnerScenarios.class)
void owner_validation_scenarios(Owner owner, boolean valid) {
  // validate and assert
}
```

---

### Organize and enforce reuse
- Put builders and example sources in src/test/java/.../testing so all tests can import them.
- Keep names stable: Examples, Builders, AssertionsEx, and Scenarios.
- When changing rules, update examples first; refactor tests to consume them so you get compiler help if something drifts.

---

### Bonus patterns
- @Nested tests for grouping scenarios; each group can use its own @MethodSource subset.
- @TestFactory dynamic tests when scenarios are generated at runtime, yet still reuse the Example streams.
- Property-based tests (jqwik) for validators: generate many inputs around boundary values while still reusing Builders as generators.

---

### Checklist for your PetClinic project
- Use constructor injection in production code; tests can use builders/mocks to supply collaborators.
- Place all new unit tests under src/test/java and ensure they run with ./mvnw test.
- Prefer @WebMvcTest for controllers, @DataJpaTest for repositories, plain JUnit + Mockito for services and validators.
- Centralize examples and builders to keep tests DRY and easy to evolve.

If you share a specific class you want to test smarter (e.g., PetValidator, OwnerController), I can tailor the example sources and parameterized tests directly to that code.
