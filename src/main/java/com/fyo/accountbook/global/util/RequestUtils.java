package com.fyo.accountbook.global.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Request util
 * 
 * @author boolancpain
 */
public class RequestUtils {
	/**
	 * RequestContextHolder로부터 HttpServletRequest를 반환
	 * 
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}
	
	/**
	 * RequestContextHolder로부터 HttpServletResponse를 반환
	 * 
	 * @return HttpServletResponse
	 */
	public static HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
	}
}
