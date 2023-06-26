package com.fyo.accountbook.domain.account.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 장부 정보
 * 
 * @author boolancpain
 */
@Getter
@Builder
@AllArgsConstructor
public class AccountInfo {
	private Long accountId;
}