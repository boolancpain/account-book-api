package com.fyo.accountbook.domain.member;

import java.util.Base64;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyo.accountbook.domain.member.MemberRequestDto.OAuthRequest;
import com.fyo.accountbook.domain.member.MemberResponseDto.KakaoOAuth2Token;
import com.fyo.accountbook.domain.member.MemberResponseDto.KakaoOAuth2UserInfo;
import com.fyo.accountbook.domain.member.MemberResponseDto.MemberInfo;
import com.fyo.accountbook.domain.member.MemberResponseDto.TokenResponse;
import com.fyo.accountbook.global.common.AuthError;
import com.fyo.accountbook.global.common.CustomException;
import com.fyo.accountbook.global.jwt.JwtProvider;
import com.fyo.accountbook.global.property.JwtProperties;
import com.fyo.accountbook.global.property.OAuth2Properties;
import com.fyo.accountbook.global.util.CookieUtils;
import com.fyo.accountbook.global.util.HttpHeaderUtils;
import com.fyo.accountbook.global.util.ServletUtils;

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
	private final ObjectMapper objectMapper;
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
					
					// cookid에 refresh token add
					// millisecond to second
					int maxAge = Long.valueOf(jwtProperties.getRefreshTokenExpirationTime() / 1000).intValue();
					CookieUtils.addCookie("refresh_token", refreshToken, maxAge);
					
					// TODO redis에 refresh token 저장
					
					return TokenResponse.builder()
							.accessToken(accessToken)
							.build();
				} catch (JsonProcessingException e) {
					throw new CustomException(MemberError.FAILED_LOGIN);
				}
			default:
				throw new CustomException(MemberError.INVALID_PROVIDER);
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
				.orElseThrow(() -> new CustomException(AuthError.UNAUTHORIZED));
		
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
		// 1. cookie에서 refresh token을 가져온다
		String refreshToken = CookieUtils.getCookie("refresh_token")
				.map(Cookie::getValue)
				.orElseThrow(() -> new CustomException(AuthError.NOT_FOUND_REFRESH_TOKEN));
		
		// 2. cookie에 담겨있던 refresh token의 유효성을 검증한다
		if(!jwtProvider.isValidToken(refreshToken)) {
			throw new CustomException(AuthError.INVALID_REFRESH_TOKEN);
		}
		
		// 3. http header에서 access token을 가져온다
		String accessToken = Optional.ofNullable(HttpHeaderUtils.getTokenFromHeader(ServletUtils.getRequest()))
				.orElseThrow(() -> new CustomException(AuthError.NOT_FOUND_ACCESS_TOKEN));
		
		// 4. access token에서 회원 정보를 추출해서 회원 정보를 검색한다
		Member member = Optional.ofNullable(jwtProvider.getClaims(accessToken))
				.map(claims -> claims.getSubject())
				.map(subject -> memberRepository.findById(Long.valueOf(subject))
						.orElseThrow(() -> new CustomException(AuthError.INVALID_ACCESS_TOKEN)))
				.orElseThrow(() -> new CustomException(AuthError.INVALID_ACCESS_TOKEN));
		
		// 5. TODO 저장소에서 회원 id로 refresh token 조회
		// 6. TODO 저장소 refresh token과 쿠키의 refresh token 일치하는지 검증
		
		// 7. refresh token 재발행 가능한지 체크(만료일로부터 n일전)
		if(jwtProvider.isReissuableRefreshToken(refreshToken)) {
			// 7-1. refresh token 재발행
			String newRefreshToken = jwtProvider.generateRefreshToken();
			
			// 7-2. cookie에 기존 refresh token 삭제
			CookieUtils.deleteCookie("refresh_token");
			
			// 7-3. 새로운 refresh token을 cookie에 추가
			int maxAge = Long.valueOf(jwtProperties.getRefreshTokenExpirationTime() / 1000).intValue();
			CookieUtils.addCookie("refresh_token", newRefreshToken, maxAge);
			
			// 7-4. TODO 저장소에 refresh token 교체
		}
		
		// 8. access token 재발행 및 리턴
		return TokenResponse.builder()
				.accessToken(jwtProvider.generateAccessToken(member))
				.build();
	}
}