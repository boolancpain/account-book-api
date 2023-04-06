package com.fyo.accountbook.global.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fyo.accountbook.global.common.BaseResponse;
import com.fyo.accountbook.global.common.CustomException;
import com.fyo.accountbook.global.util.MessageUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 전역 예외 핸들러
 * 
 * @author wbs
 */
@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	/**
	 * CustomException handling
	 */
	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<BaseResponse> handleCustomException(CustomException ce) {
		log.debug("{} error! msg : {}", ce.getBaseError().getStatus(), ce.getBaseError().getCode());
		return ResponseEntity
				.status(ce.getBaseError().getStatus())
				.body(ce.getBaseError().toBaseResponse());
	}
	
	/**
	 * RuntimeException handling
	 */
	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<BaseResponse> handleRuntimeException(RuntimeException re) {
		log.debug("runtime error! msg : {}", re.getLocalizedMessage());
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(BaseResponse.builder().message(MessageUtils.getMessage("error")).build());
	}
}