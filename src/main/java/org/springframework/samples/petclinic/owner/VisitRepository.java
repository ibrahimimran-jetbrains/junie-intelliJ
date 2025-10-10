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

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

/**
 * Repository class for <code>Visit</code> domain objects All method names are compliant
 * with Spring Data naming conventions so this interface can easily be extended for Spring
 * Data. See:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Wick Dynex
 */
public interface VisitRepository extends Repository<Visit, Integer> {

	/**
	 * Retrieve all <code>Visit</code>s for a given <code>Pet</code> with sorting.
	 * @param petId the ID of the <code>Pet</code>
	 * @param sort the sorting specification
	 * @return a <code>List</code> of matching <code>Visit</code>s (or an empty
	 * <code>List</code> if none found)
	 */
	@Query("SELECT v FROM Visit v WHERE v IN (SELECT visit FROM Pet p JOIN p.visits visit WHERE p.id = :petId)")
	List<Visit> findByPetId(@Param("petId") Integer petId, Sort sort);

}
