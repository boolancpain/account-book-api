package com.fyo.accountbook.domain.account;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fyo.accountbook.domain.category.Category;
import com.fyo.accountbook.domain.member.Member;
import com.fyo.accountbook.domain.transaction.Transaction;
import com.fyo.accountbook.global.common.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 가계부 계좌 Entity
 * 
 * @author boolancpain
 */
@Entity
@Table(name = "account")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Account extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
	private List<Member> members;
	
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
	private List<Transaction> transactions;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "accountId")
	private List<Category> categories;
	
	/**
	 * 장부의 회원 목록에서 회원을 제거한다.
	 * 
	 * @param memberId : 회원 ID
	 */
	public void removeMember(Long memberId) {
		this.members.removeIf(member -> member.getId() == memberId);
	}
	
	/**
	 * 카테고리의 설명값으로 존재하는 카테고리인지 체크한다.
	 * 
	 * @param alias : 카테고리 설명
	 * @return 카테고리 존재 여부
	 */
	public boolean existsCategoryAlias(String alias) {
		return this.categories.stream()
				.anyMatch(category -> category.getAlias().equalsIgnoreCase(alias));
	}
}