package com.fyo.accountbook.domain.member.request;

import javax.validation.constraints.NotBlank;

import com.fyo.accountbook.domain.member.MemberProvider;
import com.fyo.accountbook.global.validator.EnumConstraint;

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
	@EnumConstraint(target = MemberProvider.class)
	private String provider;
	
	@NotBlank
	private String code;
	
	@NotBlank
	private String authorizedRedirectUri;
}