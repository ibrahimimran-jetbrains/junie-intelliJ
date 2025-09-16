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
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * REST controller for managing pet clinic owners. Provides CRUD operations and search
 * functionality for owners.
 *
 * @author Junie AI Assistant
 */
@RestController
@RequestMapping("/api/owners")
@Validated
@Tag(name = "Owners", description = "Pet clinic owner management operations")
public class OwnerRestController {

	private final OwnerRepository owners;

	public OwnerRestController(OwnerRepository owners) {
		this.owners = owners;
	}

	@Operation(summary = "Create a new owner",
			description = "Creates a new pet clinic owner with the provided information. All required fields must be provided.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Owner information to create", required = true,
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = Owner.class),
							examples = @ExampleObject(name = "New Owner Example",
									summary = "Example of creating a new owner", value = """
											{
											  "firstName": "John",
											  "lastName": "Doe",
											  "address": "123 Main Street",
											  "city": "Springfield",
											  "telephone": "555-123-4567"
											}
											"""))))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Owner created successfully",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = Owner.class),
							examples = @ExampleObject(name = "Created Owner Response",
									summary = "Successfully created owner response", value = """
											{
											  "id": 1,
											  "firstName": "John",
											  "lastName": "Doe",
											  "address": "123 Main Street",
											  "city": "Springfield",
											  "telephone": "555-123-4567",
											  "pets": []
											}
											"""))),
			@ApiResponse(responseCode = "400", description = "Invalid owner data provided",
					content = @Content(mediaType = "application/json",
							examples = @ExampleObject(name = "Validation Error",
									summary = "Example validation error response", value = """
											{
											  "error": "Validation failed",
											  "details": [
											    {
											      "field": "firstName",
											      "message": "First name is required"
											    }
											  ]
											}
											"""))) })
	@PostMapping
	public ResponseEntity<Owner> createOwner(@Valid @RequestBody Owner owner) {
		Owner savedOwner = this.owners.save(owner);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedOwner);
	}

	@Operation(summary = "Get owner by ID",
			description = "Retrieves a specific owner by their unique identifier, including their associated pets.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Owner found and returned successfully",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = Owner.class),
							examples = @ExampleObject(name = "Owner Response", summary = "Example owner with pets",
									value = """
											{
											  "id": 1,
											  "firstName": "John",
											  "lastName": "Doe",
											  "address": "123 Main Street",
											  "city": "Springfield",
											  "telephone": "555-123-4567",
											  "pets": [
											    {
											      "id": 1,
											      "name": "Buddy",
											      "birthDate": "2020-05-15",
											      "type": {
											        "id": 1,
											        "name": "Dog"
											      }
											    }
											  ]
											}
											"""))),
			@ApiResponse(responseCode = "404", description = "Owner not found",
					content = @Content(mediaType = "application/json",
							examples = @ExampleObject(name = "Not Found Error", summary = "Owner not found response",
									value = """
											{
											  "error": "Owner not found",
											  "message": "Owner with ID 999 does not exist"
											}
											"""))) })
	@GetMapping("/{ownerId}")
	public ResponseEntity<Owner> getOwner(@Parameter(description = "Unique identifier of the owner",
			example = "1") @PathVariable("ownerId") @Min(1) Integer ownerId) {

		Optional<Owner> owner = this.owners.findById(ownerId);
		if (owner.isPresent()) {
			return ResponseEntity.ok(owner.get());
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Update an existing owner",
			description = "Updates an existing owner with new information. The owner ID in the path must match the owner being updated.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Owner updated successfully",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = Owner.class),
							examples = @ExampleObject(name = "Updated Owner Response",
									summary = "Successfully updated owner", value = """
											{
											  "id": 1,
											  "firstName": "John",
											  "lastName": "Smith",
											  "address": "456 Oak Avenue",
											  "city": "Springfield",
											  "telephone": "555-987-6543",
											  "pets": []
											}
											"""))),
			@ApiResponse(responseCode = "400", description = "Invalid owner data or ID mismatch",
					content = @Content(mediaType = "application/json",
							examples = @ExampleObject(name = "ID Mismatch Error", summary = "Owner ID mismatch error",
									value = """
											{
											  "error": "ID mismatch",
											  "message": "The owner ID in the URL does not match the owner ID in the request body"
											}
											"""))),
			@ApiResponse(responseCode = "404", description = "Owner not found") })
	@PutMapping("/{ownerId}")
	public ResponseEntity<Owner> updateOwner(
			@Parameter(description = "Unique identifier of the owner to update",
					example = "1") @PathVariable("ownerId") @Min(1) Integer ownerId,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated owner information",
					required = true,
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = Owner.class),
							examples = @ExampleObject(name = "Update Owner Example",
									summary = "Example of updating an owner", value = """
											{
											  "id": 1,
											  "firstName": "John",
											  "lastName": "Smith",
											  "address": "456 Oak Avenue",
											  "city": "Springfield",
											  "telephone": "555-987-6543"
											}
											"""))) @Valid @RequestBody Owner owner) {

		if (!this.owners.existsById(ownerId)) {
			return ResponseEntity.notFound().build();
		}

		if (owner.getId() != null && !owner.getId().equals(ownerId)) {
			return ResponseEntity.badRequest().build();
		}

		owner.setId(ownerId);
		Owner savedOwner = this.owners.save(owner);
		return ResponseEntity.ok(savedOwner);
	}

	@Operation(summary = "Delete an owner",
			description = "Removes an owner from the system. This operation cannot be undone.")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Owner deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Owner not found") })
	@DeleteMapping("/{ownerId}")
	public ResponseEntity<Void> deleteOwner(@Parameter(description = "Unique identifier of the owner to delete",
			example = "1") @PathVariable("ownerId") @Min(1) Integer ownerId) {

		if (!this.owners.existsById(ownerId)) {
			return ResponseEntity.notFound().build();
		}

		this.owners.deleteById(ownerId);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Search owners by last name",
			description = "Searches for owners whose last name starts with the specified text. Returns paginated results. If no lastName is provided, returns all owners.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Search completed successfully",
			content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = OwnerSearchResponse.class),
					examples = @ExampleObject(name = "Search Results",
							summary = "Example search results with pagination", value = """
									{
									  "content": [
									    {
									      "id": 1,
									      "firstName": "John",
									      "lastName": "Doe",
									      "address": "123 Main Street",
									      "city": "Springfield",
									      "telephone": "555-123-4567",
									      "pets": []
									    }
									  ],
									  "pageable": {
									    "page": 0,
									    "size": 5,
									    "sort": []
									  },
									  "totalPages": 1,
									  "totalElements": 1,
									  "first": true,
									  "last": true,
									  "numberOfElements": 1
									}
									"""))) })
	@GetMapping
	public ResponseEntity<Page<Owner>> searchOwners(
			@Parameter(description = "Last name to search for (partial match supported)",
					example = "Doe") @RequestParam(value = "lastName", defaultValue = "") String lastName,

			@Parameter(description = "Page number (0-based)", example = "0") @RequestParam(value = "page",
					defaultValue = "0") @Min(0) Integer page,

			@Parameter(description = "Number of results per page", example = "5") @RequestParam(value = "size",
					defaultValue = "5") @Min(1) Integer size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<Owner> ownersResults = this.owners.findByLastNameStartingWith(lastName, pageable);

		return ResponseEntity.ok(ownersResults);
	}

	@Operation(summary = "Get all owners", description = "Retrieves all owners in the system with pagination support.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Owners retrieved successfully",
			content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = OwnerSearchResponse.class),
					examples = @ExampleObject(name = "All Owners Response",
							summary = "Example response with all owners", value = """
									{
									  "content": [
									    {
									      "id": 1,
									      "firstName": "John",
									      "lastName": "Doe",
									      "address": "123 Main Street",
									      "city": "Springfield",
									      "telephone": "555-123-4567",
									      "pets": []
									    },
									    {
									      "id": 2,
									      "firstName": "Jane",
									      "lastName": "Smith",
									      "address": "789 Elm Street",
									      "city": "Springfield",
									      "telephone": "555-456-7890",
									      "pets": []
									    }
									  ],
									  "pageable": {
									    "page": 0,
									    "size": 5,
									    "sort": []
									  },
									  "totalPages": 1,
									  "totalElements": 2,
									  "first": true,
									  "last": true,
									  "numberOfElements": 2
									}
									"""))) })
	@GetMapping("/all")
	public ResponseEntity<Page<Owner>> getAllOwners(
			@Parameter(description = "Page number (0-based)", example = "0") @RequestParam(value = "page",
					defaultValue = "0") @Min(0) Integer page,

			@Parameter(description = "Number of results per page", example = "10") @RequestParam(value = "size",
					defaultValue = "10") @Min(1) Integer size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<Owner> allOwners = this.owners.findAll(pageable);

		return ResponseEntity.ok(allOwners);
	}

	/**
	 * Schema class for OpenAPI documentation of paginated search responses
	 */
	@Schema(description = "Paginated response for owner search operations")
	public static class OwnerSearchResponse {

		@Schema(description = "List of owners in the current page")
		private List<Owner> content;

		@Schema(description = "Pagination information")
		private Object pageable;

		@Schema(description = "Total number of pages", example = "5")
		private int totalPages;

		@Schema(description = "Total number of elements", example = "42")
		private long totalElements;

		@Schema(description = "Whether this is the first page", example = "true")
		private boolean first;

		@Schema(description = "Whether this is the last page", example = "false")
		private boolean last;

		@Schema(description = "Number of elements in current page", example = "10")
		private int numberOfElements;

		// Getters and setters for schema documentation only
		public List<Owner> getContent() {
			return content;
		}

		public void setContent(List<Owner> content) {
			this.content = content;
		}

		public Object getPageable() {
			return pageable;
		}

		public void setPageable(Object pageable) {
			this.pageable = pageable;
		}

		public int getTotalPages() {
			return totalPages;
		}

		public void setTotalPages(int totalPages) {
			this.totalPages = totalPages;
		}

		public long getTotalElements() {
			return totalElements;
		}

		public void setTotalElements(long totalElements) {
			this.totalElements = totalElements;
		}

		public boolean isFirst() {
			return first;
		}

		public void setFirst(boolean first) {
			this.first = first;
		}

		public boolean isLast() {
			return last;
		}

		public void setLast(boolean last) {
			this.last = last;
		}

		public int getNumberOfElements() {
			return numberOfElements;
		}

		public void setNumberOfElements(int numberOfElements) {
			this.numberOfElements = numberOfElements;
		}

	}

}
