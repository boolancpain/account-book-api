package com.fyo.accountbook.global.enums;

import com.fyo.accountbook.domain.category.Category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 장부의 기본 카테고리 정의
 * 
 * @author boolancpain
 */
@Getter
@RequiredArgsConstructor
public enum DefaultCategory {
	FOOD("식비", 1),
	CAFE("카페", 2),
	SHOPPING("쇼핑", 3),
	ETC("기타", 4)
	;
	
	private final String alias;
	private final int sequence;
	
	public Category toCategory(Long accountId) {
		return Category.builder()
				.accountId(accountId)
				.alias(this.alias)
				.sequence(this.sequence)
				.build();
	}
}