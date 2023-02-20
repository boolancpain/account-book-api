package com.fyo.accountbook.global.common;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * message source 상속 클래스
 * 
 * @author boolancpain
 */
public class CustomMessageSource extends ReloadableResourceBundleMessageSource {
	public String getMessage(String code) {
		return super.getMessage(code, null, LocaleContextHolder.getLocale());
	}
	
	public String getMessage(String code, Object... args) {
		return super.getMessage(code, args, LocaleContextHolder.getLocale());
	}
}