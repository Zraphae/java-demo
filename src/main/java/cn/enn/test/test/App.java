package cn.enn.test.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

	public static void main(String[] args) {

		Integer a = 3;
		Integer b = 3;

		Integer c = 312;
		Integer d = 312;
		
		Integer e = 1;
		Integer f = 2;
		
		Long g = 3L;
		
		log.info("str: {}", a == b);
		log.info("str: {}", c == d);
		log.info("str: {}", a == (e + f));
		log.info("str: {}", a.equals(e + f));
		log.info("str: {}", g == (e + f));
		log.info("str: {}", g.equals((e + f)));
	}

}
