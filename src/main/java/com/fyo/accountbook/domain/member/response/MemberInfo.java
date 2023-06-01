package com.fyo.accountbook.domain.member.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 회원 정보
 * 
 * @author boolancpain
 */
@Getter
@Builder
@AllArgsConstructor
public class MemberInfo {
	private String name;
}