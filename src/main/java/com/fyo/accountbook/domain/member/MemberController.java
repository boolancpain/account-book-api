package com.fyo.accountbook.domain.member;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fyo.accountbook.domain.member.request.OAuthRequest;
import com.fyo.accountbook.domain.member.response.MemberInfo;
import com.fyo.accountbook.domain.member.response.TokenResponse;

import lombok.RequiredArgsConstructor;

/**
 * Member Controller
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
	public ResponseEntity<TokenResponse> oAuth2Login(@Valid @RequestBody OAuthRequest oAuthRequest) {
		return ResponseEntity.ok(memberService.oAuth2Login(oAuthRequest));
	}
	
	/**
	 * 로그인 회원의 정보 조회
	 */
	@GetMapping("/me")
	public ResponseEntity<MemberInfo> getMyInfo() {
		return ResponseEntity.ok(memberService.getMyInfo());
	}
	
	/**
	 * access token 재발행
	 */
	@GetMapping("/reissue")
	public ResponseEntity<TokenResponse> reissueAccessToken() {
		return ResponseEntity.ok(memberService.reissueAccessToken());
	}
}