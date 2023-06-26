package com.fyo.accountbook.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 카테고리 Repository
 * 
 * @author boolancpain
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
	/**
	 * 장부 ID로 해당 카테고리들을 삭제한다.
	 * 
	 * @param accountId : 장부 ID
	 */
	public void deleteAllByAccountId(Long accountId);
}