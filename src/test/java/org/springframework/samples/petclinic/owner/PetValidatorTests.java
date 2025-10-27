/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link PetValidator}
 *
 * @author Wick Dynex
 */
@ExtendWith(MockitoExtension.class)
@DisabledInNativeImage
public class PetValidatorTests {

	private PetValidator petValidator;

	private Pet pet;

	private PetType petType;

	private Errors errors;

	private static final String petName = "Buddy";

	private static final String petTypeName = "Dog";

	private static final LocalDate petBirthDate = LocalDate.of(1990, 1, 1);

	@BeforeEach
	void setUp() {
		petValidator = new PetValidator();
		pet = new Pet();
		petType = new PetType();
		errors = new MapBindingResult(new HashMap<>(), "pet");
	}

	@Test
	void testValidate() {
		petType.setName(petTypeName);
		pet.setName(petName);
		pet.setType(petType);
		pet.setBirthDate(petBirthDate);

		petValidator.validate(pet, errors);

		assertFalse(errors.hasErrors());
	}

	@Test
	void testSupportsWithPetClass() {
		assertTrue(petValidator.supports(Pet.class));
	}

	@Test
	void testSupportsWithNonPetClass() {
		assertFalse(petValidator.supports(String.class));
		assertFalse(petValidator.supports(Owner.class));
	}

	@Nested
	class ValidateHasErrors {

		@Test
		void testValidateWithEmptyPetName() {
			petType.setName(petTypeName);
			pet.setName("");
			pet.setType(petType);
			pet.setBirthDate(petBirthDate);

			petValidator.validate(pet, errors);

			assertTrue(errors.hasFieldErrors("name"));
			assertEquals("required", errors.getFieldError("name").getCode());
		}

		@Test
		void testValidateWithNullPetName() {
			petType.setName(petTypeName);
			pet.setName(null);
			pet.setType(petType);
			pet.setBirthDate(petBirthDate);

			petValidator.validate(pet, errors);

			assertTrue(errors.hasFieldErrors("name"));
			assertEquals("required", errors.getFieldError("name").getCode());
		}

		@Test
		void testValidateWithWhitespacePetName() {
			petType.setName(petTypeName);
			pet.setName("   ");
			pet.setType(petType);
			pet.setBirthDate(petBirthDate);

			petValidator.validate(pet, errors);

			assertTrue(errors.hasFieldErrors("name"));
			assertEquals("required", errors.getFieldError("name").getCode());
		}

		@Test
		void testValidateWithInvalidPetTypeForNewPet() {
			pet.setName(petName);
			pet.setType(null);
			pet.setBirthDate(petBirthDate);

			petValidator.validate(pet, errors);

			assertTrue(errors.hasFieldErrors("type"));
			assertEquals("required", errors.getFieldError("type").getCode());
		}

		@Test
		void testValidateWithInvalidPetTypeForExistingPet() {
			// Set an ID to make the pet "existing" (not new)
			pet.setId(1);
			pet.setName(petName);
			pet.setType(null);
			pet.setBirthDate(petBirthDate);

			petValidator.validate(pet, errors);

			// Type is only required for new pets, so existing pets without type should
			// pass
			assertFalse(errors.hasFieldErrors("type"));
		}

		@Test
		void testValidateWithInvalidBirthDate() {
			petType.setName(petTypeName);
			pet.setName(petName);
			pet.setType(petType);
			pet.setBirthDate(null);

			petValidator.validate(pet, errors);

			assertTrue(errors.hasFieldErrors("birthDate"));
			assertEquals("required", errors.getFieldError("birthDate").getCode());
		}

		@Test
		void testValidateWithMultipleErrors() {
			// All fields invalid
			pet.setName(null);
			pet.setType(null);
			pet.setBirthDate(null);

			petValidator.validate(pet, errors);

			assertTrue(errors.hasErrors());
			assertEquals(3, errors.getErrorCount());
			assertTrue(errors.hasFieldErrors("name"));
			assertTrue(errors.hasFieldErrors("type"));
			assertTrue(errors.hasFieldErrors("birthDate"));
		}

	}

}
