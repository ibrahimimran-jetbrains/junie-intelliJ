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

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Integration tests for the {@link VisitRepository}.
 *
 * @author Wick Dynex
 */
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class VisitRepositoryTests {

	@Autowired
	private VisitRepository visits;

	@Test
	void shouldFindVisitsByPetIdWithAscendingSort() {
		// Given: Pet with id=7 has visits in the database
		Sort sort = Sort.by("date").ascending();

		// When: Fetching visits with ascending sort
		List<Visit> visitList = this.visits.findByPetId(7, sort);

		// Then: Visits should be sorted by date in ascending order
		assertThat(visitList).isNotEmpty();
		for (int i = 0; i < visitList.size() - 1; i++) {
			LocalDate currentDate = visitList.get(i).getDate();
			LocalDate nextDate = visitList.get(i + 1).getDate();
			assertThat(currentDate).isBeforeOrEqualTo(nextDate);
		}
	}

	@Test
	void shouldFindVisitsByPetIdWithDescendingSort() {
		// Given: Pet with id=7 has visits in the database
		Sort sort = Sort.by("date").descending();

		// When: Fetching visits with descending sort
		List<Visit> visitList = this.visits.findByPetId(7, sort);

		// Then: Visits should be sorted by date in descending order
		assertThat(visitList).isNotEmpty();
		for (int i = 0; i < visitList.size() - 1; i++) {
			LocalDate currentDate = visitList.get(i).getDate();
			LocalDate nextDate = visitList.get(i + 1).getDate();
			assertThat(currentDate).isAfterOrEqualTo(nextDate);
		}
	}

	@Test
	void shouldReturnEmptyListForNonExistentPet() {
		// Given: A pet ID that doesn't exist
		Sort sort = Sort.by("date").ascending();

		// When: Fetching visits for non-existent pet
		List<Visit> visitList = this.visits.findByPetId(999, sort);

		// Then: Should return empty list
		assertThat(visitList).isEmpty();
	}

}
