package com.fyo.accountbook.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.fyo.accountbook.global.property.CorsProperties;

import lombok.RequiredArgsConstructor;

/**
 * Cors 설정 클래스
 * 
 * @author boolancpain
 */
@Configuration
@RequiredArgsConstructor
public class CorsConfig {
	private final CorsProperties corsProperties;
	
	/**
	 * CorsFilter bean 생성
	 */
	@Bean
	CorsFilter corsFilter() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(corsProperties.getAllowedOrigins());
		configuration.setAllowedMethods(corsProperties.getAllowedMethods());
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		// 모든 url에 대해 cors를 허용
		source.registerCorsConfiguration("/**", configuration);
		
		return new CorsFilter(source);
	}
}