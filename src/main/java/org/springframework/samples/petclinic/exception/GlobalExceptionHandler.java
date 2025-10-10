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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * Global exception handler for the PetClinic application. Provides centralized exception
 * handling and standardized error responses for all REST endpoints.
 *
 * @author Junie AI
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handles OwnerNotFoundException and returns a NOT_FOUND response.
	 * @param ex the OwnerNotFoundException
	 * @param request the web request
	 * @return ResponseEntity with error details
	 */
	@ExceptionHandler(OwnerNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleOwnerNotFound(OwnerNotFoundException ex, WebRequest request) {
		ErrorResponse errorResponse = ErrorResponse.builder()
			.errorCode(ex.getErrorCode())
			.message(ex.getMessage())
			.timestamp(ex.getTimestamp())
			.path(getRequestPath(request))
			.build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	/**
	 * Handles OwnerValidationException and returns a BAD_REQUEST response.
	 * @param ex the OwnerValidationException
	 * @param request the web request
	 * @return ResponseEntity with validation error details
	 */
	@ExceptionHandler(OwnerValidationException.class)
	public ResponseEntity<ValidationErrorResponse> handleOwnerValidation(OwnerValidationException ex,
			WebRequest request) {
		ValidationErrorResponse errorResponse = ValidationErrorResponse.validationBuilder()
			.errorCode(ex.getErrorCode())
			.message(ex.getMessage())
			.timestamp(ex.getTimestamp())
			.path(getRequestPath(request))
			.fieldErrors(convertToFieldErrorList(ex.getFieldErrors()))
			.build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	/**
	 * Handles PetValidationException and returns a BAD_REQUEST response.
	 * @param ex the PetValidationException
	 * @param request the web request
	 * @return ResponseEntity with validation error details
	 */
	@ExceptionHandler(PetValidationException.class)
	public ResponseEntity<ValidationErrorResponse> handlePetValidation(PetValidationException ex, WebRequest request) {
		ValidationErrorResponse errorResponse = ValidationErrorResponse.validationBuilder()
			.errorCode(ex.getErrorCode())
			.message(ex.getMessage())
			.timestamp(ex.getTimestamp())
			.path(getRequestPath(request))
			.fieldErrors(convertToFieldErrorList(ex.getFieldErrors()))
			.build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	/**
	 * Handles BusinessRuleViolationException and returns a BAD_REQUEST response.
	 * @param ex the BusinessRuleViolationException
	 * @param request the web request
	 * @return ResponseEntity with business rule error details
	 */
	@ExceptionHandler(BusinessRuleViolationException.class)
	public ResponseEntity<ErrorResponse> handleBusinessRuleViolation(BusinessRuleViolationException ex,
			WebRequest request) {
		ErrorResponse errorResponse = ErrorResponse.builder()
			.errorCode(ex.getErrorCode())
			.message(ex.getMessage())
			.timestamp(ex.getTimestamp())
			.path(getRequestPath(request))
			.build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	/**
	 * Handles MethodArgumentNotValidException for Spring validation errors.
	 * @param ex the MethodArgumentNotValidException
	 * @param request the web request
	 * @return ResponseEntity with validation error details
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			WebRequest request) {
		List<FieldErrorDto> fieldErrors = new ArrayList<>();

		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			FieldErrorDto dto = new FieldErrorDto();
			dto.setField(fieldError.getField());
			dto.setMessage(fieldError.getDefaultMessage());
			dto.setRejectedValue(
					fieldError.getRejectedValue() != null ? fieldError.getRejectedValue().toString() : null);
			fieldErrors.add(dto);
		}

		ValidationErrorResponse errorResponse = ValidationErrorResponse.validationBuilder()
			.errorCode("VALIDATION_ERROR")
			.message("Validation failed for request")
			.timestamp(LocalDateTime.now())
			.path(getRequestPath(request))
			.fieldErrors(fieldErrors)
			.build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	/**
	 * Handles BindException for form validation errors.
	 * @param ex the BindException
	 * @param request the web request
	 * @return ResponseEntity with validation error details
	 */
	@ExceptionHandler(BindException.class)
	public ResponseEntity<ValidationErrorResponse> handleBindException(BindException ex, WebRequest request) {
		List<FieldErrorDto> fieldErrors = new ArrayList<>();

		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			FieldErrorDto dto = new FieldErrorDto();
			dto.setField(fieldError.getField());
			dto.setMessage(fieldError.getDefaultMessage());
			dto.setRejectedValue(
					fieldError.getRejectedValue() != null ? fieldError.getRejectedValue().toString() : null);
			fieldErrors.add(dto);
		}

		ValidationErrorResponse errorResponse = ValidationErrorResponse.validationBuilder()
			.errorCode("VALIDATION_ERROR")
			.message("Form validation failed")
			.timestamp(LocalDateTime.now())
			.path(getRequestPath(request))
			.fieldErrors(fieldErrors)
			.build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	/**
	 * Handles generic RuntimeException as fallback.
	 * @param ex the RuntimeException
	 * @param request the web request
	 * @return ResponseEntity with error details
	 */
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, WebRequest request) {
		ErrorResponse errorResponse = ErrorResponse.builder()
			.errorCode("INTERNAL_ERROR")
			.message("An unexpected error occurred")
			.timestamp(LocalDateTime.now())
			.path(getRequestPath(request))
			.build();

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}

	/**
	 * Extracts the request path from WebRequest.
	 * @param request the web request
	 * @return the request path
	 */
	private String getRequestPath(WebRequest request) {
		return request.getDescription(false).replace("uri=", "");
	}

	/**
	 * Converts field error map to list of FieldErrorDto objects.
	 * @param fieldErrors map of field errors
	 * @return list of FieldErrorDto objects
	 */
	private List<FieldErrorDto> convertToFieldErrorList(Map<String, List<String>> fieldErrors) {
		if (fieldErrors == null || fieldErrors.isEmpty()) {
			return new ArrayList<>();
		}

		List<FieldErrorDto> errorList = new ArrayList<>();
		for (Map.Entry<String, List<String>> entry : fieldErrors.entrySet()) {
			String fieldName = entry.getKey();
			List<String> messages = entry.getValue();

			if (messages != null) {
				for (String message : messages) {
					FieldErrorDto dto = new FieldErrorDto();
					dto.setField(fieldName);
					dto.setMessage(message);
					errorList.add(dto);
				}
			}
		}

		return errorList;
	}

}
