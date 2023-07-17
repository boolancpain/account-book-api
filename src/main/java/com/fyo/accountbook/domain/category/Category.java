package com.fyo.accountbook.domain.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fyo.accountbook.global.common.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 지출 카테고리 Entity
 * 
 * @author boolancpain
 */
@Entity
@Table(name = "category")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Category extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private Long accountId;
	
	@Column(length = 10)
	private String alias;
	
	@Column(nullable = false)
	private int sequence;
	
	/**
	 * 카테고리 id로 동일한 객체인지 비교할 수 있도록 equals 오버라이딩
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Category ? this.id == ((Category) obj).getId() : false;
	}
	
	/**
	 * 카테고리 alias 수정
	 * 
	 * @param alias
	 */
	public void updateAlias(String alias) {
		this.alias = alias;
	}
}