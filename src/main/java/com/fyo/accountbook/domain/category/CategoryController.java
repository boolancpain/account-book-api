package com.fyo.accountbook.domain.category;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fyo.accountbook.domain.category.request.CategoryCreate;
import com.fyo.accountbook.domain.category.response.CategoryInfo;
import com.fyo.accountbook.global.response.BaseResponse;

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
	 * @param accountId : 장부 id
	 * @return 카테고리 목록
	 */
	@GetMapping("/accounts/{accountId}/categories")
	public List<CategoryInfo> getCategories(@PathVariable Long accountId) {
		return categoryService.getCategories(accountId);
	}
	
	/**
	 * 장부의 카테고리 하나를 삭제한다.
	 * 
	 * @param accountId : 장부 id
	 * @param categoryId : 카테고리 id
	 * @return 삭제 결과
	 */
	@DeleteMapping("/accounts/{accountId}/categories/{categoryId}")
	public BaseResponse deleteCategory(@PathVariable Long accountId, @PathVariable Long categoryId) {
		return categoryService.deleteCategory(accountId, categoryId);
	}
	
	/**
	 * 카테고리를 생성한다.
	 * 
	 * @param accountId
	 * @param categoryCreate
	 * @return 생성된 카테고리
	 */
	@PostMapping("/accounts/{accountId}/categories")
	public CategoryInfo createCategory(@PathVariable Long accountId, @Valid @RequestBody CategoryCreate categoryCreate) {
		return categoryService.createCategory(accountId, categoryCreate);
	}
}