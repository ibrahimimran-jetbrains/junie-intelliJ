/*
 * Tests for Pet.getAge()
 */
package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisabledInNativeImage
public class PetAgeTests {

	@Test
	void ageIsZeroWhenBirthDateIsNull() {
		Pet p = new Pet();
		p.setBirthDate(null);
		assertEquals(0, p.getAge());
	}

	@Test
	void ageIsZeroWhenBirthDateInFuture() {
		Pet p = new Pet();
		p.setBirthDate(LocalDate.now().plusDays(1));
		assertEquals(0, p.getAge());
	}

	@Test
	void ageExactYearsOnBirthday() {
		Pet p = new Pet();
		LocalDate today = LocalDate.now();
		p.setBirthDate(today.minusYears(5));
		assertEquals(5, p.getAge());
	}

	@Test
	void ageBeforeBirthdayThisYear() {
		Pet p = new Pet();
		LocalDate today = LocalDate.now();
		LocalDate birth = today.minusYears(5).plusDays(1); // birthday is tomorrow
		p.setBirthDate(birth);
		assertEquals(4, p.getAge());
	}

	@Test
	void ageAfterBirthdayThisYear() {
		Pet p = new Pet();
		LocalDate today = LocalDate.now();
		LocalDate birth = today.minusYears(5).minusDays(1); // birthday was yesterday
		p.setBirthDate(birth);
		assertEquals(5, p.getAge());
	}

}
