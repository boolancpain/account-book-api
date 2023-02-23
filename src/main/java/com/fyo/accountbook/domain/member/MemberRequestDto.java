package com.fyo.accountbook.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 회원 서비스 요청 객체
 * 
 * @author boolancpain
 */
public class MemberRequestDto {
	/**
	 * OAuth2 인증을 위한 요청 객체(인가 코드 포함)
	 */
	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	static class OAuthRequest {
		private String code;
		private String authorizedRedirectUri;
	}
}