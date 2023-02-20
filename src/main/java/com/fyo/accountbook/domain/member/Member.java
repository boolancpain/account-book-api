package com.fyo.accountbook.domain.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fyo.accountbook.domain.account.Account;
import com.fyo.accountbook.global.common.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 Entity
 * 
 * @author boolancpain
 */
@Entity
@Table(name = "member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Member extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 255, nullable = false, unique = true)
	private String email;
	
	@Column(length = 255, nullable = false)
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MemberRole role;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private Account account;
}