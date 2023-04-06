package com.fyo.accountbook.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 서비스 응답 객체
 * 
 * @author boolancpain
 */
public class MemberResponseDto {
	/**
	 * kakao oAuth2 토큰 요청 response
	 */
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	static class KakaoOAuth2Token {
		private String access_token;
		private String token_type;
		private String refresh_token;
		private String id_token;
		private long expires_in;
		private long refresh_token_expires_in;
	}
	
	/**
	 * kakao oAuth2 회원 정보 response
	 */
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	static class KakaoOAuth2UserInfo {
		private String sub;
		private String nickname;
	}
	
	/**
	 * 회원 정보
	 */
	@Getter
	@Builder
	@AllArgsConstructor
	static class MemberInfo {
		private String name;
	}
	
	/**
	 * 토큰
	 */
	@Getter
	@Builder
	@AllArgsConstructor
	static class TokenResponse {
		private String accessToken;
	}
}