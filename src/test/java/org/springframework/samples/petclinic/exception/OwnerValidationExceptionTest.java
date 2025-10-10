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
 * Unit tests for {@link OwnerValidationException}.
 *
 * @author Junie AI
 */
class OwnerValidationExceptionTest {

	@Test
	void shouldCreateExceptionWithMessage() {
		// Given
		String message = "Validation failed for owner";

		// When
		OwnerValidationException exception = new OwnerValidationException(message);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("OWNER_VALIDATION_ERROR");
		assertThat(exception.getMessage()).isEqualTo(message);
		assertThat(exception.getFieldErrors()).isNull();
		assertThat(exception.hasFieldErrors()).isFalse();
		assertThat(exception.getTimestamp()).isNotNull();
	}

	@Test
	void shouldCreateExceptionWithMessageAndFieldErrors() {
		// Given
		String message = "Owner validation failed";
		Map<String, List<String>> fieldErrors = new HashMap<>();
		fieldErrors.put("firstName", Arrays.asList("First name is required"));
		fieldErrors.put("telephone", Arrays.asList("Invalid phone format", "Phone number too long"));

		// When
		OwnerValidationException exception = new OwnerValidationException(message, fieldErrors);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("OWNER_VALIDATION_ERROR");
		assertThat(exception.getMessage()).isEqualTo(message);
		assertThat(exception.getFieldErrors()).isEqualTo(fieldErrors);
		assertThat(exception.hasFieldErrors()).isTrue();
		assertThat(exception.getFieldErrors().get("firstName")).containsExactly("First name is required");
		assertThat(exception.getFieldErrors().get("telephone")).containsExactly("Invalid phone format",
				"Phone number too long");
	}

	@Test
	void shouldCreateExceptionWithMessageAndCause() {
		// Given
		String message = "Validation error occurred";
		RuntimeException cause = new RuntimeException("Database validation failed");

		// When
		OwnerValidationException exception = new OwnerValidationException(message, cause);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("OWNER_VALIDATION_ERROR");
		assertThat(exception.getMessage()).isEqualTo(message);
		assertThat(exception.getCause()).isEqualTo(cause);
		assertThat(exception.getFieldErrors()).isNull();
		assertThat(exception.hasFieldErrors()).isFalse();
	}

	@Test
	void shouldCreateExceptionWithMessageFieldErrorsAndCause() {
		// Given
		String message = "Complete validation failure";
		Map<String, List<String>> fieldErrors = new HashMap<>();
		fieldErrors.put("city", Arrays.asList("City is required"));
		RuntimeException cause = new RuntimeException("External validation service failed");

		// When
		OwnerValidationException exception = new OwnerValidationException(message, fieldErrors, cause);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("OWNER_VALIDATION_ERROR");
		assertThat(exception.getMessage()).isEqualTo(message);
		assertThat(exception.getFieldErrors()).isEqualTo(fieldErrors);
		assertThat(exception.getCause()).isEqualTo(cause);
		assertThat(exception.hasFieldErrors()).isTrue();
	}

	@Test
	void shouldHandleEmptyFieldErrors() {
		// Given
		String message = "Validation failed";
		Map<String, List<String>> emptyFieldErrors = new HashMap<>();

		// When
		OwnerValidationException exception = new OwnerValidationException(message, emptyFieldErrors);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("OWNER_VALIDATION_ERROR");
		assertThat(exception.getFieldErrors()).isEqualTo(emptyFieldErrors);
		assertThat(exception.hasFieldErrors()).isFalse();
	}

	@Test
	void shouldHandleNullFieldErrors() {
		// Given
		String message = "Validation failed";
		Map<String, List<String>> nullFieldErrors = null;

		// When
		OwnerValidationException exception = new OwnerValidationException(message, nullFieldErrors);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("OWNER_VALIDATION_ERROR");
		assertThat(exception.getFieldErrors()).isNull();
		assertThat(exception.hasFieldErrors()).isFalse();
	}

	@Test
	void shouldInheritFromPetClinicException() {
		// Given
		String message = "Test validation error";

		// When
		OwnerValidationException exception = new OwnerValidationException(message);

		// Then
		assertThat(exception).isInstanceOf(PetClinicException.class);
		assertThat(exception).isInstanceOf(RuntimeException.class);
	}

	@Test
	void shouldHandleFieldErrorsWithNullValues() {
		// Given
		String message = "Validation failed";
		Map<String, List<String>> fieldErrors = new HashMap<>();
		fieldErrors.put("address", null);
		fieldErrors.put("lastName", Arrays.asList("Last name is required"));

		// When
		OwnerValidationException exception = new OwnerValidationException(message, fieldErrors);

		// Then
		assertThat(exception.getFieldErrors()).isEqualTo(fieldErrors);
		assertThat(exception.hasFieldErrors()).isTrue();
		assertThat(exception.getFieldErrors().get("address")).isNull();
		assertThat(exception.getFieldErrors().get("lastName")).containsExactly("Last name is required");
	}

}
