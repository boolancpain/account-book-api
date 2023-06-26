package com.fyo.accountbook.domain.account;

import java.util.Arrays;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fyo.accountbook.domain.account.response.AccountInfo;
import com.fyo.accountbook.domain.category.CategoryRepository;
import com.fyo.accountbook.domain.member.Member;
import com.fyo.accountbook.domain.member.MemberError;
import com.fyo.accountbook.domain.member.MemberRepository;
import com.fyo.accountbook.domain.transaction.TransactionRepository;
import com.fyo.accountbook.global.enums.DefaultCategory;
import com.fyo.accountbook.global.error.CustomException;
import com.fyo.accountbook.global.response.BaseResponse;
import com.fyo.accountbook.global.util.MessageUtils;
import com.fyo.accountbook.global.util.SecurityUtils;

import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 장부 Service
 * 
 * @author boolancpain
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
	private final AccountRepository accountRepository;
	private final MemberRepository memberRepository;
	private final CategoryRepository categoryRepository;
	private final TransactionRepository transactionRepository;
	
	/**
	 * 내 장부를 조회한다.
	 * 
	 * @return 장부 정보
	 */
	public AccountInfo getMyAccount() {
		// 현재 로그인 회원 조회
		Member member = memberRepository.findById(SecurityUtils.getCurrentMemberId())
				.orElseThrow(() -> new CustomException(MemberError.NOT_FOUND_MEMBER));
		
		Long accountId = Optional.ofNullable(member.getAccount()).map(account -> account.getId())
				.orElseGet(() -> null);
		
		return AccountInfo.builder()
				.accountId(accountId)
				.build();
	}
	
	/**
	 * 장부를 생성한다.
	 * 
	 * @return 장부 정보
	 */
	@Transactional
	public AccountInfo createAccount() {
		// 현재 로그인 회원 조회
		Member member = memberRepository.findById(SecurityUtils.getCurrentMemberId())
				.orElseThrow(() -> new CustomException(MemberError.NOT_FOUND_MEMBER));
		
		// 장부 보유 유무 체크
		if(member.getAccount() != null) {
			// 409
			throw new CustomException(AccountError.ALREADY_EXISTS_ACCOUNT);
		}
		
		// 장부 생성
		Account account = Account.builder().build();
		
		// 장부 저장
		accountRepository.save(account);
		
		// 회원 정보 업데이트
		member.updateAccount(account);
		
		// 기본 카테고리 생성 후 저장
		Arrays.stream(DefaultCategory.values()).forEach(defaultCategory -> {
			categoryRepository.save(defaultCategory.toCategory(account.getId()));
		});
		
		return AccountInfo.builder()
				.accountId(account.getId())
				.build();
	}
	
	/**
	 * 장부를 삭제한다.
	 * 
	 * @param accountId : 장부 ID
	 * @return 삭제 결과
	 */
	@Transactional
	public BaseResponse deleteAccount(Long accountId) {
		// 현재 로그인 회원 조회
		Member member = memberRepository.findById(SecurityUtils.getCurrentMemberId())
				.orElseThrow(() -> new CustomException(MemberError.NOT_FOUND_MEMBER));
		
		// 회원의 장부
		Account account = member.getAccount();
		if(account == null || account.getId() != accountId) {
			// 404
			throw new CustomException(AccountError.NOT_FOUND_ACCOUNT);
		}
		
		// 장부의 회원 목록에서 해당 회원 제거
		account.removeMember(member.getId());
		
		// 회원의 장부 ID를 제거
		member.updateAccount(null);
		
		if(Collections.isEmpty(account.getMembers())) {
			// 해당 장부에 회원 목록이 존재하지 않는 경우
			// 카테고리 삭제
			log.debug("장부(accountId:{})의 카테고리 모두 삭제", accountId);
			categoryRepository.deleteAllByAccountId(accountId);
			
			// 거래내역 삭제
			log.debug("장부(accountId:{})의 거래내역 모두 삭제", accountId);
			transactionRepository.deleteAllByAccountId(accountId);
			
			// 장부 삭제
			log.debug("장부(accountId:{}) 삭제", accountId);
			accountRepository.deleteById(accountId);
		} else {
			// 해당 장부에 회원 목록이 존재하는 경우
			// 해당 회원의 거래내역 회원 ID null 처리
			log.debug("장부(accountId:{})에서 해당 회원(memberId:{})의 거래내역 모두 null 처리", accountId, member.getId());
			transactionRepository.updateAllByAccountIdAndMemberId(accountId, member.getId());
		}
		
		return BaseResponse.builder()
				.code("ok")
				.message(MessageUtils.getMessage("account.delete"))
				.build();
	}
}