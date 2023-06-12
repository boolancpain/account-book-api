package com.fyo.accountbook.global.validator;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @EnumConstraint 검증 구현체
 * 
 * @author boolancpain
 *
 */
public class EnumValidator implements ConstraintValidator<EnumConstraint, String>{
	private Set<String> elements;
	
	/*
	 * enum code to list
	 */
	@Override
	public void initialize(EnumConstraint constraintAnnotation) {
		elements = Arrays.stream(constraintAnnotation.target().getEnumConstants())
				.map(constant -> constant.name())
				.collect(Collectors.toSet());
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null) return false;
		return elements.contains(value);
	}
}