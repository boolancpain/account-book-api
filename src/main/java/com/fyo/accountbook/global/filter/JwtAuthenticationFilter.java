package com.fyo.accountbook.global.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fyo.accountbook.global.jwt.JwtProvider;
import com.fyo.accountbook.global.util.HttpHeaderUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Jwt 인증 필터
 * 
 * @author boolancpain
 */
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtProvider jwtProvider;
	
	/**
	 * 헤더에서 access token을 추출하고 유효한 token인 경우 인증 객체(Authentication)를 SecurityContextHolder에 저장한다
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request
			, HttpServletResponse response
			, FilterChain filterChain) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String token = HttpHeaderUtils.getTokenFromHeader(request);
		
		if(StringUtils.hasText(token) && jwtProvider.isValidToken(token)) {
			// 토큰에서 인증 객체를 추출
			Authentication authentication = jwtProvider.getAuthentication(token);
			// Security Context에 인증 정보 저장
			SecurityContextHolder.getContext().setAuthentication(authentication);
			log.debug("SecurityContextHolder에 인증 객체를 저장함 [uri : {}]", uri);
		} else {
			log.debug("유효한 jwt를 가지지 않음 [uri : {}]", uri);
		}
		
		filterChain.doFilter(request, response);
	}
}