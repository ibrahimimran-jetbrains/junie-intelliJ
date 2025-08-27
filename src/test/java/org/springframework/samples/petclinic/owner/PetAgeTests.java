package org.springframework.samples.petclinic.owner;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class PetAgeTests {

	@Test
	void ageIsZeroWhenBirthDateIsNull() {
		Pet p = new Pet();
		p.setBirthDate(null);
		assertThat(p.getAge()).isZero();
	}

	@Test
	void ageIsZeroWhenBirthDateInFuture() {
		Pet p = new Pet();
		p.setBirthDate(LocalDate.now().plusDays(10));
		assertThat(p.getAge()).isZero();
	}

	@Test
	void ageIsCalculatedInYears() {
		Pet p = new Pet();
		// 3 years ago exactly
		p.setBirthDate(LocalDate.now().minusYears(3));
		assertThat(p.getAge()).isEqualTo(3);
	}

	@Test
	void ageRoundsDownToFullYears() {
		Pet p = new Pet();
		// 2 years and 364 days ago -> still 2 until the day passes
		p.setBirthDate(LocalDate.now().minusYears(3).plusDays(1));
		assertThat(p.getAge()).isEqualTo(2);
	}

}
