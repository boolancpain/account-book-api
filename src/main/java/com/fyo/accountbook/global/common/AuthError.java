package com.fyo.accountbook.global.common;

import org.springframework.http.HttpStatus;

import com.fyo.accountbook.global.util.MessageUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 인증 관련 에러 정의
 * 
 * @author boolancpain
 */
@Getter
@RequiredArgsConstructor
public enum AuthError implements BaseError {
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "error.401", "unauthorized"),
	FORBIDDEN(HttpStatus.FORBIDDEN, "error.403", "forbidden"),
	// access token
	NOT_FOUND_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "jwt.access_token.not_found", "not_found_access_token"),
	INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "jwt.access_token.invalid", "invalid_access_token"),
	// refresh token
	NOT_FOUND_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "jwt.refresh_token.not_found", "not_found_refresh_token"),
	INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "jwt.refresh_token.invalid", "invalid_refresh_token")
	;
	
	private final HttpStatus status;
	private final String key;
	private final String code;
	
	@Override
	public BaseResponse toBaseResponse() {
		return BaseResponse.builder()
				.code(this.code)
				.message(MessageUtils.getMessage(this.key))
				.build();
	}
}