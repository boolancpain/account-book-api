package com.fyo.accountbook.domain.account;

import org.springframework.http.HttpStatus;

import com.fyo.accountbook.global.error.BaseError;
import com.fyo.accountbook.global.response.BaseResponse;
import com.fyo.accountbook.global.util.MessageUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 장부 서비스 에러 정의
 * 
 * @author boolancpain
 */
@Getter
@RequiredArgsConstructor
public enum AccountError implements BaseError {
	NOT_FOUND_ACCOUNT(HttpStatus.NOT_FOUND, "account.not_found", "not_found_account"),
	
	ALREADY_EXISTS_ACCOUNT(HttpStatus.CONFLICT, "account.exists", "already_exists_account")
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