package cn.enn.test.common;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class ThreadLocalApp {
	
	private static ThreadLocal<Random> threadLocalRam = ThreadLocal.withInitial(ThreadLocalApp::createRandom);
	
	public static void main(String[] args) {
		
		for (int a = 0; a < 10 ; a++) {
			new Thread(()->
				log.info("== {}", threadLocalRam.get())
			).start(); 
		}
		
	}
	
	private static Random createRandom() {
		return new Random();
	}
	
}
