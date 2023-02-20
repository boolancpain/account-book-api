package com.fyo.accountbook.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Cors 설정 클래스
 * 
 * @author boolancpain
 */
@Configuration
public class CorsConfig {
	/**
	 * CorsFilter bean 생성
	 */
	@Bean
	CorsFilter corsFilter() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.addAllowedOriginPattern("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		// 모든 url에 대해 cors를 허용
		source.registerCorsConfiguration("/**", configuration);
		
		return new CorsFilter(source);
	}
}