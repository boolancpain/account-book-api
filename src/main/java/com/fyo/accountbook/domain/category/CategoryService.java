package com.fyo.accountbook.domain.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fyo.accountbook.domain.account.AccountError;
import com.fyo.accountbook.domain.category.response.CategoryInfo;
import com.fyo.accountbook.domain.member.Member;
import com.fyo.accountbook.domain.member.MemberError;
import com.fyo.accountbook.domain.member.MemberRepository;
import com.fyo.accountbook.global.error.CustomException;
import com.fyo.accountbook.global.util.SecurityUtils;

import lombok.RequiredArgsConstructor;

/**
 * 카테고리 Service
 * 
 * @author boolancpain
 */
@Service
@RequiredArgsConstructor
public class CategoryService {
	private final CategoryRepository categoryRepository;
	private final MemberRepository memberRepository;
	
	/**
	 * 장부의 카테고리 목록을 조회한다.
	 * 
	 * @param accountId
	 * @return 카테고리 목록
	 */
	public List<CategoryInfo> getCategories(Long accountId) {
		// 현재 로그인 회원 조회
		Member member = memberRepository.findById(SecurityUtils.getCurrentMemberId())
				.orElseThrow(() -> new CustomException(MemberError.NOT_FOUND_MEMBER));
		
		// 장부 보유 유무와 장부 ID가 일치하는지 체크
		if(member.getAccount() == null || member.getAccount().getId() != accountId) {
			// 404
			throw new CustomException(AccountError.NOT_FOUND_ACCOUNT);
		}
		
		return member.getAccount().getCategories().stream()
				.map(category -> CategoryInfo.builder()
						.categoryId(category.getId())
						.alias(category.getAlias())
						.build())
				.collect(Collectors.toList());
	}
}