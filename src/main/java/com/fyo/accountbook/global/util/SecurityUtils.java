package com.fyo.accountbook.global.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import com.fyo.accountbook.global.error.AuthError;
import com.fyo.accountbook.global.error.CustomException;

/**
 * Security util class
 * 
 * @author boolancpain
 */
public class SecurityUtils {
	// JwtFilter에서 저장한 회원 인증 객체에서 회원 id를 추출
	public static Long getCurrentMemberId() {
		// JwtFilter에서 저장한 Authentication 인증 객체를 Security Context로부터 가져옴
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || !StringUtils.hasText(authentication.getName())) {
			throw new CustomException(AuthError.UNAUTHORIZED);
		}
		return Long.parseLong(authentication.getName());
	}
}