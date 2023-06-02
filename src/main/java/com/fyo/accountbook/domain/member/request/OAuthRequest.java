package com.fyo.accountbook.domain.member.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * OAuth2 인증을 위한 요청 객체(인가 코드 포함)
 * 
 * @author boolancpain
 */
@Getter
@Setter
public class OAuthRequest {
	@NotBlank
	private String code;
	
	@NotBlank
	private String authorizedRedirectUri;
}