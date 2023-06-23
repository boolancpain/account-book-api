package com.fyo.accountbook.domain.member;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fyo.accountbook.domain.member.response.KakaoOAuth2Token;
import com.fyo.accountbook.global.common.CustomException;
import com.fyo.accountbook.global.property.KakaoProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * OAuth2 관련 서비스
 * 
 * @author boolancpain
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2Service {
	private final RestTemplate restTemplate;
	private final KakaoProperties kakaoProperties;
	
	/**
	 * 카카오 로그인 후 전송받은 정보로 카카오에서 토큰 및 회원 정보를 가져온다.
	 * 
	 * @param authorizationCode : 카카오 인가 코드
	 * @param redirectUri : 인가 코드가 리다이렉트된 URI
	 * @return 토큰과 사용자 인증 정보
	 */
	public KakaoOAuth2Token kakaoLogin(String authorizationCode, String redirectUri) {
		try {
			// kakao 토큰 받기 요청
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("grant_type", kakaoProperties.getGrantType());
			params.add("client_id", kakaoProperties.getClientId());
			params.add("code", authorizationCode);
			params.add("redirect_uri", redirectUri);
			return restTemplate.postForObject(kakaoProperties.getUrl(), params, KakaoOAuth2Token.class);
		} catch (HttpClientErrorException ce) {
			log.error("http client error := {}", ce.getMessage());
			throw new CustomException(MemberError.INVALID_AUTHORIZATION_CODE);
		} catch (HttpServerErrorException se) {
			log.error("http server error := {}", se.getMessage());
			throw new CustomException(MemberError.FAILED_LOGIN);
		} catch (Exception e) {
			log.error("kakao login call error := {}", e.getMessage());
			throw new CustomException(MemberError.FAILED_LOGIN);
		}
	}
}