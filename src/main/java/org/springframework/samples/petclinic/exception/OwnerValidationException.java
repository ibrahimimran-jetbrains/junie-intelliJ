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
 * Exception thrown when owner validation fails. This exception is used when business
 * rules or field validation for owner data fails during create or update operations.
 *
 * @author Junie AI
 */
public class OwnerValidationException extends PetClinicException {

	private static final String ERROR_CODE = "OWNER_VALIDATION_ERROR";

	private final Map<String, List<String>> fieldErrors;

	/**
	 * Constructs a new OwnerValidationException with a message.
	 * @param message the validation error message
	 */
	public OwnerValidationException(String message) {
		super(ERROR_CODE, message);
		this.fieldErrors = null;
	}

	/**
	 * Constructs a new OwnerValidationException with a message and field errors.
	 * @param message the validation error message
	 * @param fieldErrors map of field names to their validation error messages
	 */
	public OwnerValidationException(String message, Map<String, List<String>> fieldErrors) {
		super(ERROR_CODE, message);
		this.fieldErrors = fieldErrors;
	}

	/**
	 * Constructs a new OwnerValidationException with a message and cause.
	 * @param message the validation error message
	 * @param cause the cause of this exception
	 */
	public OwnerValidationException(String message, Throwable cause) {
		super(ERROR_CODE, message, cause);
		this.fieldErrors = null;
	}

	/**
	 * Constructs a new OwnerValidationException with message, field errors, and cause.
	 * @param message the validation error message
	 * @param fieldErrors map of field names to their validation error messages
	 * @param cause the cause of this exception
	 */
	public OwnerValidationException(String message, Map<String, List<String>> fieldErrors, Throwable cause) {
		super(ERROR_CODE, message, cause);
		this.fieldErrors = fieldErrors;
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
	 * Returns true if this exception contains field-specific validation errors.
	 * @return true if field errors are present, false otherwise
	 */
	public boolean hasFieldErrors() {
		return fieldErrors != null && !fieldErrors.isEmpty();
	}

}
