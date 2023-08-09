package com.fyo.accountbook.domain.category.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 카테고리 정보
 * 
 * @author boolancpain
 */
@Getter
@Builder
@AllArgsConstructor
public class CategoryInfo {
	private Long categoryId;
	private String alias;
}