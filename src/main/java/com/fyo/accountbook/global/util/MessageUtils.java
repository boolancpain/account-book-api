package com.fyo.accountbook.global.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Message util class
 * 
 * @author boolancpain
 */
public class MessageUtils {
	private static MessageSource messageSource;
	
	/**
	 * MessageSource를 인자로 받아 set
	 */
	public static void setMessageSource(MessageSource ms) {
		messageSource = ms;
	}
	
	public static String getMessage(String key) {
		return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
	}
	
	public static String getMessage(String key, Object... args) {
		return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
	}
}