package cn.enn.test;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
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


		ArrayList<String> strs = Lists.newArrayList();
		strs.add("\"2031\",\"4\",\"10\",16,5,1987-10-16,\"NW\",19386,\"NW\",\"\",\"613\",13487,1348701,31650,\"MSP\",\"Minneapolis, MN\",\"MN\",\"27\",\"Minnesota\",63,11637,1163702,31637,\"FAR\",\"Fargo, ND\",\"ND\",\"38\",\"North Dakota\",66,\"1210\",\"1233\",23,23,1,1,\"1200-1259\",,\"\",\"\",,\"1320\",\"1335\",15,15,1,1,\"1300-1359\",0,\"\",0,70,62,,1,223,1,,,,,,\"\",,,,,,,,\"\",,,\"\",,,\"\",\"\",\"\",,,\"\",,,\"\",\"\",\"\",,,\"\",,,\"\",\"\",\"\",,,\"\",,,\"\",\"\",\"\",,,\"\",,,\"\",\"\"");
		strs.add("\"2032\",\"4\",\"10\",16,5,1987-10-16,\"NW\",19386,\"NW\",\"\",\"613\",13487,1348701,31650,\"MSP\",\"Minneapolis, MN\",\"MN\",\"27\",\"Minnesota\",63,11637,1163702,31637,\"FAR\",\"Fargo, ND\",\"ND\",\"38\",\"North Dakota\",66,\"1210\",\"1233\",23,23,1,1,\"1200-1259\",,\"\",\"\",,\"1320\",\"1335\",15,15,1,1,\"1300-1359\",0,\"\",0,70,62,,1,223,1,,,,,,\"\",,,,,,,,\"\",,,\"\",,,\"\",\"\",\"\",,,\"\",,,\"\",\"\",\"\",,,\"\",,,\"\",\"\",\"\",,,\"\",,,\"\",\"\",\"\",,,\"\",,,\"\",\"\"");
		String join = String.join("\n", strs);
		log.info("=====>{}", join);

		String csvContent = FileUtils.readFileToString(new File("/Users/zhaopeng/Downloads/1.csv"));
		log.info("=====>{}", csvContent);

		boolean equals = StringUtils.equals(join, csvContent);
		log.info("=====>{}", equals);

	}

	private static ArrayList<String> testIterator(ArrayList<String> list) {

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
