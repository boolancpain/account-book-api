package com.fyo.accountbook.domain.transaction;

import org.springframework.http.HttpStatus;

import com.fyo.accountbook.global.error.BaseError;
import com.fyo.accountbook.global.response.BaseResponse;
import com.fyo.accountbook.global.util.MessageUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 거래내역 서비스 에러 정의
 * 
 * @author boolancpain
 */
@Getter
@RequiredArgsConstructor
public enum TransactionError implements BaseError {
	INVALID_RANGE_DATE(HttpStatus.BAD_REQUEST, "transaction.invalid_range_date", "invalid_range_date")
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