package com.fyo.accountbook.global.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Enum 파라미터 검증 애노테이션 정의
 * 
 * @author boolancpain
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface EnumConstraint {
	String message() default "지원하지 않는 값입니다.";
	
	Class<?>[] groups() default { };
	
	Class<? extends Payload>[] payload() default { };
	
	Class<? extends ValidateEnum> target();
}