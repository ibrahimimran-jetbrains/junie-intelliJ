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

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link PetClinicException}.
 *
 * @author Junie AI
 */
class PetClinicExceptionTest {

	@Test
	void shouldCreateExceptionWithErrorCodeAndMessage() {
		// Given
		String errorCode = "TEST_ERROR";
		String message = "Test error message";

		// When
		TestPetClinicException exception = new TestPetClinicException(errorCode, message);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo(errorCode);
		assertThat(exception.getMessage()).isEqualTo(message);
		assertThat(exception.getTimestamp()).isNotNull();
		assertThat(exception.getTimestamp()).isBefore(LocalDateTime.now().plusSeconds(1));
	}

	@Test
	void shouldCreateExceptionWithErrorCodeMessageAndCause() {
		// Given
		String errorCode = "TEST_ERROR";
		String message = "Test error message";
		RuntimeException cause = new RuntimeException("Cause message");

		// When
		TestPetClinicException exception = new TestPetClinicException(errorCode, message, cause);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo(errorCode);
		assertThat(exception.getMessage()).isEqualTo(message);
		assertThat(exception.getCause()).isEqualTo(cause);
		assertThat(exception.getTimestamp()).isNotNull();
	}

	@Test
	void shouldGenerateProperToStringRepresentation() {
		// Given
		String errorCode = "TEST_ERROR";
		String message = "Test error message";
		TestPetClinicException exception = new TestPetClinicException(errorCode, message);

		// When
		String toString = exception.toString();

		// Then
		assertThat(toString).contains("TestPetClinicException");
		assertThat(toString).contains("errorCode='TEST_ERROR'");
		assertThat(toString).contains("message='Test error message'");
		assertThat(toString).contains("timestamp=");
	}

	@Test
	void shouldMaintainTimestampConsistency() {
		// Given
		LocalDateTime before = LocalDateTime.now();

		// When
		TestPetClinicException exception = new TestPetClinicException("ERROR", "message");
		LocalDateTime after = LocalDateTime.now();

		// Then
		assertThat(exception.getTimestamp()).isAfterOrEqualTo(before);
		assertThat(exception.getTimestamp()).isBeforeOrEqualTo(after);
	}

	@Test
	void shouldHandleNullErrorCode() {
		// Given
		String message = "Test message";

		// When
		TestPetClinicException exception = new TestPetClinicException(null, message);

		// Then
		assertThat(exception.getErrorCode()).isNull();
		assertThat(exception.getMessage()).isEqualTo(message);
	}

	@Test
	void shouldHandleNullMessage() {
		// Given
		String errorCode = "ERROR";

		// When
		TestPetClinicException exception = new TestPetClinicException(errorCode, null);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo(errorCode);
		assertThat(exception.getMessage()).isNull();
	}

	/**
	 * Test implementation of PetClinicException for testing purposes.
	 */
	private static class TestPetClinicException extends PetClinicException {

		public TestPetClinicException(String errorCode, String message) {
			super(errorCode, message);
		}

		public TestPetClinicException(String errorCode, String message, Throwable cause) {
			super(errorCode, message, cause);
		}

	}

}
