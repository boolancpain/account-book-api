package com.fyo.accountbook.global.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Date Validator
 * 
 * @author boolancpain
 */
public class DateValidator implements ConstraintValidator<DateConstraint, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
			LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyyMMdd"));
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}