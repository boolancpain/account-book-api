package com.fyo.accountbook.global.property;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * Cors Property class
 * 
 * @author boolancpain
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.cors")
public class CorsProperties {
	private List<String> allowedOrigins;
	
	private List<String> allowedMethods;
	
	private String allowedHeader;
}