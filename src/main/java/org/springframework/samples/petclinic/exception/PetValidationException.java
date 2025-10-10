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

import java.util.List;
import java.util.Map;

/**
 * Exception thrown when pet validation fails. This exception is used when business rules
 * or field validation for pet data fails during create or update operations.
 *
 * @author Junie AI
 */
public class PetValidationException extends PetClinicException {

	private static final String ERROR_CODE = "PET_VALIDATION_ERROR";

	private final Map<String, List<String>> fieldErrors;

	private final Integer petId;

	/**
	 * Constructs a new PetValidationException with a message.
	 * @param message the validation error message
	 */
	public PetValidationException(String message) {
		super(ERROR_CODE, message);
		this.fieldErrors = null;
		this.petId = null;
	}

	/**
	 * Constructs a new PetValidationException with a message and pet ID.
	 * @param message the validation error message
	 * @param petId the ID of the pet that failed validation
	 */
	public PetValidationException(String message, Integer petId) {
		super(ERROR_CODE, message);
		this.fieldErrors = null;
		this.petId = petId;
	}

	/**
	 * Constructs a new PetValidationException with a message and field errors.
	 * @param message the validation error message
	 * @param fieldErrors map of field names to their validation error messages
	 */
	public PetValidationException(String message, Map<String, List<String>> fieldErrors) {
		super(ERROR_CODE, message);
		this.fieldErrors = fieldErrors;
		this.petId = null;
	}

	/**
	 * Constructs a new PetValidationException with message, field errors, and pet ID.
	 * @param message the validation error message
	 * @param fieldErrors map of field names to their validation error messages
	 * @param petId the ID of the pet that failed validation
	 */
	public PetValidationException(String message, Map<String, List<String>> fieldErrors, Integer petId) {
		super(ERROR_CODE, message);
		this.fieldErrors = fieldErrors;
		this.petId = petId;
	}

	/**
	 * Constructs a new PetValidationException with a message and cause.
	 * @param message the validation error message
	 * @param cause the cause of this exception
	 */
	public PetValidationException(String message, Throwable cause) {
		super(ERROR_CODE, message, cause);
		this.fieldErrors = null;
		this.petId = null;
	}

	/**
	 * Returns the field validation errors if available.
	 * @return map of field names to their validation error messages, or null if not
	 * available
	 */
	public Map<String, List<String>> getFieldErrors() {
		return fieldErrors;
	}

	/**
	 * Returns the ID of the pet that failed validation.
	 * @return the pet ID, or null if not specified
	 */
	public Integer getPetId() {
		return petId;
	}

	/**
	 * Returns true if this exception contains field-specific validation errors.
	 * @return true if field errors are present, false otherwise
	 */
	public boolean hasFieldErrors() {
		return fieldErrors != null && !fieldErrors.isEmpty();
	}

}
