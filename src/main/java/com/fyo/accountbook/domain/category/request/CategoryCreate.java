package com.fyo.accountbook.domain.category.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 카테고리 생성 요청 객체
 * 
 * @author boolancpain
 */
@Getter
@Setter
public class CategoryCreate {
	private String alias;
}