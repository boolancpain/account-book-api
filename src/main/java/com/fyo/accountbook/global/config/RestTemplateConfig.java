package com.fyo.accountbook.global.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.stream.Collectors;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * RestTemplate 설정 클래스
 * 
 * @author boolancpain
 */
@Configuration
public class RestTemplateConfig {
	@Bean
	RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return new RestTemplateBuilder()
				.setConnectTimeout(Duration.ofSeconds(5))
				.setReadTimeout(Duration.ofSeconds(5))
				.additionalInterceptors(clientHttpRequestInterceptor(), new LoggingInterceptor())
				.requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
				.build();
	}
	
	/*
	 * 통신 실패시 3번까지 재시도한다.
	 */
	public ClientHttpRequestInterceptor clientHttpRequestInterceptor() {
		return (request, body, execution) -> {
			RetryTemplate retryTemplate = new RetryTemplate();
			retryTemplate.setRetryPolicy(new SimpleRetryPolicy(3));
			try {
				return retryTemplate.execute(context -> execution.execute(request, body));
			} catch (Throwable throwable) {
				throw new RuntimeException(throwable);
			}
		};
	}
	
	@Slf4j
	static class LoggingInterceptor implements ClientHttpRequestInterceptor {
		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
				throws IOException {
			String rId = String.valueOf(System.currentTimeMillis());
			this.printRequest(rId, request, body);
			ClientHttpResponse response = execution.execute(request, body);
			this.printResponse(rId, response);
			return response;
		}
		
		/*
		 * request print
		 */
		private void printRequest(String rId, HttpRequest request, byte[] body) {
			log.info("[{}] uri:{}, method:{}, headers:{}, body:{}", rId, request.getURI(), request.getMethod(), request.getHeaders(), new String(body, StandardCharsets.UTF_8));
		}
		
		/*
		 * response print
		 */
		private void printResponse(String rId, ClientHttpResponse response) throws IOException {
			String body = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8)).lines()
					.collect(Collectors.joining("\n"));
			log.info("[{}] status:{}, headers:{}, body:{}", rId, response.getStatusCode(), response.getHeaders(), body);
		}
	}
}