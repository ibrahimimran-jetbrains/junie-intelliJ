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

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Data Transfer Object for individual field validation errors. Represents a validation
 * error for a specific field in a form or request.
 *
 * @author Junie AI
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldErrorDto {

	private String field;

	private String message;

	private String rejectedValue;

	/**
	 * Default constructor for JSON deserialization.
	 */
	public FieldErrorDto() {
	}

	/**
	 * Constructor with field and message.
	 * @param field the field name
	 * @param message the error message
	 */
	public FieldErrorDto(String field, String message) {
		this.field = field;
		this.message = message;
	}

	/**
	 * Constructor with all fields.
	 * @param field the field name
	 * @param message the error message
	 * @param rejectedValue the rejected value
	 */
	public FieldErrorDto(String field, String message, String rejectedValue) {
		this.field = field;
		this.message = message;
		this.rejectedValue = rejectedValue;
	}

	/**
	 * Creates a new builder instance.
	 * @return a new FieldErrorDtoBuilder
	 */
	public static FieldErrorDtoBuilder builder() {
		return new FieldErrorDtoBuilder();
	}

	/**
	 * Returns the field name.
	 * @return the field name
	 */
	public String getField() {
		return field;
	}

	/**
	 * Sets the field name.
	 * @param field the field name to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * Returns the error message.
	 * @return the error message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the error message.
	 * @param message the error message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Returns the rejected value.
	 * @return the rejected value
	 */
	public String getRejectedValue() {
		return rejectedValue;
	}

	/**
	 * Sets the rejected value.
	 * @param rejectedValue the rejected value to set
	 */
	public void setRejectedValue(String rejectedValue) {
		this.rejectedValue = rejectedValue;
	}

	/**
	 * Returns a string representation of this field error.
	 * @return string representation
	 */
	@Override
	public String toString() {
		return String.format("FieldErrorDto{field='%s', message='%s', rejectedValue='%s'}", field, message,
				rejectedValue);
	}

	/**
	 * Checks equality based on field, message, and rejected value.
	 * @param obj the object to compare
	 * @return true if equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		FieldErrorDto that = (FieldErrorDto) obj;
		return java.util.Objects.equals(field, that.field) && java.util.Objects.equals(message, that.message)
				&& java.util.Objects.equals(rejectedValue, that.rejectedValue);
	}

	/**
	 * Returns hash code based on field, message, and rejected value.
	 * @return hash code
	 */
	@Override
	public int hashCode() {
		return java.util.Objects.hash(field, message, rejectedValue);
	}

	/**
	 * Builder class for FieldErrorDto.
	 */
	public static class FieldErrorDtoBuilder {

		private String field;

		private String message;

		private String rejectedValue;

		private FieldErrorDtoBuilder() {
		}

		/**
		 * Sets the field name.
		 * @param field the field name
		 * @return this builder
		 */
		public FieldErrorDtoBuilder field(String field) {
			this.field = field;
			return this;
		}

		/**
		 * Sets the message.
		 * @param message the error message
		 * @return this builder
		 */
		public FieldErrorDtoBuilder message(String message) {
			this.message = message;
			return this;
		}

		/**
		 * Sets the rejected value.
		 * @param rejectedValue the rejected value
		 * @return this builder
		 */
		public FieldErrorDtoBuilder rejectedValue(String rejectedValue) {
			this.rejectedValue = rejectedValue;
			return this;
		}

		/**
		 * Builds the FieldErrorDto.
		 * @return the built FieldErrorDto
		 */
		public FieldErrorDto build() {
			return new FieldErrorDto(field, message, rejectedValue);
		}

	}

}
