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

import java.time.LocalDateTime;

/**
 * Base abstract exception class for all PetClinic custom exceptions. Provides common
 * error handling functionality including error codes, timestamps, and standardized
 * message formatting.
 *
 * @author Junie AI
 */
public abstract class PetClinicException extends RuntimeException {

	private final String errorCode;

	private final LocalDateTime timestamp;

	/**
	 * Constructs a new PetClinicException with the specified error code and message.
	 * @param errorCode the error code identifying the type of error
	 * @param message the detail message explaining the error
	 */
	protected PetClinicException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.timestamp = LocalDateTime.now();
	}

	/**
	 * Constructs a new PetClinicException with the specified error code, message, and
	 * cause.
	 * @param errorCode the error code identifying the type of error
	 * @param message the detail message explaining the error
	 * @param cause the cause of this exception
	 */
	protected PetClinicException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		this.timestamp = LocalDateTime.now();
	}

	/**
	 * Returns the error code for this exception.
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Returns the timestamp when this exception was created.
	 * @return the timestamp
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	/**
	 * Returns a string representation of this exception including error code and
	 * timestamp.
	 * @return a string representation of this exception
	 */
	@Override
	public String toString() {
		return String.format("%s[errorCode='%s', timestamp='%s', message='%s']", getClass().getSimpleName(), errorCode,
				timestamp, getMessage());
	}

}
