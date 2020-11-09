package cn.enn.test;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.util.Base64;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

@Slf4j
public class App {

	public static void test() {

		Map<String, String> map = System.getenv();
		System.out.println(map.get("ENN_FACEID_HOME"));
	}

	public static void main(String[] args) throws IOException {

		ArrayList<String> list = Lists.newArrayList();
		for(int i=0; i<10; i++){
			list.add(i+"");
		}

		log.info("===>{}", list);
		list = testInterator(list);
		log.info("---->{}", list);

	}

	private static ArrayList<String> testInterator(ArrayList<String> list) {

		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()){
			String next = iterator.next();
			if(next.equals("2")){
				iterator.remove();
			}
		}
		return list;
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
