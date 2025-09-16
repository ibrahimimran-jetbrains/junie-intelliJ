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
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for Owner class, specifically focusing on the getPet(String name) method.
 *
 * @author Generated Tests
 */
class OwnerTest {

	private Owner owner;

	private Pet pet1;

	private Pet pet2;

	private Pet pet3;

	@BeforeEach
	void setUp() {
		owner = Owner.builder()
			.firstName("John")
			.lastName("Doe")
			.address("123 Main St")
			.city("Anytown")
			.telephone("1234567890")
			.build();

		// Create test pets with different names
		pet1 = new Pet();
		pet1.setName("Fluffy");

		pet2 = new Pet();
		pet2.setName("Buddy");

		pet3 = new Pet();
		pet3.setName("Max");

		// Add pets to owner
		owner.addPet(pet1);
		owner.addPet(pet2);
		owner.addPet(pet3);
	}

	@Test
	void getPet_WithValidName_ShouldReturnCorrectPet() {
		// Test finding first pet
		Pet foundPet = owner.getPet("Fluffy");
		assertThat(foundPet).isNotNull();
		assertThat(foundPet.getName()).isEqualTo("Fluffy");
		assertThat(foundPet).isSameAs(pet1);

		// Test finding second pet
		foundPet = owner.getPet("Buddy");
		assertThat(foundPet).isNotNull();
		assertThat(foundPet.getName()).isEqualTo("Buddy");
		assertThat(foundPet).isSameAs(pet2);

		// Test finding third pet
		foundPet = owner.getPet("Max");
		assertThat(foundPet).isNotNull();
		assertThat(foundPet.getName()).isEqualTo("Max");
		assertThat(foundPet).isSameAs(pet3);
	}

	@Test
	void getPet_WithCaseInsensitiveName_ShouldReturnCorrectPet() {
		// Test case insensitive matching
		Pet foundPet = owner.getPet("FLUFFY");
		assertThat(foundPet).isNotNull();
		assertThat(foundPet.getName()).isEqualTo("Fluffy");
		assertThat(foundPet).isSameAs(pet1);

		foundPet = owner.getPet("buddy");
		assertThat(foundPet).isNotNull();
		assertThat(foundPet.getName()).isEqualTo("Buddy");
		assertThat(foundPet).isSameAs(pet2);

		foundPet = owner.getPet("mAx");
		assertThat(foundPet).isNotNull();
		assertThat(foundPet.getName()).isEqualTo("Max");
		assertThat(foundPet).isSameAs(pet3);
	}

	@Test
	void getPet_WithNullName_ShouldReturnNull() {
		Pet foundPet = owner.getPet((String) null);
		assertThat(foundPet).isNull();
	}

	@Test
	void getPet_WithNonExistentName_ShouldReturnNull() {
		Pet foundPet = owner.getPet("NonExistentPet");
		assertThat(foundPet).isNull();

		foundPet = owner.getPet("Charlie");
		assertThat(foundPet).isNull();

		foundPet = owner.getPet("Rex");
		assertThat(foundPet).isNull();
	}

	@Test
	void getPet_WithEmptyStringName_ShouldReturnNull() {
		Pet foundPet = owner.getPet("");
		assertThat(foundPet).isNull();
	}

	@Test
	void getPet_WithWhitespaceOnlyName_ShouldReturnNull() {
		Pet foundPet = owner.getPet("   ");
		assertThat(foundPet).isNull();

		foundPet = owner.getPet("\t");
		assertThat(foundPet).isNull();

		foundPet = owner.getPet("\n");
		assertThat(foundPet).isNull();
	}

	@Test
	void getPet_WithOwnerHavingNoPets_ShouldReturnNull() {
		Owner emptyOwner = Owner.builder()
			.firstName("Jane")
			.lastName("Smith")
			.address("456 Oak Ave")
			.city("Somewhere")
			.telephone("9876543210")
			.build();

		Pet foundPet = emptyOwner.getPet("AnyName");
		assertThat(foundPet).isNull();

		foundPet = emptyOwner.getPet((String) null);
		assertThat(foundPet).isNull();

		foundPet = emptyOwner.getPet("");
		assertThat(foundPet).isNull();
	}

	@Test
	void getPet_WithPetHavingNullName_ShouldNotMatchAnySearch() {
		Pet petWithNullName = new Pet();
		petWithNullName.setName(null);
		owner.addPet(petWithNullName);

		// Searching with null should still return null (no match)
		Pet foundPet = owner.getPet((String) null);
		assertThat(foundPet).isNull();

		// Searching with any string should not find the pet with null name
		foundPet = owner.getPet("AnyName");
		assertThat(foundPet).isNull();
	}

	@Test
	void getPet_WithDuplicateNames_ShouldReturnFirstMatch() {
		// Add another pet with the same name as pet1
		Pet duplicatePet = new Pet();
		duplicatePet.setName("Fluffy");
		owner.addPet(duplicatePet);

		// Should return the first pet added with that name
		Pet foundPet = owner.getPet("Fluffy");
		assertThat(foundPet).isNotNull();
		assertThat(foundPet.getName()).isEqualTo("Fluffy");
		assertThat(foundPet).isSameAs(pet1); // Should be the first one added
	}

	@Test
	void getPet_WithSpecialCharactersInName_ShouldWorkCorrectly() {
		Pet specialPet = new Pet();
		specialPet.setName("Mr. Whiskers-2!");
		owner.addPet(specialPet);

		Pet foundPet = owner.getPet("Mr. Whiskers-2!");
		assertThat(foundPet).isNotNull();
		assertThat(foundPet.getName()).isEqualTo("Mr. Whiskers-2!");
		assertThat(foundPet).isSameAs(specialPet);

		// Case insensitive should work with special characters too
		foundPet = owner.getPet("mr. whiskers-2!");
		assertThat(foundPet).isNotNull();
		assertThat(foundPet).isSameAs(specialPet);
	}

}
