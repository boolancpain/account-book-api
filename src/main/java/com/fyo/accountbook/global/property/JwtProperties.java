package com.fyo.accountbook.global.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * Jwt Property class
 * 
 * @author boolancpain
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {
	private String secret;
	
	private long accessTokenExpirationTime;
	
	private long refreshTokenExpirationTime;
	
	private long reissuableDayBeforeExpirationDay;
}