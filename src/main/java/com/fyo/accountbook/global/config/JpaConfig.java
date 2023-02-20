package com.fyo.accountbook.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Jpa 설정 클래스
 * {@link com.fyo.beginning.global.entity.BaseTimeEntity} audit 사용을 위한 설정
 * 
 * @author boolancpain
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {

}