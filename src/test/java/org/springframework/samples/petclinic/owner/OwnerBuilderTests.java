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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

/**
 * Test class for {@link Owner.Builder}
 *
 * @author Junie
 */
class OwnerBuilderTests {

	@Test
	void shouldCreateOwnerWithValidData() {
		Owner owner = Owner.builder()
			.firstName("John")
			.lastName("Doe")
			.address("123 Main Street")
			.city("Springfield")
			.telephone("1234567890")
			.build();

		assertThat(owner.getFirstName()).isEqualTo("John");
		assertThat(owner.getLastName()).isEqualTo("Doe");
		assertThat(owner.getAddress()).isEqualTo("123 Main Street");
		assertThat(owner.getCity()).isEqualTo("Springfield");
		assertThat(owner.getTelephone()).isEqualTo("1234567890");
		assertThat(owner.getId()).isNull();
		assertThat(owner.isNew()).isTrue();
	}

	@Test
	void shouldCreateOwnerWithId() {
		Owner owner = Owner.builder()
			.id(1)
			.firstName("Jane")
			.lastName("Smith")
			.address("456 Oak Avenue")
			.city("Metropolis")
			.telephone("9876543210")
			.build();

		assertThat(owner.getId()).isEqualTo(1);
		assertThat(owner.isNew()).isFalse();
		assertThat(owner.getFirstName()).isEqualTo("Jane");
		assertThat(owner.getLastName()).isEqualTo("Smith");
		assertThat(owner.getAddress()).isEqualTo("456 Oak Avenue");
		assertThat(owner.getCity()).isEqualTo("Metropolis");
		assertThat(owner.getTelephone()).isEqualTo("9876543210");
	}

	@Test
	void shouldThrowExceptionWhenFirstNameIsNull() {
		assertThatThrownBy(() -> Owner.builder()
			.lastName("Doe")
			.address("123 Main Street")
			.city("Springfield")
			.telephone("1234567890")
			.build()).isInstanceOf(IllegalArgumentException.class)
			.hasMessage("First name is required and cannot be blank");
	}

	@Test
	void shouldThrowExceptionWhenFirstNameIsEmpty() {
		assertThatThrownBy(() -> Owner.builder()
			.firstName("")
			.lastName("Doe")
			.address("123 Main Street")
			.city("Springfield")
			.telephone("1234567890")
			.build()).isInstanceOf(IllegalArgumentException.class)
			.hasMessage("First name is required and cannot be blank");
	}

	@Test
	void shouldThrowExceptionWhenFirstNameIsBlank() {
		assertThatThrownBy(() -> Owner.builder()
			.firstName("   ")
			.lastName("Doe")
			.address("123 Main Street")
			.city("Springfield")
			.telephone("1234567890")
			.build()).isInstanceOf(IllegalArgumentException.class)
			.hasMessage("First name is required and cannot be blank");
	}

	@Test
	void shouldThrowExceptionWhenLastNameIsNull() {
		assertThatThrownBy(() -> Owner.builder()
			.firstName("John")
			.address("123 Main Street")
			.city("Springfield")
			.telephone("1234567890")
			.build()).isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Last name is required and cannot be blank");
	}

	@Test
	void shouldThrowExceptionWhenAddressIsNull() {
		assertThatThrownBy(() -> Owner.builder()
			.firstName("John")
			.lastName("Doe")
			.city("Springfield")
			.telephone("1234567890")
			.build()).isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Address is required and cannot be blank");
	}

	@Test
	void shouldThrowExceptionWhenCityIsNull() {
		assertThatThrownBy(() -> Owner.builder()
			.firstName("John")
			.lastName("Doe")
			.address("123 Main Street")
			.telephone("1234567890")
			.build()).isInstanceOf(IllegalArgumentException.class).hasMessage("City is required and cannot be blank");
	}

	@Test
	void shouldThrowExceptionWhenTelephoneIsNull() {
		assertThatThrownBy(() -> Owner.builder()
			.firstName("John")
			.lastName("Doe")
			.address("123 Main Street")
			.city("Springfield")
			.build()).isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Telephone is required and cannot be blank");
	}

	@Test
	void shouldThrowExceptionWhenTelephoneIsInvalidFormat() {
		assertThatThrownBy(() -> Owner.builder()
			.firstName("John")
			.lastName("Doe")
			.address("123 Main Street")
			.city("Springfield")
			.telephone("123456789") // 9 digits instead of 10
			.build()).isInstanceOf(IllegalArgumentException.class).hasMessage("Telephone must be exactly 10 digits");
	}

	@Test
	void shouldThrowExceptionWhenTelephoneContainsNonDigits() {
		assertThatThrownBy(() -> Owner.builder()
			.firstName("John")
			.lastName("Doe")
			.address("123 Main Street")
			.city("Springfield")
			.telephone("123-456-789") // Contains dashes
			.build()).isInstanceOf(IllegalArgumentException.class).hasMessage("Telephone must be exactly 10 digits");
	}

	@Test
	void shouldThrowExceptionWhenTelephoneHasTooManyDigits() {
		assertThatThrownBy(() -> Owner.builder()
			.firstName("John")
			.lastName("Doe")
			.address("123 Main Street")
			.city("Springfield")
			.telephone("12345678901") // 11 digits
			.build()).isInstanceOf(IllegalArgumentException.class).hasMessage("Telephone must be exactly 10 digits");
	}

	@Test
	void shouldSupportMethodChaining() {
		Owner owner = Owner.builder()
			.firstName("Alice")
			.lastName("Johnson")
			.address("789 Pine Street")
			.city("Gotham")
			.telephone("5555555555")
			.id(42)
			.build();

		assertThat(owner.getFirstName()).isEqualTo("Alice");
		assertThat(owner.getLastName()).isEqualTo("Johnson");
		assertThat(owner.getAddress()).isEqualTo("789 Pine Street");
		assertThat(owner.getCity()).isEqualTo("Gotham");
		assertThat(owner.getTelephone()).isEqualTo("5555555555");
		assertThat(owner.getId()).isEqualTo(42);
	}

}
