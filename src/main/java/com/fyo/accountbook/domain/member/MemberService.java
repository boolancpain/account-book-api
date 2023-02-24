package com.fyo.accountbook.domain.member;

import java.util.Base64;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.Cookie;
import javax.transaction.Transactional;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyo.accountbook.domain.member.MemberRequestDto.OAuthRequest;
import com.fyo.accountbook.domain.member.MemberResponseDto.KakaoOAuth2Token;
import com.fyo.accountbook.domain.member.MemberResponseDto.KakaoOAuth2UserInfo;
import com.fyo.accountbook.domain.member.MemberResponseDto.MemberInfo;
import com.fyo.accountbook.domain.member.MemberResponseDto.TokenResponse;
import com.fyo.accountbook.global.jwt.JwtProvider;
import com.fyo.accountbook.global.property.JwtProperties;
import com.fyo.accountbook.global.property.OAuth2Properties;
import com.fyo.accountbook.global.util.CookieUtils;

import lombok.RequiredArgsConstructor;

/**
 * 회원 서비스
 * 
 * @author boolancpain
 */
@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;
	private final JwtProperties jwtProperties;
	private final OAuth2Properties oAuth2Properties;
	
	/**
	 * OAuth2 인증 요청 및 로그인 처리(토큰 발행)
	 */
	@Transactional
	public TokenResponse oAuth2Login(String provider, OAuthRequest oAuthRequest) {
		MemberProvider thisProvider = MemberProvider.valueOf(provider.toUpperCase());
		
		switch(thisProvider) {
			case KAKAO:
				// kakao 토큰 받기 요청
				KakaoOAuth2Token kakaoOAuth2Token = WebClient.create("https://kauth.kakao.com/oauth/token")
					.post()
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.accept(MediaType.APPLICATION_JSON)
					.body(BodyInserters.fromFormData("grant_type", "authorization_code")
							.with("client_id", oAuth2Properties.getKakao().getClientId())
							.with("redirect_uri", oAuthRequest.getAuthorizedRedirectUri())
							.with("code", oAuthRequest.getCode())
					)
					.retrieve()
					.bodyToMono(KakaoOAuth2Token.class)
					.block();
				
				String idToken = kakaoOAuth2Token.getId_token();
				String payload = new String(Base64.getDecoder().decode(idToken.split("[.]")[1]));
				
				// 존재하지 않는 속성 무시
				ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				try {
					KakaoOAuth2UserInfo userInfo = objectMapper.readValue(payload, KakaoOAuth2UserInfo.class);
					
					// 회원을 조회하거나 생성
					Member member = memberRepository.findByProviderAndProviderId(thisProvider, userInfo.getSub())
							.orElseGet(() -> Member.builder()
									.provider(MemberProvider.KAKAO)
									.providerId(userInfo.getSub())
									.name(userInfo.getNickname())
									.role(MemberRole.USER)
									.build());
					
					memberRepository.save(member);
					
					// access token 생성
					String accessToken = jwtProvider.generateAccessToken(member);
					// refresh token 생성
					String refreshToken = jwtProvider.generateRefreshToken();
					
					// millisecond to second
					int maxAge = Long.valueOf(jwtProperties.getRefreshTokenExpirationTime() / 1000).intValue();
					CookieUtils.addCookie("refresh_token", refreshToken, maxAge);
					
					// TODO redis에 refresh token 저장
					
					return TokenResponse.builder()
							.accessToken(accessToken)
							.build();
				} catch (JsonProcessingException e) {
					throw new RuntimeException("회원 정보 파싱 에러");
				}
			default:
				throw new RuntimeException("Unsupport Provider!");
		}
	}
	
	/**
	 * 로그인 회원의 정보 조회
	 * 
	 * @return 로그인 회원 정보
	 */
	public MemberInfo getMyInfo() {
		// SecurityContextHolder에서 Authentication 객체 추출
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Member member = memberRepository.findById(Long.valueOf(authentication.getName()))
				.orElseThrow(() -> new EntityNotFoundException("zzz"));
		
		return MemberInfo.builder()
				.name(member.getName())
				.build();
	}
	
	/**
	 * access token 재발행
	 * 
	 * @return 재발행 된 access token
	 */
	public TokenResponse reissueAccessToken() {
		// 1. SecurityContextHolder로부터 Authentication 객체를 가져옴
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		// 2. Authentication 객체에서 name attribute(member.id)로 회원 조회
		Member member = memberRepository.findById(Long.valueOf(authentication.getName()))
				.orElseThrow(() -> new RuntimeException("not found member"));
		
		// 3. cookie에서 refresh token 꺼냄
		String refreshToken = CookieUtils.getCookie("refresh_token")
				.map(Cookie::getValue)
				.orElseThrow(() -> new RuntimeException("not exists refresh token"));
		
		// 4. refresh token 유효성 검증
		if(!jwtProvider.isValidToken(refreshToken))
			throw new RuntimeException("Invalid refresh token");
		
		// 5. refresh token 재발행 가능한지 체크(만료일로부터 n일전)
		if(jwtProvider.isReissuableRefreshToken(refreshToken)) {
			// 5-1. refresh token 재발행
			String newRefreshToken = jwtProvider.generateRefreshToken();
			
			// 5-2. cookie에 기존 refresh token 삭제
			CookieUtils.deleteCookie("refresh_token");
			
			// 5-3. 새로운 refresh token을 cookie에 추가
			int maxAge = Long.valueOf(jwtProperties.getRefreshTokenExpirationTime() / 1000).intValue();
			CookieUtils.addCookie("refresh_token", newRefreshToken, maxAge);
			
			// 5-4. TODO redis에 refresh token 교체
		}
		
		// 6. access token 재발행 및 리턴
		return TokenResponse.builder()
				.accessToken(jwtProvider.generateAccessToken(member))
				.build();
	}
}