package cn.enn.test.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DateFormatApp {

	public static void main(String[] args) {
		
//		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
//		log.info(ZonedDateTime.now().format(pattern));
		
		log.info("str: {}", ZonedDateTime.now());
		log.info(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(ZonedDateTime.now()));
		LocalTime time = LocalTime.now();
		log.info("str: {}", time);
	}

}
