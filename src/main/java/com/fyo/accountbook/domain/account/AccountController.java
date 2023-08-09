package com.fyo.accountbook.domain.account;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fyo.accountbook.global.response.BaseResponse;

import lombok.RequiredArgsConstructor;

/**
 * 장부 Controller
 * 
 * @author boolancpain
 */
@RestController
@RequiredArgsConstructor
public class AccountController {
	private final AccountService accountService;
	
	/**
	 * 내 장부를 조회한다.
	 * 
	 * @return 장부 정보
	 */
	@GetMapping("/accounts")
	public BaseResponse getMyAccount() {
		return BaseResponse.ok(accountService.getMyAccount());
	}
	
	/**
	 * 장부를 생성한다.
	 * 
	 * @return 장부 정보
	 */
	@PostMapping("/accounts")
	public BaseResponse createAccount() {
		return BaseResponse.ok(accountService.createAccount());
	}
	
	/**
	 * 장부를 삭제한다.
	 * 
	 * @return 장부 삭제
	 */
	@DeleteMapping("/accounts/{accountId}")
	public BaseResponse deleteAccount(@PathVariable Long accountId) {
		return BaseResponse.ok(accountService.deleteAccount(accountId));
	}
}