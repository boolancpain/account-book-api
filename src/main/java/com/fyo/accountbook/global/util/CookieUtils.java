package com.fyo.accountbook.global.util;

import java.util.Optional;

import javax.servlet.http.Cookie;

/**
 * Cookie util class
 * 
 * @author boolancpain
 */
public class CookieUtils {
	/**
	 * name으로 검색해서 쿠키를 가져온다
	 */
	public static Optional<Cookie> getCookie(String name) {
		Cookie[] cookies = RequestUtils.getRequest().getCookies();
		if(cookies != null && cookies.length > 0) {
			for(Cookie cookie : cookies) {
				if(name.equals(cookie.getName())) {
					return Optional.of(cookie);
				}
			}
		}
		return Optional.empty();
	}
	
	/**
	 * 쿠키를 추가한다
	 */
	public static void addCookie(String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(maxAge);
		RequestUtils.getResponse().addCookie(cookie);
	}
	
	/**
	 * 쿠키를 삭제한다
	 */
	public static void deleteCookie(String name) {
		Cookie[] cookies = RequestUtils.getRequest().getCookies();
		if(cookies != null && cookies.length > 0) {
			for(Cookie cookie : cookies) {
				if(name.equals(cookie.getName())) {
					cookie.setValue("");
					cookie.setMaxAge(0);
					RequestUtils.getResponse().addCookie(cookie);
				}
			}
		}
	}
}