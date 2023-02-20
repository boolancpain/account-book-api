package com.fyo.accountbook.global.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 공통 response object
 * 
 * @author boolancpain
 */
@Getter
@Builder
@AllArgsConstructor
public class BaseResponse {
	private String message;
}