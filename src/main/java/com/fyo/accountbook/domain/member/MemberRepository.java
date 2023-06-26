package com.fyo.accountbook.domain.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 회원 Repository
 * 
 * @author boolancpain
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
	/**
	 * provider, providerId 로 회원 검색
	 * 
	 * @param email
	 */
	public Optional<Member> findByProviderAndProviderId(MemberProvider provider, String providerId);
}