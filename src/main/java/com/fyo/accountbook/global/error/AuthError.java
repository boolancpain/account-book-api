package com.fyo.accountbook.global.error;

import org.springframework.http.HttpStatus;

import com.fyo.accountbook.global.response.BaseResponse;
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
	// access token
	NOT_FOUND_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "jwt.access_token.not_found", "not_found_access_token"),
	INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "jwt.access_token.invalid", "invalid_access_token"),
	// refresh token
	NOT_FOUND_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "jwt.refresh_token.not_found", "not_found_refresh_token"),
	INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "jwt.refresh_token.invalid", "invalid_refresh_token"),
	// oauth2
	INVALID_AUTHORIZATION_CODE(HttpStatus.BAD_REQUEST, "oauth2.invalid.authorization_code", "invalid_authorization_code"),
	
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "unauthorized", "unauthorized"),
	
	FORBIDDEN(HttpStatus.FORBIDDEN, "forbidden", "forbidden"),
	
	INVALID_PROVIDER(HttpStatus.CONFLICT, "oauth2.invalid.provider", "invalid_provider"),
	
	FAILED_LOGIN(HttpStatus.INTERNAL_SERVER_ERROR, "oauth2.login.failed", "failed_login")
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