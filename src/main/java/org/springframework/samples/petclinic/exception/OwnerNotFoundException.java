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
package org.springframework.samples.petclinic.exception;

/**
 * Exception thrown when an owner is not found in the system. This exception is typically
 * used when attempting to retrieve, update, or delete an owner that does not exist in the
 * database.
 *
 * @author Junie AI
 */
public class OwnerNotFoundException extends PetClinicException {

	private static final String ERROR_CODE = "OWNER_NOT_FOUND";

	private final Integer ownerId;

	/**
	 * Constructs a new OwnerNotFoundException for the specified owner ID.
	 * @param ownerId the ID of the owner that was not found
	 */
	public OwnerNotFoundException(Integer ownerId) {
		super(ERROR_CODE, String.format(
				"Owner not found with id: %d. Please ensure the ID is correct and the owner exists in the database.",
				ownerId));
		this.ownerId = ownerId;
	}

	/**
	 * Constructs a new OwnerNotFoundException with a custom message.
	 * @param ownerId the ID of the owner that was not found
	 * @param message custom error message
	 */
	public OwnerNotFoundException(Integer ownerId, String message) {
		super(ERROR_CODE, message);
		this.ownerId = ownerId;
	}

	/**
	 * Constructs a new OwnerNotFoundException with a custom message and cause.
	 * @param ownerId the ID of the owner that was not found
	 * @param message custom error message
	 * @param cause the cause of this exception
	 */
	public OwnerNotFoundException(Integer ownerId, String message, Throwable cause) {
		super(ERROR_CODE, message, cause);
		this.ownerId = ownerId;
	}

	/**
	 * Returns the ID of the owner that was not found.
	 * @return the owner ID
	 */
	public Integer getOwnerId() {
		return ownerId;
	}

}
