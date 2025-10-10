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
 * Unit tests for {@link BusinessRuleViolationException}.
 *
 * @author Junie AI
 */
class BusinessRuleViolationExceptionTest {

	@Test
	void shouldCreateExceptionWithMessage() {
		// Given
		String message = "Business rule violation occurred";

		// When
		BusinessRuleViolationException exception = new BusinessRuleViolationException(message);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("BUSINESS_RULE_VIOLATION");
		assertThat(exception.getMessage()).isEqualTo(message);
		assertThat(exception.getRuleCode()).isNull();
		assertThat(exception.getRelatedEntity()).isNull();
		assertThat(exception.hasRuleCode()).isFalse();
		assertThat(exception.getTimestamp()).isNotNull();
	}

	@Test
	void shouldCreateExceptionWithMessageAndRuleCode() {
		// Given
		String message = "Owner cannot have more than 5 pets";
		String ruleCode = "MAX_PETS_EXCEEDED";

		// When
		BusinessRuleViolationException exception = new BusinessRuleViolationException(message, ruleCode);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("BUSINESS_RULE_VIOLATION");
		assertThat(exception.getMessage()).isEqualTo(message);
		assertThat(exception.getRuleCode()).isEqualTo(ruleCode);
		assertThat(exception.getRelatedEntity()).isNull();
		assertThat(exception.hasRuleCode()).isTrue();
	}

	@Test
	void shouldCreateExceptionWithMessageRuleCodeAndRelatedEntity() {
		// Given
		String message = "Pet cannot be older than owner";
		String ruleCode = "PET_AGE_VALIDATION";
		Object relatedEntity = "Pet ID: 123, Owner ID: 456";

		// When
		BusinessRuleViolationException exception = new BusinessRuleViolationException(message, ruleCode, relatedEntity);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("BUSINESS_RULE_VIOLATION");
		assertThat(exception.getMessage()).isEqualTo(message);
		assertThat(exception.getRuleCode()).isEqualTo(ruleCode);
		assertThat(exception.getRelatedEntity()).isEqualTo(relatedEntity);
		assertThat(exception.hasRuleCode()).isTrue();
	}

	@Test
	void shouldCreateExceptionWithMessageAndCause() {
		// Given
		String message = "Business rule check failed";
		RuntimeException cause = new RuntimeException("External service unavailable");

		// When
		BusinessRuleViolationException exception = new BusinessRuleViolationException(message, cause);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("BUSINESS_RULE_VIOLATION");
		assertThat(exception.getMessage()).isEqualTo(message);
		assertThat(exception.getCause()).isEqualTo(cause);
		assertThat(exception.getRuleCode()).isNull();
		assertThat(exception.getRelatedEntity()).isNull();
		assertThat(exception.hasRuleCode()).isFalse();
	}

	@Test
	void shouldCreateExceptionWithMessageRuleCodeAndCause() {
		// Given
		String message = "Business rule validation failed";
		String ruleCode = "EXTERNAL_VALIDATION_FAILED";
		RuntimeException cause = new RuntimeException("Network timeout");

		// When
		BusinessRuleViolationException exception = new BusinessRuleViolationException(message, ruleCode, cause);

		// Then
		assertThat(exception.getErrorCode()).isEqualTo("BUSINESS_RULE_VIOLATION");
		assertThat(exception.getMessage()).isEqualTo(message);
		assertThat(exception.getRuleCode()).isEqualTo(ruleCode);
		assertThat(exception.getCause()).isEqualTo(cause);
		assertThat(exception.getRelatedEntity()).isNull();
		assertThat(exception.hasRuleCode()).isTrue();
	}

	@Test
	void shouldHandleNullRuleCode() {
		// Given
		String message = "Business rule violation";
		String ruleCode = null;

		// When
		BusinessRuleViolationException exception = new BusinessRuleViolationException(message, ruleCode);

		// Then
		assertThat(exception.getRuleCode()).isNull();
		assertThat(exception.hasRuleCode()).isFalse();
	}

	@Test
	void shouldHandleEmptyRuleCode() {
		// Given
		String message = "Business rule violation";
		String ruleCode = "";

		// When
		BusinessRuleViolationException exception = new BusinessRuleViolationException(message, ruleCode);

		// Then
		assertThat(exception.getRuleCode()).isEqualTo("");
		assertThat(exception.hasRuleCode()).isFalse();
	}

	@Test
	void shouldHandleWhitespaceOnlyRuleCode() {
		// Given
		String message = "Business rule violation";
		String ruleCode = "   ";

		// When
		BusinessRuleViolationException exception = new BusinessRuleViolationException(message, ruleCode);

		// Then
		assertThat(exception.getRuleCode()).isEqualTo("   ");
		assertThat(exception.hasRuleCode()).isFalse();
	}

	@Test
	void shouldHandleValidRuleCodeWithSpaces() {
		// Given
		String message = "Business rule violation";
		String ruleCode = " VALID_RULE_CODE ";

		// When
		BusinessRuleViolationException exception = new BusinessRuleViolationException(message, ruleCode);

		// Then
		assertThat(exception.getRuleCode()).isEqualTo(" VALID_RULE_CODE ");
		assertThat(exception.hasRuleCode()).isTrue();
	}

	@Test
	void shouldHandleNullRelatedEntity() {
		// Given
		String message = "Business rule violation";
		String ruleCode = "TEST_RULE";
		Object relatedEntity = null;

		// When
		BusinessRuleViolationException exception = new BusinessRuleViolationException(message, ruleCode, relatedEntity);

		// Then
		assertThat(exception.getRelatedEntity()).isNull();
		assertThat(exception.getRuleCode()).isEqualTo(ruleCode);
	}

	@Test
	void shouldHandleComplexRelatedEntity() {
		// Given
		String message = "Complex business rule violation";
		String ruleCode = "COMPLEX_RULE";
		Object relatedEntity = new TestEntity("test", 123);

		// When
		BusinessRuleViolationException exception = new BusinessRuleViolationException(message, ruleCode, relatedEntity);

		// Then
		assertThat(exception.getRelatedEntity()).isEqualTo(relatedEntity);
		assertThat(exception.getRelatedEntity()).isInstanceOf(TestEntity.class);
		TestEntity entity = (TestEntity) exception.getRelatedEntity();
		assertThat(entity.getName()).isEqualTo("test");
		assertThat(entity.getId()).isEqualTo(123);
	}

	@Test
	void shouldInheritFromPetClinicException() {
		// Given
		String message = "Test business rule violation";

		// When
		BusinessRuleViolationException exception = new BusinessRuleViolationException(message);

		// Then
		assertThat(exception).isInstanceOf(PetClinicException.class);
		assertThat(exception).isInstanceOf(RuntimeException.class);
	}

	/**
	 * Test entity class for testing related entity functionality.
	 */
	private static class TestEntity {

		private final String name;

		private final Integer id;

		public TestEntity(String name, Integer id) {
			this.name = name;
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public Integer getId() {
			return id;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null || getClass() != obj.getClass())
				return false;
			TestEntity that = (TestEntity) obj;
			return java.util.Objects.equals(name, that.name) && java.util.Objects.equals(id, that.id);
		}

		@Override
		public int hashCode() {
			return java.util.Objects.hash(name, id);
		}

	}

}
