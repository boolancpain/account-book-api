package com.fyo.accountbook.domain.member;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fyo.accountbook.domain.member.request.OAuthRequest;
import com.fyo.accountbook.domain.member.response.MemberInfo;
import com.fyo.accountbook.domain.member.response.TokenResponse;

import lombok.RequiredArgsConstructor;

/**
 * 회원 Controller
 * 
 * @author boolancpain
 */
@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	
	/*
	 * oAuth2 인증 요청
	 */
	@PostMapping("/login/oauth2")
	public TokenResponse oAuth2Login(@Valid @RequestBody OAuthRequest oAuthRequest) {
		return memberService.oAuth2Login(oAuthRequest);
	}
	
	/**
	 * 로그인 회원의 정보 조회
	 */
	@GetMapping("/me")
	public MemberInfo getMyInfo() {
		return memberService.getMyInfo();
	}
	
	/**
	 * access token 재발행
	 */
	@GetMapping("/reissue")
	public TokenResponse reissueAccessToken() {
		return memberService.reissueAccessToken();
	}
}