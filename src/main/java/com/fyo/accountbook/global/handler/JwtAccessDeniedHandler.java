package com.fyo.accountbook.global.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyo.accountbook.global.common.BaseResponse;
import com.fyo.accountbook.global.util.MessageUtils;

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
	private final ObjectMapper objectMapper;
	
	/**
	 * 자원의 접근 권한이 없는 경우 SC_FORBIDDEN(403) 응답
	 */
	@Override
	public void handle(HttpServletRequest request
			, HttpServletResponse response
			, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.debug("access denied error. message := {}", accessDeniedException.getLocalizedMessage());
		
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.getWriter().write(objectMapper
				.writeValueAsString(BaseResponse.builder().message(MessageUtils.getMessage("forbidden")).build()));
	}
}