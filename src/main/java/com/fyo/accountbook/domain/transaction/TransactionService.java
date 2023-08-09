package com.fyo.accountbook.domain.transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fyo.accountbook.domain.account.Account;
import com.fyo.accountbook.domain.account.AccountError;
import com.fyo.accountbook.domain.member.Member;
import com.fyo.accountbook.domain.member.MemberError;
import com.fyo.accountbook.domain.member.MemberRepository;
import com.fyo.accountbook.domain.transaction.request.TransactionRequest;
import com.fyo.accountbook.domain.transaction.response.TransactionInfo;
import com.fyo.accountbook.global.error.CustomException;
import com.fyo.accountbook.global.util.DateUtils;
import com.fyo.accountbook.global.util.SecurityUtils;

import lombok.RequiredArgsConstructor;

/**
 * 거래내역 Service
 * 
 * @author boolancpain
 */
@Service
@RequiredArgsConstructor
public class TransactionService {
	private final TransactionRepository transactionRepository;
	private final MemberRepository memberRepository;
	
	/**
	 * 장부의 거래내역을 조회한다.
	 * 
	 * @param accountId : 장부 id
	 * @param transactionRequest : 거래내역 요청 정보
	 * @return 거래내역
	 */
	public List<TransactionInfo> getTransactions(Long accountId, TransactionRequest transactionRequest) {
		// 현재 로그인 회원 조회
		Member member = memberRepository.findById(SecurityUtils.getCurrentMemberId())
				.orElseThrow(() -> new CustomException(MemberError.NOT_FOUND_MEMBER));
		
		// 장부 보유 유무와 장부 ID가 일치하는지 체크
		Account account = member.getAccount();
		if(account == null || account.getId() != accountId) {
			// 404
			throw new CustomException(AccountError.NOT_FOUND_ACCOUNT);
		}
		
		LocalDateTime startDate = DateUtils.toLocalDateTime(transactionRequest.getStartDate(), 0, 0, 0);
		LocalDateTime endDate = DateUtils.toLocalDateTime(transactionRequest.getEndDate(), 23, 59, 59);
		
		// 검색 시작일~종료일 범위 체크
		if(startDate.isAfter(endDate)) {
			// 400
			throw new CustomException(TransactionError.INVALID_RANGE_DATE);
		}
		
		return transactionRepository.findAllByAccountIdAndTransactedDateBetween(accountId, startDate, endDate).stream()
				.map(transaction -> TransactionInfo.builder()
						.transactionId(transaction.getId())
						.memberId(transaction.getMember().getId())
						.categoryId(transaction.getCategory().getId())
						.cost(transaction.getCost())
						.remark(transaction.getRemark())
						.transactedDate(DateUtils.toString(transaction.getTransactedDate()))
						.build())
				.collect(Collectors.toList());
	}
}