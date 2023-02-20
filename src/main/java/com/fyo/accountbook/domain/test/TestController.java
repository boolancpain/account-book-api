package com.fyo.accountbook.domain.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fyo.accountbook.global.common.BaseResponse;
import com.fyo.accountbook.global.common.CustomMessageSource;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {
	private final CustomMessageSource messageSource;
	
	@GetMapping("/test")
	public ResponseEntity<BaseResponse> test() {
		return ResponseEntity.ok(BaseResponse.builder()
				.message(messageSource.getMessage("test"))
				.build());
	}
}