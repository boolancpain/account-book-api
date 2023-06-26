package com.fyo.accountbook.domain.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 거래내역 Repository
 * 
 * @author boolancpain
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	/**
	 * 장부 ID로 해당 거래내역들을 삭제한다.
	 * 
	 * @param accountId : 장부 ID
	 */
	public void deleteAllByAccountId(Long accountId);
	
	/**
	 * 장부 ID와 회원 ID로 거래내역의 회원 ID를 업데이트 한다.
	 * 
	 * @param accountId : 장부 ID
	 * @param memberId : 회원 ID
	 */
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE transaction SET member_id = null WHERE member_id = :memberId AND account_id = :accountId", nativeQuery = true)
	public void updateAllByAccountIdAndMemberId(@Param("accountId") Long accountId, @Param("memberId") Long memberId);
}