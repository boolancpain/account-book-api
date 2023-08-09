package com.fyo.accountbook.domain.category;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fyo.accountbook.domain.category.request.CategoryRequest;
import com.fyo.accountbook.global.response.BaseResponse;
import com.fyo.accountbook.global.util.MessageUtils;

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
	public BaseResponse getCategories(@PathVariable Long accountId) {
		return BaseResponse.ok(categoryService.getCategories(accountId));
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
		categoryService.deleteCategory(accountId, categoryId);
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
	@PostMapping("/accounts/{accountId}/categories")
	public BaseResponse createCategory(@PathVariable Long accountId, @Valid @RequestBody CategoryRequest categoryRequest) {
		return BaseResponse.ok(categoryService.createCategory(accountId, categoryRequest));
	}
	
	/**
	 * 카테고리를 수정한다.
	 * 
	 * @param accountId : 장부 id
	 * @param categoryId : 카테고리 id
	 * @param categoryRequest : 카테고리 수정 정보
	 * @return 수정된 카테고리
	 */
	@PutMapping("/accounts/{accountId}/categories/{categoryId}")
	public BaseResponse updateCategory(@PathVariable Long accountId, @PathVariable Long categoryId, @Valid @RequestBody CategoryRequest categoryRequest) {
		return BaseResponse.ok(categoryService.updateCategory(accountId, categoryId, categoryRequest));
	}
}