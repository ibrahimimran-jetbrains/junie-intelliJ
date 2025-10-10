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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Base error response class for standardized error handling. Provides common fields for
 * all error responses in the PetClinic application.
 *
 * @author Junie AI
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

	private String errorCode;

	private String message;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime timestamp;

	private String path;

	/**
	 * Default constructor for JSON deserialization.
	 */
	public ErrorResponse() {
	}

	/**
	 * Constructor with all fields.
	 * @param errorCode the error code
	 * @param message the error message
	 * @param timestamp the timestamp
	 * @param path the request path
	 */
	public ErrorResponse(String errorCode, String message, LocalDateTime timestamp, String path) {
		this.errorCode = errorCode;
		this.message = message;
		this.timestamp = timestamp;
		this.path = path;
	}

	/**
	 * Creates a new builder instance.
	 * @return a new ErrorResponseBuilder
	 */
	public static ErrorResponseBuilder builder() {
		return new ErrorResponseBuilder();
	}

	/**
	 * Returns the error code.
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the error code.
	 * @param errorCode the error code to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
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
	 * Returns the timestamp.
	 * @return the timestamp
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp.
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Returns the request path.
	 * @return the request path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the request path.
	 * @param path the request path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Builder class for ErrorResponse.
	 */
	public static class ErrorResponseBuilder {

		private String errorCode;

		private String message;

		private LocalDateTime timestamp;

		private String path;

		private ErrorResponseBuilder() {
		}

		/**
		 * Sets the error code.
		 * @param errorCode the error code
		 * @return this builder
		 */
		public ErrorResponseBuilder errorCode(String errorCode) {
			this.errorCode = errorCode;
			return this;
		}

		/**
		 * Sets the message.
		 * @param message the error message
		 * @return this builder
		 */
		public ErrorResponseBuilder message(String message) {
			this.message = message;
			return this;
		}

		/**
		 * Sets the timestamp.
		 * @param timestamp the timestamp
		 * @return this builder
		 */
		public ErrorResponseBuilder timestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		/**
		 * Sets the path.
		 * @param path the request path
		 * @return this builder
		 */
		public ErrorResponseBuilder path(String path) {
			this.path = path;
			return this;
		}

		/**
		 * Builds the ErrorResponse.
		 * @return the built ErrorResponse
		 */
		public ErrorResponse build() {
			return new ErrorResponse(errorCode, message, timestamp, path);
		}

	}

}
