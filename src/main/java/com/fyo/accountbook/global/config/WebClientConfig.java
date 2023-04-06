package com.fyo.accountbook.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * WebClient configuration
 * 
 * @author boolancpain
 */
@Configuration
@Slf4j
public class WebClientConfig {
	@Bean
	WebClient webClient() {
		ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder().build();
		
		/*
		exchangeStrategies
			.messageWriters().stream()
			.filter(LoggingCodecSupport.class::isInstance)
			.forEach(writer -> ((LoggingCodecSupport)writer).setEnableLoggingRequestDetails(true));
		*/
		
		return WebClient.builder()
				.exchangeStrategies(exchangeStrategies)
				.filter(ExchangeFilterFunction.ofRequestProcessor(
					clientRequest -> {
						log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
						clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
						return Mono.just(clientRequest);
					}
				))
				.filter(ExchangeFilterFunction.ofResponseProcessor(
					clientResponse -> {
						clientResponse.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
						return Mono.just(clientResponse);
					}
				))
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}
}