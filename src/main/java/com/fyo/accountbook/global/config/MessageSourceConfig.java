package com.fyo.accountbook.global.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fyo.accountbook.global.common.CustomMessageSource;

/**
 * Message Source 설정 클래스
 * 
 * @author boolancpain
 */
@Configuration
public class MessageSourceConfig implements WebMvcConfigurer {
	/**
	 * MessageSource 빈 생성
	 */
	@Bean
	MessageSource messageSource() {
		CustomMessageSource messageSource = new CustomMessageSource();
		messageSource.setBasename("classpath:messages/messages");
		messageSource.setDefaultLocale(Locale.KOREA);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setUseCodeAsDefaultMessage(true); // 메세지를 찾지 못한 경우 예외를 발생시키지 않고 코드를 메세지로 사용
		return messageSource;
	}
}