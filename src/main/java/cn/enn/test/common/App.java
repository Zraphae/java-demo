package cn.enn.test.common;

import java.util.ArrayList;
import java.util.List;

public class App {

	public static void main(String[] args) {
		
//		String[] strs = {"123", "456", "789"};
//		List<String> asList = Arrays.asList(strs);
//		System.out.println(asList.get(0));
//		strs[0] = "ads";
//		asList.set(0, "dsgdg");
//		System.out.println(asList.get(0));
		
		
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		for (String item : list) {
			if ("2".equals(item)) {
				list.remove(item);
			}
		}
		
		list.forEach(System.out::println);

	}

}
