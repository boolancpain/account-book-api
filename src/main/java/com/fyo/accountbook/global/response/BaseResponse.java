package com.fyo.accountbook.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fyo.accountbook.global.util.MessageUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 공통 응답 class
 * 
 * @author boolancpain
 */
@Getter
@Builder
@AllArgsConstructor
public class BaseResponse {
	@JsonInclude(value = Include.NON_EMPTY)
	private String code;
	
	private String message;
	
	@JsonInclude(value = Include.NON_NULL)
	private Object data;
	
	/**
	 * 기본 응답 객체를 생성한다.
	 * 
	 * @param data
	 * @return 기본 응답 객체
	 */
	public static BaseResponse ok(Object data) {
		return BaseResponse.builder()
				.code("ok")
				.message(MessageUtils.getMessage("ok"))
				.data(data)
				.build();
	}
}