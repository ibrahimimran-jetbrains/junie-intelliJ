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
 * Exception thrown when business rules are violated. This exception is used for
 * domain-specific business logic violations that don't fit into specific validation
 * categories but still represent rule violations within the PetClinic application.
 *
 * @author Junie AI
 */
public class BusinessRuleViolationException extends PetClinicException {

	private static final String ERROR_CODE = "BUSINESS_RULE_VIOLATION";

	private final String ruleCode;

	private final Object relatedEntity;

	/**
	 * Constructs a new BusinessRuleViolationException with a message.
	 * @param message the business rule violation message
	 */
	public BusinessRuleViolationException(String message) {
		super(ERROR_CODE, message);
		this.ruleCode = null;
		this.relatedEntity = null;
	}

	/**
	 * Constructs a new BusinessRuleViolationException with a message and rule code.
	 * @param message the business rule violation message
	 * @param ruleCode specific code identifying which business rule was violated
	 */
	public BusinessRuleViolationException(String message, String ruleCode) {
		super(ERROR_CODE, message);
		this.ruleCode = ruleCode;
		this.relatedEntity = null;
	}

	/**
	 * Constructs a new BusinessRuleViolationException with message, rule code, and
	 * related entity.
	 * @param message the business rule violation message
	 * @param ruleCode specific code identifying which business rule was violated
	 * @param relatedEntity the entity related to this business rule violation
	 */
	public BusinessRuleViolationException(String message, String ruleCode, Object relatedEntity) {
		super(ERROR_CODE, message);
		this.ruleCode = ruleCode;
		this.relatedEntity = relatedEntity;
	}

	/**
	 * Constructs a new BusinessRuleViolationException with a message and cause.
	 * @param message the business rule violation message
	 * @param cause the cause of this exception
	 */
	public BusinessRuleViolationException(String message, Throwable cause) {
		super(ERROR_CODE, message, cause);
		this.ruleCode = null;
		this.relatedEntity = null;
	}

	/**
	 * Constructs a new BusinessRuleViolationException with message, rule code, and cause.
	 * @param message the business rule violation message
	 * @param ruleCode specific code identifying which business rule was violated
	 * @param cause the cause of this exception
	 */
	public BusinessRuleViolationException(String message, String ruleCode, Throwable cause) {
		super(ERROR_CODE, message, cause);
		this.ruleCode = ruleCode;
		this.relatedEntity = null;
	}

	/**
	 * Returns the specific rule code that was violated, if available.
	 * @return the rule code, or null if not specified
	 */
	public String getRuleCode() {
		return ruleCode;
	}

	/**
	 * Returns the entity related to this business rule violation, if available.
	 * @return the related entity, or null if not specified
	 */
	public Object getRelatedEntity() {
		return relatedEntity;
	}

	/**
	 * Returns true if this exception has a specific rule code.
	 * @return true if rule code is present, false otherwise
	 */
	public boolean hasRuleCode() {
		return ruleCode != null && !ruleCode.trim().isEmpty();
	}

}
