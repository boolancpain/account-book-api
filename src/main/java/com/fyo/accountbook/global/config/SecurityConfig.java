package com.fyo.accountbook.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.fyo.accountbook.domain.member.MemberRole;

import lombok.RequiredArgsConstructor;

/**
 * Spring Security 설정 클래스
 * 
 * @author boolancpain
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/favicon.ico");
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.csrf()
					.disable() // csrf 사용 x
				.cors()
				.and()
					.sessionManagement()
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // stateless
				.and()
					.formLogin()
						.disable() // form login 사용 x
					.httpBasic()
						.disable() // 기본 로그인 화면 비활성화
					.authorizeRequests()
						.antMatchers("/login/**", "/test").permitAll()
						.antMatchers("/members/**").hasRole(MemberRole.ADMIN.name())
						.anyRequest().authenticated();
		
		return httpSecurity.build();
	}
}