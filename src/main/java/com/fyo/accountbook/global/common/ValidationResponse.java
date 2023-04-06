package com.fyo.accountbook.global.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * validation response
 * 
 * @author boolancpain
 */
@Getter
@Builder
@AllArgsConstructor
public class ValidationResponse {
	private List<ValidationField> fields;
}