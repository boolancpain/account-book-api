package com.fyo.accountbook.domain.member;

import com.fyo.accountbook.global.validator.ValidateEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * OAuth2 provider enum
 * 
 * @author boolancpain
 */
@Getter
@AllArgsConstructor
public enum MemberProvider implements ValidateEnum {
	KAKAO;
}