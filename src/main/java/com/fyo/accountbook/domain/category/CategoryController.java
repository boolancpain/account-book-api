package com.fyo.accountbook.domain.category;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fyo.accountbook.domain.category.response.CategoryInfo;

import lombok.RequiredArgsConstructor;

/**
 * 카테고리 Controller
 * 
 * @author boolancpain
 */
@RestController
@RequiredArgsConstructor
public class CategoryController {
	private final CategoryService categoryService;
	
	/**
	 * 장부의 카테고리 목록을 조회한다.
	 * 
	 * @param accountId
	 * @return 카테고리 목록
	 */
	@GetMapping("/accounts/{accountId}/categories")
	public List<CategoryInfo> getCategories(@PathVariable Long accountId) {
		return categoryService.getCategories(accountId);
	}
}