package com.fyo.accountbook.domain.transaction.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 거래내역 정보
 * 
 * @author boolancpain
 */
@Getter
@Builder
@AllArgsConstructor
public class TransactionInfo {
	private Long transactionId;
	
	private Long memberId;
	
	private Long categoryId;
	
	private int cost;
	
	private String remark;
	
	private String transactedDate;
}