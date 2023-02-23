package com.fyo.accountbook.global.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * OAuth2 Property class
 * 
 * @author boolancpain
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.oauth2")
public class OAuth2Properties {
	private Kakao kakao;
	
	@Getter
	@Setter
	public static class Kakao {
		private String clientId;
	}
}