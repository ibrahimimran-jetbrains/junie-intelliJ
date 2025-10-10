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
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Validation error response class that extends ErrorResponse. Provides additional field
 * error information for validation failures.
 *
 * @author Junie AI
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationErrorResponse extends ErrorResponse {

	private List<FieldErrorDto> fieldErrors;

	/**
	 * Default constructor for JSON deserialization.
	 */
	public ValidationErrorResponse() {
		super();
	}

	/**
	 * Constructor with all fields.
	 * @param errorCode the error code
	 * @param message the error message
	 * @param timestamp the timestamp
	 * @param path the request path
	 * @param fieldErrors the list of field errors
	 */
	public ValidationErrorResponse(String errorCode, String message, LocalDateTime timestamp, String path,
			List<FieldErrorDto> fieldErrors) {
		super(errorCode, message, timestamp, path);
		this.fieldErrors = fieldErrors;
	}

	/**
	 * Creates a new builder instance.
	 * @return a new ValidationErrorResponseBuilder
	 */
	public static ValidationErrorResponseBuilder validationBuilder() {
		return new ValidationErrorResponseBuilder();
	}

	/**
	 * Returns the list of field errors.
	 * @return the field errors
	 */
	public List<FieldErrorDto> getFieldErrors() {
		return fieldErrors;
	}

	/**
	 * Sets the list of field errors.
	 * @param fieldErrors the field errors to set
	 */
	public void setFieldErrors(List<FieldErrorDto> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	/**
	 * Returns true if field errors are present.
	 * @return true if field errors exist, false otherwise
	 */
	public boolean hasFieldErrors() {
		return fieldErrors != null && !fieldErrors.isEmpty();
	}

	/**
	 * Returns the number of field errors.
	 * @return the count of field errors
	 */
	public int getFieldErrorCount() {
		return fieldErrors != null ? fieldErrors.size() : 0;
	}

	/**
	 * Builder class for ValidationErrorResponse.
	 */
	public static class ValidationErrorResponseBuilder {

		private String errorCode;

		private String message;

		private LocalDateTime timestamp;

		private String path;

		private List<FieldErrorDto> fieldErrors;

		private ValidationErrorResponseBuilder() {
		}

		/**
		 * Sets the error code.
		 * @param errorCode the error code
		 * @return this builder
		 */
		public ValidationErrorResponseBuilder errorCode(String errorCode) {
			this.errorCode = errorCode;
			return this;
		}

		/**
		 * Sets the message.
		 * @param message the error message
		 * @return this builder
		 */
		public ValidationErrorResponseBuilder message(String message) {
			this.message = message;
			return this;
		}

		/**
		 * Sets the timestamp.
		 * @param timestamp the timestamp
		 * @return this builder
		 */
		public ValidationErrorResponseBuilder timestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		/**
		 * Sets the path.
		 * @param path the request path
		 * @return this builder
		 */
		public ValidationErrorResponseBuilder path(String path) {
			this.path = path;
			return this;
		}

		/**
		 * Sets the field errors.
		 * @param fieldErrors the list of field errors
		 * @return this builder
		 */
		public ValidationErrorResponseBuilder fieldErrors(List<FieldErrorDto> fieldErrors) {
			this.fieldErrors = fieldErrors;
			return this;
		}

		/**
		 * Builds the ValidationErrorResponse.
		 * @return the built ValidationErrorResponse
		 */
		public ValidationErrorResponse build() {
			return new ValidationErrorResponse(errorCode, message, timestamp, path, fieldErrors);
		}

	}

}
