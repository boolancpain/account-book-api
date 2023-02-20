package com.fyo.accountbook.domain.transaction;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fyo.accountbook.domain.account.Account;
import com.fyo.accountbook.domain.category.Category;
import com.fyo.accountbook.domain.member.Member;
import com.fyo.accountbook.global.common.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 가계부 거래내역 Entity
 * 
 * @author boolancpain
 */
@Entity
@Table(name = "transaction")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Transaction extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private Account account;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;
	
	@Column(nullable = false)
	private int cost;
	
	@Column(length = 255)
	private String remark;
	
	@Column(columnDefinition = "DATETIME(0) default CURRENT_TIMESTAMP")
	private LocalDateTime transactedDate;
}