package com.fyo.accountbook.global.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * Kakao OAuth2 Property class
 * 
 * @author boolancpain
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.oauth2.kakao")
public class KakaoProperty {
	private String url;
	
	private String clientId;
	
	private String grantType;
}