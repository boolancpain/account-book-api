package com.fyo.accountbook.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 회원 권한 enum
 * 
 * @author boolancpain
 */
@Getter
@AllArgsConstructor
public enum MemberRole {
	USER,
	ADMIN;
}