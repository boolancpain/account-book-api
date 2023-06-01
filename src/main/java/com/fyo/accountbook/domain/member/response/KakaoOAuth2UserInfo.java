package com.fyo.accountbook.domain.member.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * kakao oAuth2 회원 정보 response
 * 
 * @author boolancpain
 */
@Getter
@Builder
@AllArgsConstructor
public class KakaoOAuth2UserInfo {
	private String sub;
	private String nickname;
}