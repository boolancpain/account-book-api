package com.fyo.accountbook.domain.member.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 토큰 응답
 * 
 * @author boolancpain
 */
@Getter
@Builder
@AllArgsConstructor
public class TokenResponse {
	private String accessToken;
}