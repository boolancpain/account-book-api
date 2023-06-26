package com.fyo.accountbook.global.error;

import org.springframework.http.HttpStatus;

import com.fyo.accountbook.global.response.BaseResponse;

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