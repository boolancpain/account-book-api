package com.fyo.accountbook.domain.member.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * kakao oAuth2 토큰 요청 response
 * 
 * @author boolancpain
 */
@Getter
@Builder
@AllArgsConstructor
public class KakaoOAuth2Token {
	private String access_token;
	private String token_type;
	private String refresh_token;
	private String id_token;
	private long expires_in;
	private long refresh_token_expires_in;
}