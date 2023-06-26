package com.fyo.accountbook.global.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fyo.accountbook.global.error.CustomException;
import com.fyo.accountbook.global.response.BaseResponse;
import com.fyo.accountbook.global.response.ValidationField;
import com.fyo.accountbook.global.response.ValidationResponse;
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
		log.debug("{} error! msg : {}", ce.getError().getStatus(), ce.getError().getCode());
		return ResponseEntity
				.status(ce.getError().getStatus())
				.body(ce.getError().toBaseResponse());
	}
	
	/**
	 * RuntimeException handling
	 */
	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<BaseResponse> handleRuntimeException(RuntimeException re) {
		log.debug("runtime error! msg : {}", re.getLocalizedMessage());
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(BaseResponse.builder().message(MessageUtils.getMessage("internal_server_error")).build());
	}
	
	/**
	 * BindException handler : requestparam validation exception handler
	 */
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex
			, HttpHeaders headers
			, HttpStatus status
			, WebRequest request) {
		// 유효성 검증 결과 데이터 변환
		List<ValidationField> validationFields = this.getValidationFields(ex.getFieldErrors());
		return ResponseEntity.badRequest().body(ValidationResponse.builder()
				.code("invalid_request")
				.fields(validationFields)
				.build());
	}
	
	/**
	 * MethodArgumentNotValidException handler : requestbody validation exception handler
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex
			, HttpHeaders headers
			, HttpStatus status
			, WebRequest request) {
		// 유효성 검증 결과 데이터 변환
		List<ValidationField> validationFields = this.getValidationFields(ex.getFieldErrors());
		return ResponseEntity.badRequest().body(ValidationResponse.builder()
				.code("invalid_request")
				.fields(validationFields)
				.build());
	}
	
	/**
	 * FieldError 객체를 유효성 검증 결과 데이터로 변환한다.
	 * 
	 * @param List<FieldError> fieldErrors
	 */
	private List<ValidationField> getValidationFields(List<FieldError> errors) {
		return errors.stream()
				.map(fieldError -> {
					StringBuilder sb = new StringBuilder();
					
					String code = fieldError.getCode();
					String key = String.format("validation.%s", code);
					switch(code) {
					case "Range":
						Object[] args = fieldError.getArguments();
						sb.append(MessageUtils.getMessage(key, args[2], args[1]));
						break;
					default:
						sb.append(MessageUtils.getMessage(key));
					}
					
					return ValidationField.builder()
						.field(fieldError.getField())
						.input(fieldError.getRejectedValue())
						.message(sb.toString())
						.build();
				})
				.collect(Collectors.toList());
	}
}