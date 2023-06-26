package com.fyo.accountbook.domain.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fyo.accountbook.global.response.BaseResponse;
import com.fyo.accountbook.global.util.MessageUtils;

@RestController
public class TestController {
	@GetMapping("/test")
	public ResponseEntity<BaseResponse> test() {
		return ResponseEntity.ok(BaseResponse.builder()
				.message(MessageUtils.getMessage("test"))
				.build());
	}
}