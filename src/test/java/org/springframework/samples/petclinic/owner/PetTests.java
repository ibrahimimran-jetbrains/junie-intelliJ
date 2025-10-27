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

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the Pet model.
 *
 * @author Wick Dynex
 */
class PetTests {

	@Test
	void testGetAgeWithValidBirthDate() {
		Pet pet = new Pet();
		// Set birth date to 5 years ago
		pet.setBirthDate(LocalDate.now().minusYears(5));

		assertThat(pet.getAge()).isEqualTo(5);
	}

	@Test
	void testGetAgeWithBirthDateOneYearAgo() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now().minusYears(1));

		assertThat(pet.getAge()).isEqualTo(1);
	}

	@Test
	void testGetAgeWithBirthDateLessThanOneYearAgo() {
		Pet pet = new Pet();
		// Set birth date to 6 months ago
		pet.setBirthDate(LocalDate.now().minusMonths(6));

		assertThat(pet.getAge()).isEqualTo(0);
	}

	@Test
	void testGetAgeWithNullBirthDate() {
		Pet pet = new Pet();
		pet.setBirthDate(null);

		assertThat(pet.getAge()).isEqualTo(0);
	}

	@Test
	void testGetAgeWithBirthDateToday() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now());

		assertThat(pet.getAge()).isEqualTo(0);
	}

	@Test
	void testGetAgeWithOldPet() {
		Pet pet = new Pet();
		// Set birth date to 20 years ago
		pet.setBirthDate(LocalDate.now().minusYears(20));

		assertThat(pet.getAge()).isEqualTo(20);
	}

}
