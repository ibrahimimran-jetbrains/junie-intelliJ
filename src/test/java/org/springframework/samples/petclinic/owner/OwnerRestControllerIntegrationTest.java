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

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

/**
 * Integration tests for {@link OwnerRestController}. Tests all REST endpoints with actual
 * HTTP requests and database operations.
 *
 * @author Junie AI
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OwnerRestControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private OwnerRepository ownerRepository;

	private String getBaseUrl() {
		return "http://localhost:" + port + "/api/owners";
	}

	@Test
	void shouldCreateOwnerSuccessfully() {
		// Given
		Owner newOwner = new Owner();
		newOwner.setFirstName("John");
		newOwner.setLastName("Doe");
		newOwner.setAddress("123 Main Street");
		newOwner.setCity("Springfield");
		newOwner.setTelephone("5551234567");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Owner> request = new HttpEntity<>(newOwner, headers);

		// When
		ResponseEntity<Owner> response = restTemplate.postForEntity(getBaseUrl(), request, Owner.class);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isNotNull();
		assertThat(response.getBody().getFirstName()).isEqualTo("John");
		assertThat(response.getBody().getLastName()).isEqualTo("Doe");
		assertThat(response.getBody().getAddress()).isEqualTo("123 Main Street");
		assertThat(response.getBody().getCity()).isEqualTo("Springfield");
		assertThat(response.getBody().getTelephone()).isEqualTo("5551234567");

		// Verify owner was persisted
		Owner persistedOwner = ownerRepository.findById(response.getBody().getId()).orElse(null);
		assertThat(persistedOwner).isNotNull();
		assertThat(persistedOwner.getFirstName()).isEqualTo("John");
	}

	@Test
	void shouldReturnValidationErrorsForInvalidOwner() {
		// Given - Owner with invalid data (empty required fields)
		Owner invalidOwner = new Owner();
		// firstName is required but not set
		// lastName is required but not set

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Owner> request = new HttpEntity<>(invalidOwner, headers);

		// When
		ResponseEntity<Map<String, Object>> response = restTemplate.exchange(getBaseUrl(), HttpMethod.POST, request,
				new ParameterizedTypeReference<Map<String, Object>>() {
				});

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void shouldGetOwnerById() {
		// Given - Create an owner first
		Owner owner = new Owner();
		owner.setFirstName("Jane");
		owner.setLastName("Smith");
		owner.setAddress("789 Elm Street");
		owner.setCity("Springfield");
		owner.setTelephone("5554567890");
		Owner savedOwner = ownerRepository.save(owner);

		// When
		ResponseEntity<Owner> response = restTemplate.getForEntity(getBaseUrl() + "/" + savedOwner.getId(),
				Owner.class);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isEqualTo(savedOwner.getId());
		assertThat(response.getBody().getFirstName()).isEqualTo("Jane");
		assertThat(response.getBody().getLastName()).isEqualTo("Smith");
	}

	@Test
	void shouldReturnNotFoundForNonExistentOwner() {
		// When
		ResponseEntity<Map<String, Object>> response = restTemplate.exchange(getBaseUrl() + "/99999", HttpMethod.GET,
				null, new ParameterizedTypeReference<Map<String, Object>>() {
				});

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void shouldUpdateOwnerSuccessfully() {
		// Given - Create an owner first
		Owner owner = new Owner();
		owner.setFirstName("Bob");
		owner.setLastName("Johnson");
		owner.setAddress("456 Oak Avenue");
		owner.setCity("Springfield");
		owner.setTelephone("5557890123");
		Owner savedOwner = ownerRepository.save(owner);

		// Update the owner
		savedOwner.setFirstName("Robert");
		savedOwner.setAddress("789 Pine Street");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Owner> request = new HttpEntity<>(savedOwner, headers);

		// When
		ResponseEntity<Owner> response = restTemplate.exchange(getBaseUrl() + "/" + savedOwner.getId(), HttpMethod.PUT,
				request, Owner.class);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getFirstName()).isEqualTo("Robert");
		assertThat(response.getBody().getAddress()).isEqualTo("789 Pine Street");

		// Verify update was persisted
		Owner updatedOwner = ownerRepository.findById(savedOwner.getId()).orElse(null);
		assertThat(updatedOwner).isNotNull();
		assertThat(updatedOwner.getFirstName()).isEqualTo("Robert");
		assertThat(updatedOwner.getAddress()).isEqualTo("789 Pine Street");
	}

	@Test
	void shouldReturnConflictForIdMismatchOnUpdate() {
		// Given - Create an owner first
		Owner owner = new Owner();
		owner.setFirstName("Alice");
		owner.setLastName("Brown");
		owner.setAddress("321 Maple Street");
		owner.setCity("Springfield");
		owner.setTelephone("5553216543");
		Owner savedOwner = ownerRepository.save(owner);

		// Try to update with mismatched ID
		savedOwner.setId(999);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Owner> request = new HttpEntity<>(savedOwner, headers);

		// When
		ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
				getBaseUrl() + "/" + (savedOwner.getId() - 998), // Different ID in URL
				HttpMethod.PUT, request, new ParameterizedTypeReference<Map<String, Object>>() {
				});

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void shouldDeleteOwnerSuccessfully() {
		// Given - Create an owner first
		Owner owner = new Owner();
		owner.setFirstName("Charlie");
		owner.setLastName("Wilson");
		owner.setAddress("654 Cedar Lane");
		owner.setCity("Springfield");
		owner.setTelephone("5556543210");
		Owner savedOwner = ownerRepository.save(owner);

		// When
		ResponseEntity<Void> response = restTemplate.exchange(getBaseUrl() + "/" + savedOwner.getId(),
				HttpMethod.DELETE, null, Void.class);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

		// Verify owner was deleted
		assertThat(ownerRepository.findById(savedOwner.getId())).isEmpty();
	}

	@Test
	void shouldReturnNotFoundWhenDeletingNonExistentOwner() {
		// When
		ResponseEntity<Map<String, Object>> response = restTemplate.exchange(getBaseUrl() + "/99999", HttpMethod.DELETE,
				null, new ParameterizedTypeReference<Map<String, Object>>() {
				});

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void shouldSearchOwnersByLastName() {
		// Given - Create multiple owners
		Owner owner1 = new Owner();
		owner1.setFirstName("John");
		owner1.setLastName("Smith");
		owner1.setAddress("123 Main Street");
		owner1.setCity("Springfield");
		owner1.setTelephone("5550010001");
		ownerRepository.save(owner1);

		Owner owner2 = new Owner();
		owner2.setFirstName("Jane");
		owner2.setLastName("Smith");
		owner2.setAddress("456 Oak Avenue");
		owner2.setCity("Springfield");
		owner2.setTelephone("5550020002");
		ownerRepository.save(owner2);

		Owner owner3 = new Owner();
		owner3.setFirstName("Bob");
		owner3.setLastName("Johnson");
		owner3.setAddress("789 Pine Street");
		owner3.setCity("Springfield");
		owner3.setTelephone("5550030003");
		ownerRepository.save(owner3);

		// When - Search for owners with lastName starting with "Smith"
		ResponseEntity<String> response = restTemplate.exchange(getBaseUrl() + "?lastName=Smith&page=0&size=10",
				HttpMethod.GET, null, String.class);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).contains("\"content\"");
		assertThat(response.getBody()).contains("\"lastName\":\"Smith\"");
		assertThat(response.getBody()).contains("John");
		assertThat(response.getBody()).contains("Jane");
	}

	@Test
	void shouldReturnAllOwnersWhenSearchingWithoutLastName() {
		// Given - Create multiple owners
		Owner owner1 = new Owner();
		owner1.setFirstName("Test1");
		owner1.setLastName("Owner1");
		owner1.setAddress("Address1");
		owner1.setCity("City1");
		owner1.setTelephone("5551111111");
		ownerRepository.save(owner1);

		Owner owner2 = new Owner();
		owner2.setFirstName("Test2");
		owner2.setLastName("Owner2");
		owner2.setAddress("Address2");
		owner2.setCity("City2");
		owner2.setTelephone("5552222222");
		ownerRepository.save(owner2);

		// When - Search without lastName parameter
		ResponseEntity<String> response = restTemplate.exchange(getBaseUrl() + "?page=0&size=10", HttpMethod.GET, null,
				String.class);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).contains("\"content\"");
		assertThat(response.getBody()).contains("\"totalElements\":");
		assertThat(response.getBody()).contains("\"firstName\":");
	}

	@Test
	void shouldGetAllOwnersWithPagination() {
		// Given - Create multiple owners to test pagination
		for (int i = 1; i <= 15; i++) {
			Owner owner = new Owner();
			owner.setFirstName("Owner" + i);
			owner.setLastName("Test" + i);
			owner.setAddress("Address " + i);
			owner.setCity("City " + i);
			owner.setTelephone("555" + String.format("%03d", i) + String.format("%04d", i));
			ownerRepository.save(owner);
		}

		// When - Get first page with size 5
		ResponseEntity<String> response = restTemplate.exchange(getBaseUrl() + "/all?page=0&size=5", HttpMethod.GET,
				null, String.class);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).contains("\"content\"");
		assertThat(response.getBody()).contains("\"size\":5");
		assertThat(response.getBody()).contains("\"totalElements\":");
		assertThat(response.getBody()).contains("\"first\":true");
		assertThat(response.getBody()).contains("\"firstName\":");
	}

	@Test
	void shouldHandlePaginationParametersCorrectly() {
		// Given - Create a few owners
		for (int i = 1; i <= 3; i++) {
			Owner owner = new Owner();
			owner.setFirstName("Page" + i);
			owner.setLastName("Test" + i);
			owner.setAddress("Address " + i);
			owner.setCity("City " + i);
			owner.setTelephone("555724" + String.format("%04d", i));
			ownerRepository.save(owner);
		}

		// When - Test with different page parameters
		ResponseEntity<String> response = restTemplate.exchange(getBaseUrl() + "?page=0&size=2", HttpMethod.GET, null,
				String.class);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).contains("\"content\"");
		assertThat(response.getBody()).contains("\"size\":2");
		assertThat(response.getBody()).contains("\"number\":0");
	}

}
