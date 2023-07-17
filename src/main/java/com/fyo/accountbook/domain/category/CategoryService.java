package com.fyo.accountbook.domain.category;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fyo.accountbook.domain.account.Account;
import com.fyo.accountbook.domain.account.AccountError;
import com.fyo.accountbook.domain.category.request.CategoryRequest;
import com.fyo.accountbook.domain.category.response.CategoryInfo;
import com.fyo.accountbook.domain.member.Member;
import com.fyo.accountbook.domain.member.MemberError;
import com.fyo.accountbook.domain.member.MemberRepository;
import com.fyo.accountbook.global.error.CustomException;
import com.fyo.accountbook.global.response.BaseResponse;
import com.fyo.accountbook.global.util.MessageUtils;
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
	
	/**
	 * 장부의 카테고리 하나를 삭제한다.
	 * 
	 * @param accountId : 장부 id
	 * @param categoryId : 카테고리 id
	 * @return 삭제 결과
	 */
	public BaseResponse deleteCategory(Long accountId, Long categoryId) {
		// 현재 로그인 회원 조회
		Member member = memberRepository.findById(SecurityUtils.getCurrentMemberId())
				.orElseThrow(() -> new CustomException(MemberError.NOT_FOUND_MEMBER));
		
		// 장부 보유 유무와 장부 ID가 일치하는지 체크
		Account account = member.getAccount();
		if(account == null || account.getId() != accountId) {
			// 404
			throw new CustomException(AccountError.NOT_FOUND_ACCOUNT);
		}
		
		// 카테고리 조회
		Category targetCategory = categoryRepository.findByIdAndAccountId(categoryId, accountId)
				.orElseThrow(() -> new CustomException(CategoryError.NOT_FOUND_CATEGORY));
		
		// 장부에 포함되어 있는 카테고리인지 체크
		if(!account.getCategories().contains(targetCategory)) {
			throw new CustomException(CategoryError.NOT_FOUND_CATEGORY);
		}
		
		// 카테고리 삭제
		categoryRepository.delete(targetCategory);
		
		return BaseResponse.builder()
				.code("ok")
				.message(MessageUtils.getMessage("category.delete"))
				.build();
	}
	
	/**
	 * 카테고리를 생성한다.
	 * 
	 * @param accountId : 장부 id
	 * @param categoryRequest : 카테고리 생성 정보
	 * @return 생성된 카테고리
	 */
	public CategoryInfo createCategory(Long accountId, CategoryRequest categoryRequest) {
		// 현재 로그인 회원 조회
		Member member = memberRepository.findById(SecurityUtils.getCurrentMemberId())
				.orElseThrow(() -> new CustomException(MemberError.NOT_FOUND_MEMBER));
		
		// 장부 보유 유무와 장부 ID가 일치하는지 체크
		Account account = member.getAccount();
		if(account == null || account.getId() != accountId) {
			// 404
			throw new CustomException(AccountError.NOT_FOUND_ACCOUNT);
		}
		
		// 이미 입력된 카테고리 설명인지 체크
		if(account.existsCategoryAlias(categoryRequest.getAlias())) {
			// 400
			throw new CustomException(CategoryError.EXISTS_CATEGORY);
		}
		
		// 카테고리 생성
		Category category = Category.builder()
				.alias(categoryRequest.getAlias())
				.sequence(account.getCategories().size())
				.build();
		
		// 장부에 추가
		account.getCategories().add(category);
		
		// 저장
		category = categoryRepository.save(category);
		
		return CategoryInfo.builder()
				.categoryId(category.getId())
				.alias(category.getAlias())
				.build();
	}
	
	/**
	 * 카테고리를 수정한다.
	 * 
	 * @param accountId : 장부 id
	 * @param categoryId : 카테고리 id
	 * @param categoryRequest : 카테고리 수정 정보
	 * @return 수정된 카테고리
	 */
	@Transactional
	public CategoryInfo updateCategory(Long accountId, Long categoryId, CategoryRequest categoryRequest) {
		// 현재 로그인 회원 조회
		Member member = memberRepository.findById(SecurityUtils.getCurrentMemberId())
				.orElseThrow(() -> new CustomException(MemberError.NOT_FOUND_MEMBER));
		
		// 장부 보유 유무와 장부 ID가 일치하는지 체크
		Account account = member.getAccount();
		if(account == null || account.getId() != accountId) {
			// 404
			throw new CustomException(AccountError.NOT_FOUND_ACCOUNT);
		}
		
		// 카테고리 조회
		Category targetCategory = categoryRepository.findByIdAndAccountId(categoryId, accountId)
				.orElseThrow(() -> new CustomException(CategoryError.NOT_FOUND_CATEGORY));
		
		// 이미 입력된 카테고리 설명인지 체크
		if(account.existsCategoryAlias(categoryRequest.getAlias())) {
			// 400
			throw new CustomException(CategoryError.EXISTS_CATEGORY);
		}
		
		// alias 수정
		targetCategory.updateAlias(categoryRequest.getAlias());
		
		return CategoryInfo.builder()
				.categoryId(targetCategory.getId())
				.alias(targetCategory.getAlias())
				.build();
	}
}