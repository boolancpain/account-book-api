package com.fyo.accountbook.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 장부 Repository
 * 
 * @author boolancpain
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

}