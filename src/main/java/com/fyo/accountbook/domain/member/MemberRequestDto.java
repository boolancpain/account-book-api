package com.fyo.accountbook.domain.member;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	static class OAuthRequest {
		@NotBlank(message = "{validation.not_blank}")
		private String code;
		
		@NotBlank(message = "{validation.not_blank}")
		private String authorizedRedirectUri;
	}
}