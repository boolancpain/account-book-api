package com.fyo.accountbook.global.common;

import org.springframework.http.HttpStatus;

/**
 * 공통 에러 정의 Interface
 * 
 * @author boolancpain
 */
public interface BaseError {
	// enum name
	String name();
	// enum.status
	HttpStatus getStatus();
	// enum code
	String getCode();
	
	// to BaseResponse
	BaseResponse toBaseResponse();
}