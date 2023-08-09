package com.fyo.accountbook.domain.transaction;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fyo.accountbook.domain.transaction.request.TransactionRequest;
import com.fyo.accountbook.global.response.BaseResponse;

import lombok.RequiredArgsConstructor;

/**
 * 거래내역 Controller
 * 
 * @author boolancpain
 */
@RestController
@RequiredArgsConstructor
public class TransactionController {
	private final TransactionService transactionService;
	/**
	 * 거래내역을 조회 한다.
	 * 
	 * @param accountId : 장부 id
	 * @param transactionRequest : 거래내역 요청 정보
	 * @return 거래내역
	 */
	@GetMapping("/accounts/{accountId}/transactions")
	public BaseResponse getTransactions(@PathVariable Long accountId, @Valid @RequestBody TransactionRequest transactionRequest) {
		return BaseResponse.ok(transactionService.getTransactions(accountId, transactionRequest));
	}
}