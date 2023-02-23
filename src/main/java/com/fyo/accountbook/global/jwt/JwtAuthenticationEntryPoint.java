package com.fyo.accountbook.global.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * AuthenticationEntryPoint 구현체
 *  인증되지 않은 유저가 요청했을 때 동작
 * 
 * @author boolancpain
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	/**
	 * 인증 정보 없이 접근하는 경우 SC_UNAUTHORIZED(401) 응답
	 */
	@Override
	public void commence(HttpServletRequest request
			, HttpServletResponse response
			, AuthenticationException authException) throws IOException, ServletException {
		log.debug("unauthorized error. message := {}", authException.getMessage());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}
}