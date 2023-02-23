package com.fyo.accountbook.global.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * AccessDeniedHandler 구현체
 *  액세스 가능한지 권한 체크 후 권한이 없을 때 동작
 * 
 * @author boolancpain
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
	/**
	 * 자원의 접근 권한이 없는 경우 SC_FORBIDDEN(403) 응답
	 */
	@Override
	public void handle(HttpServletRequest request
			, HttpServletResponse response
			, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.debug("access denied error. message := {}", accessDeniedException.getLocalizedMessage());
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}
}