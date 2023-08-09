package com.fyo.accountbook.global.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 
 * 
 * @author boolancpain
 */
public class DateUtils {
	/**
	 * 날짜형태의 문자열을 LocalDateTime 객체로 변환
	 * 
	 * @param date("yyyy-MM-dd")
	 * @param hour
	 * @param minute
	 * @param second
	 */
	public static LocalDateTime toLocalDateTime(String date, int hour, int minute, int second) {
		return LocalDateTime.of(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd")), LocalTime.of(hour, minute, second));
	}
	
	/**
	 * LocalDateTime 객체를 문자열로 변환
	 * 
	 * @param date
	 * @return 날짜 문자열
	 */
	public static String toString(LocalDateTime date) {
		return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}