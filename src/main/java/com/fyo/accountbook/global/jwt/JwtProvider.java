package com.fyo.accountbook.global.jwt;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.fyo.accountbook.domain.member.Member;
import com.fyo.accountbook.global.common.AuthError;
import com.fyo.accountbook.global.common.CustomException;
import com.fyo.accountbook.global.property.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 * jwt provider
 * 
 * @author boolancpain
 */
@Component
@Slf4j
public class JwtProvider {
	private final Key key;
	private final JwtProperties jwtProperties;
	
	private static final String AUTHORITIES_KEY = "auth";
	
	public JwtProvider(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
		this.key = Keys.hmacShaKeyFor(this.jwtProperties.getSecret().getBytes());
	}
	
	/**
	 * Access token 생성
	 * 
	 * @param Member
	 * @return accessToken
	 */
	public String generateAccessToken(Member member) {
		return Jwts.builder()
				.setSubject(String.valueOf(member.getId()))
				.signWith(key, SignatureAlgorithm.HS512)
				.setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpirationTime()))
				.claim(AUTHORITIES_KEY, member.getRole().name())
				.compact();
	}
	
	/**
	 * Refresh token 생성
	 * 
	 * @return refreshToken
	 */
	public String generateRefreshToken() {
		return Jwts.builder()
				.setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenExpirationTime()))
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();
	}
	
	/**
	 * Access token에서 Authentication 인증 객체를 추출
	 * 
	 * @param accessToken
	 * @return Authentication 인증 객체
	 */
	public Authentication getAuthentication(String accessToken) {
		if(this.isValidToken(accessToken)) {
			Claims claims = this.getClaims(accessToken);
			List<GrantedAuthority> authorities = Arrays.stream(new String[] {claims.get(AUTHORITIES_KEY, String.class)})
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
			User principal = new User(claims.getSubject(), "", authorities);
			return new UsernamePasswordAuthenticationToken(principal, "", authorities);
		} else {
			throw new CustomException(AuthError.INVALID_ACCESS_TOKEN);
		}
	}
	
	/**
	 * token의 유효성 검증 결과 리턴
	 * 
	 * @param token
	 * @return 유효성 검증 결과
	 */
	public boolean isValidToken(String token) {
		try {
			return this.getClaims(token) != null;
		} catch (SecurityException e) {
			log.debug("Invalid JWT signature.");
		} catch (MalformedJwtException e) {
			log.debug("Invalid JWT token.");
		} catch (ExpiredJwtException e) {
			log.debug("Expired JWT token.");
		} catch (UnsupportedJwtException e) {
			log.debug("Unsupported JWT token.");
		} catch (IllegalArgumentException e) {
			log.debug("JWT token compact of handler are invalid.");
		}
		
		return false;
	}
	
	/**
	 * token에서 claims를 추출
	 * 
	 * @param token
	 * @return claims
	 */
	public Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
	
	/**
	 * refresh token의 재발급 가능 여부 리턴(설정된 만료일 이내인지) 
	 * 
	 * @param refreshToken
	 * @return refresh token 재발급 가능 여부
	 */
	public boolean isReissuableRefreshToken(String refreshToken) {
		Claims claims = this.getClaims(refreshToken);
		LocalDateTime expiration = claims.getExpiration()
				.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
		return expiration.minusDays(jwtProperties.getReissuableDayBeforeExpirationDay()).isBefore(LocalDateTime.now());
	}
}