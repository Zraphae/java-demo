package cn.enn.test;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.util.Base64;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

	public static void test() {

		Map<String, String> map = System.getenv();
		System.out.println(map.get("ENN_FACEID_HOME"));
	}

	public static void main(String[] args) throws IOException {
		
		for (int a = 0; a < 10 ; a++) {
			new Thread(()->
				log.info("== {}", threadLocalRam.get())
			).start(); 
		}
		//sdf
//		System.out.println("==============");
//		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//			test();
//		}));
//
//		test();
//		decode();

	}
	
	private static Random createRandom() {
		return new Random();
	}
	
	public static ThreadLocal<Random> threadLocalRam = ThreadLocal.withInitial(App::createRandom);

	public static void encode() throws IOException {
		File imgPath = new File("D:/testspace/imgs/gas");
		int count = 0;
		for (File imgFile : imgPath.listFiles()) {
			byte[] imgArr = FileUtils.readFileToByteArray(imgFile);
			String imgBase64Str = Base64.encodeBase64URLSafeString(imgArr);
			FileUtils.writeStringToFile(new File("D:/testspace/" + count++), imgBase64Str);
		}
		log.info("task has been finished");
	}

	public static void decode() throws IOException {
		String readFileToString = FileUtils.readFileToString(new File("D:/1.jpg"));
		byte[] decodeBase64 = Base64.decodeBase64(readFileToString);
		FileUtils.writeByteArrayToFile(new File("D:/2.jpg"), decodeBase64);
		log.info("task has been finished");
	}

}
