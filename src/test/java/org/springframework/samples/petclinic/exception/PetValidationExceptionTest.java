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
package org.springframework.samples.petclinic.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link PetValidationException}.
 *
 * @author Junie AI
 */
class PetValidationExceptionTest {

	@Test
	void shouldCreateExceptionWithMessage() {
		// Given
		String message = "Pet validation failed";

		// When
		PetValidationException exception = new PetValidationException(message);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("PET_VALIDATION_ERROR");
		assertThat(exception.getMessage()).isEqualTo(message);
		assertThat(exception.getFieldErrors()).isNull();
		assertThat(exception.getPetId()).isNull();
		assertThat(exception.hasFieldErrors()).isFalse();
		assertThat(exception.getTimestamp()).isNotNull();
	}

	@Test
	void shouldCreateExceptionWithMessageAndPetId() {
		// Given
		String message = "Pet validation failed";
		Integer petId = 42;

		// When
		PetValidationException exception = new PetValidationException(message, petId);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("PET_VALIDATION_ERROR");
		assertThat(exception.getMessage()).isEqualTo(message);
		assertThat(exception.getPetId()).isEqualTo(petId);
		assertThat(exception.getFieldErrors()).isNull();
		assertThat(exception.hasFieldErrors()).isFalse();
	}

	@Test
	void shouldCreateExceptionWithMessageAndFieldErrors() {
		// Given
		String message = "Pet validation failed";
		Map<String, List<String>> fieldErrors = new HashMap<>();
		fieldErrors.put("name", Arrays.asList("Pet name is required"));
		fieldErrors.put("type", Arrays.asList("Invalid pet type"));

		// When
		PetValidationException exception = new PetValidationException(message, fieldErrors);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("PET_VALIDATION_ERROR");
		assertThat(exception.getMessage()).isEqualTo(message);
		assertThat(exception.getFieldErrors()).isEqualTo(fieldErrors);
		assertThat(exception.getPetId()).isNull();
		assertThat(exception.hasFieldErrors()).isTrue();
	}

	@Test
	void shouldCreateExceptionWithMessageFieldErrorsAndPetId() {
		// Given
		String message = "Pet validation failed";
		Map<String, List<String>> fieldErrors = new HashMap<>();
		fieldErrors.put("birthDate", Arrays.asList("Birth date cannot be in the future"));
		Integer petId = 123;

		// When
		PetValidationException exception = new PetValidationException(message, fieldErrors, petId);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("PET_VALIDATION_ERROR");
		assertThat(exception.getMessage()).isEqualTo(message);
		assertThat(exception.getFieldErrors()).isEqualTo(fieldErrors);
		assertThat(exception.getPetId()).isEqualTo(petId);
		assertThat(exception.hasFieldErrors()).isTrue();
	}

	@Test
	void shouldCreateExceptionWithMessageAndCause() {
		// Given
		String message = "Pet validation error occurred";
		RuntimeException cause = new RuntimeException("Database constraint violation");

		// When
		PetValidationException exception = new PetValidationException(message, cause);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("PET_VALIDATION_ERROR");
		assertThat(exception.getMessage()).isEqualTo(message);
		assertThat(exception.getCause()).isEqualTo(cause);
		assertThat(exception.getFieldErrors()).isNull();
		assertThat(exception.getPetId()).isNull();
		assertThat(exception.hasFieldErrors()).isFalse();
	}

	@Test
	void shouldHandleNullPetId() {
		// Given
		String message = "Pet validation failed";
		Integer petId = null;

		// When
		PetValidationException exception = new PetValidationException(message, petId);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("PET_VALIDATION_ERROR");
		assertThat(exception.getPetId()).isNull();
		assertThat(exception.getMessage()).isEqualTo(message);
	}

	@Test
	void shouldHandleNegativePetId() {
		// Given
		String message = "Pet validation failed";
		Integer petId = -1;

		// When
		PetValidationException exception = new PetValidationException(message, petId);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("PET_VALIDATION_ERROR");
		assertThat(exception.getPetId()).isEqualTo(petId);
		assertThat(exception.getMessage()).isEqualTo(message);
	}

	@Test
	void shouldHandleEmptyFieldErrors() {
		// Given
		String message = "Pet validation failed";
		Map<String, List<String>> emptyFieldErrors = new HashMap<>();

		// When
		PetValidationException exception = new PetValidationException(message, emptyFieldErrors);

		// Then
		assertThat(exception.getFieldErrors()).isEqualTo(emptyFieldErrors);
		assertThat(exception.hasFieldErrors()).isFalse();
	}

	@Test
	void shouldInheritFromPetClinicException() {
		// Given
		String message = "Test pet validation error";

		// When
		PetValidationException exception = new PetValidationException(message);

		// Then
		assertThat(exception).isInstanceOf(PetClinicException.class);
		assertThat(exception).isInstanceOf(RuntimeException.class);
	}

	@Test
	void shouldHandleMultipleFieldErrorsPerField() {
		// Given
		String message = "Multiple validation errors";
		Map<String, List<String>> fieldErrors = new HashMap<>();
		fieldErrors.put("name", Arrays.asList("Name is required", "Name must be at least 2 characters",
				"Name contains invalid characters"));

		// When
		PetValidationException exception = new PetValidationException(message, fieldErrors);

		// Then
		assertThat(exception.getFieldErrors().get("name")).hasSize(3);
		assertThat(exception.getFieldErrors().get("name")).containsExactly("Name is required",
				"Name must be at least 2 characters", "Name contains invalid characters");
		assertThat(exception.hasFieldErrors()).isTrue();
	}

}
