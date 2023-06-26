package com.fyo.accountbook.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * CustomException
 * 
 * @author boolancpain
 */
@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private final BaseError error;
}