package com.fyo.accountbook.domain.category;

import org.springframework.http.HttpStatus;

import com.fyo.accountbook.global.error.BaseError;
import com.fyo.accountbook.global.response.BaseResponse;
import com.fyo.accountbook.global.util.MessageUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 카테고리 서비스 에러 정의
 * 
 * @author boolancpain
 */
@Getter
@RequiredArgsConstructor
public enum CategoryError implements BaseError {
	NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "category.not_found", "not_found_category"),
	
	EXISTS_CATEGORY(HttpStatus.BAD_REQUEST, "category.exists", "already_exists_category")
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