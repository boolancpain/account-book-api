package com.fyo.accountbook.domain.member;

import org.springframework.http.HttpStatus;

import com.fyo.accountbook.global.error.BaseError;
import com.fyo.accountbook.global.response.BaseResponse;
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
	NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "member.not_found", "not_found_member")
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