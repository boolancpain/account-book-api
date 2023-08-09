package com.fyo.accountbook.domain.transaction.request;

import com.fyo.accountbook.global.validator.DateConstraint;

import lombok.Getter;
import lombok.Setter;

/**
 * 거래내역 조회 요청 객체
 * 
 * @author boolancpain
 */
@Getter
@Setter
public class TransactionRequest {
	@DateConstraint
	private String startDate;
	
	@DateConstraint
	private String endDate;
}