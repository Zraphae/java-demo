package cn.enn.test.classloader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Bar {

	public Bar(String a, String b) {
		log.info("Bar Constructor >>> {} {}", a, b);
	}

	public void printCL() {
		log.info("Bar ClassLoader: {}", Bar.class.getClassLoader());
	}
}
