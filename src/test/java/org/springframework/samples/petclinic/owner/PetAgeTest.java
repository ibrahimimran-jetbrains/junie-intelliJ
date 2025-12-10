package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for Pet age calculation functionality.
 */
class PetAgeTest {

	@Test
	void testGetAge_WithValidBirthDate() {
		Pet pet = new Pet();
		// Set birth date to 3 years ago
		pet.setBirthDate(LocalDate.now().minusYears(3));
		assertEquals(3, pet.getAge());
	}

	@Test
	void testGetAge_WithBirthDateOneYearAgo() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now().minusYears(1));
		assertEquals(1, pet.getAge());
	}

	@Test
	void testGetAge_WithBirthDateLessThanOneYearAgo() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now().minusMonths(6));
		assertEquals(0, pet.getAge());
	}

	@Test
	void testGetAge_WithNullBirthDate() {
		Pet pet = new Pet();
		pet.setBirthDate(null);
		assertEquals(0, pet.getAge());
	}

	@Test
	void testGetAge_WithBirthDateToday() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.now());
		assertEquals(0, pet.getAge());
	}

}
