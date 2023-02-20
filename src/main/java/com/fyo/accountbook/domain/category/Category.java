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
	
	@Column(length = 10)
	private String alias;
	
	@Column(nullable = false)
	private int sequence;
}