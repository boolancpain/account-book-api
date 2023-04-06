package com.fyo.accountbook.global.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fyo.accountbook.global.util.MessageUtils;

/**
 * Message Source 설정 클래스
 * 
 * @author boolancpain
 */
@Configuration
public class MessageSourceConfig implements WebMvcConfigurer {
	/**
	 * MessageSource bean 생성
	 */
	@Bean
	MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages/messages");
		messageSource.setDefaultLocale(Locale.KOREA);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setUseCodeAsDefaultMessage(true); // 메세지를 찾지 못한 경우 예외를 발생시키지 않고 코드를 메세지로 사용
		
		MessageUtils.setMessageSource(messageSource); // MessageUtils set
		
		return messageSource;
	}
	
	/**
	 * LocalValidatorFactoryBean에 message source를 set해서 bean 생성
	 */
	@Bean
	public LocalValidatorFactoryBean getValidator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}
}