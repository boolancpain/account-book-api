package com.fyo.accountbook.domain.member;

import org.springframework.http.HttpStatus;

import com.fyo.accountbook.global.common.BaseError;
import com.fyo.accountbook.global.common.BaseResponse;
import com.fyo.accountbook.global.util.MessageUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 회원 서비스 에러 정의
 * 
 * @author boolancpain
 */
@Getter
@RequiredArgsConstructor
public enum MemberError implements BaseError {
	INVALID_PROVIDER(HttpStatus.CONFLICT, "oauth2.provider.invalid", "invalid_provider"),
	FAILED_LOGIN(HttpStatus.INTERNAL_SERVER_ERROR, "oauth2.login.failed", "failed_login"),
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