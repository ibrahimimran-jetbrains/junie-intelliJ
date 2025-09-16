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
 * Test class for Pet entity.
 *
 * @author Junie
 */
class PetTests {

	@Test
	void testGetAgeWithValidBirthDate() {
		Pet pet = new Pet();
		LocalDate birthDate = LocalDate.now().minusYears(5);
		pet.setBirthDate(birthDate);

		int age = pet.getAge();

		assertThat(age).isEqualTo(5);
	}

	@Test
	void testGetAgeWithBirthDateToday() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now());

		int age = pet.getAge();

		assertThat(age).isEqualTo(0);
	}

	@Test
	void testGetAgeWithNullBirthDate() {
		Pet pet = new Pet();
		pet.setBirthDate(null);

		int age = pet.getAge();

		assertThat(age).isEqualTo(0);
	}

	@Test
	void testGetAgeWithOldBirthDate() {
		Pet pet = new Pet();
		LocalDate birthDate = LocalDate.of(2000, 6, 15);
		pet.setBirthDate(birthDate);

		int age = pet.getAge();

		// Age should be calculated from birth date to current date
		int expectedAge = LocalDate.now().getYear() - 2000;
		if (LocalDate.now().getDayOfYear() < LocalDate.of(2000, 6, 15).getDayOfYear()) {
			expectedAge--;
		}

		assertThat(age).isEqualTo(expectedAge);
	}

}
