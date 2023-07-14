package com.fyo.accountbook.domain.category;

import java.util.Optional;

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
	
	/**
	 * 장부 id와 카테고리 id로 카테고리를 조회한다.
	 * 
	 * @param id : 카테고리 id
	 * @param accountId : 장부 id
	 * @return 카테고리
	 */
	public Optional<Category> findByIdAndAccountId(Long id, Long accountId);
}