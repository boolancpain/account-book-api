package com.fyo.accountbook.global.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fyo.accountbook.global.common.BaseResponse;
import com.fyo.accountbook.global.common.CustomException;
import com.fyo.accountbook.global.common.ValidationField;
import com.fyo.accountbook.global.common.ValidationResponse;
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
	
	/**
	 * validation exception handling
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex
			, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<ValidationField> validationFields = ex.getFieldErrors().stream()
				.map(fieldError -> ValidationField.builder()
					.field(fieldError.getField())
					.message(fieldError.getDefaultMessage())
					.build())
				.collect(Collectors.toList());
		
		return ResponseEntity.badRequest().body(ValidationResponse.builder()
				.fields(validationFields)
				.build());
	}
}