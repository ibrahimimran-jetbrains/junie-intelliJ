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

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link OwnerNotFoundException}.
 *
 * @author Junie AI
 */
class OwnerNotFoundExceptionTest {

	@Test
	void shouldCreateExceptionWithOwnerId() {
		// Given
		Integer ownerId = 123;

		// When
		OwnerNotFoundException exception = new OwnerNotFoundException(ownerId);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("OWNER_NOT_FOUND");
		assertThat(exception.getOwnerId()).isEqualTo(ownerId);
		assertThat(exception.getMessage()).contains("Owner not found with id: 123");
		assertThat(exception.getMessage()).contains("Please ensure the ID is correct");
		assertThat(exception.getTimestamp()).isNotNull();
	}

	@Test
	void shouldCreateExceptionWithOwnerIdAndCustomMessage() {
		// Given
		Integer ownerId = 456;
		String customMessage = "Custom owner not found message";

		// When
		OwnerNotFoundException exception = new OwnerNotFoundException(ownerId, customMessage);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("OWNER_NOT_FOUND");
		assertThat(exception.getOwnerId()).isEqualTo(ownerId);
		assertThat(exception.getMessage()).isEqualTo(customMessage);
		assertThat(exception.getTimestamp()).isNotNull();
	}

	@Test
	void shouldCreateExceptionWithOwnerIdMessageAndCause() {
		// Given
		Integer ownerId = 789;
		String customMessage = "Database connection failed";
		RuntimeException cause = new RuntimeException("Connection timeout");

		// When
		OwnerNotFoundException exception = new OwnerNotFoundException(ownerId, customMessage, cause);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("OWNER_NOT_FOUND");
		assertThat(exception.getOwnerId()).isEqualTo(ownerId);
		assertThat(exception.getMessage()).isEqualTo(customMessage);
		assertThat(exception.getCause()).isEqualTo(cause);
		assertThat(exception.getTimestamp()).isNotNull();
	}

	@Test
	void shouldHandleNullOwnerId() {
		// Given
		Integer ownerId = null;

		// When
		OwnerNotFoundException exception = new OwnerNotFoundException(ownerId);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("OWNER_NOT_FOUND");
		assertThat(exception.getOwnerId()).isNull();
		assertThat(exception.getMessage()).contains("Owner not found with id: null");
	}

	@Test
	void shouldHandleNegativeOwnerId() {
		// Given
		Integer ownerId = -1;

		// When
		OwnerNotFoundException exception = new OwnerNotFoundException(ownerId);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("OWNER_NOT_FOUND");
		assertThat(exception.getOwnerId()).isEqualTo(ownerId);
		assertThat(exception.getMessage()).contains("Owner not found with id: -1");
	}

	@Test
	void shouldCreateExceptionWithZeroOwnerId() {
		// Given
		Integer ownerId = 0;

		// When
		OwnerNotFoundException exception = new OwnerNotFoundException(ownerId);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("OWNER_NOT_FOUND");
		assertThat(exception.getOwnerId()).isEqualTo(ownerId);
		assertThat(exception.getMessage()).contains("Owner not found with id: 0");
	}

	@Test
	void shouldInheritFromPetClinicException() {
		// Given
		Integer ownerId = 100;

		// When
		OwnerNotFoundException exception = new OwnerNotFoundException(ownerId);

		// Then
		assertThat(exception).isInstanceOf(PetClinicException.class);
		assertThat(exception).isInstanceOf(RuntimeException.class);
	}

}
