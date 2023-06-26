package com.fyo.accountbook.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * validation error field
 * 
 * @author boolancpain
 */
@Getter
@Builder
@AllArgsConstructor
public class ValidationField {
	private String field;
	private String message;
	private Object input;
}