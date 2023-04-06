package com.fyo.accountbook.global.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 공통 응답 class
 * 
 * @author boolancpain
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
	private String code;
	private String message;
}