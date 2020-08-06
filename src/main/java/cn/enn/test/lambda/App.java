package cn.enn.test.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class App {

	public static void main(String[] args) {
		 testForeach();

		// testPredition();

		// testMap();

		// testStringFilter();
	}

	public static void testForeach() {

		List<String> asList = Arrays.asList("java", "c++", "python");
		asList.forEach(System.out::println);
		asList.forEach(s -> System.out.println(s));
	}

	public static void testPredition() {
		List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

		System.out.println("Languages which starts with J :");
		filter(languages, (str) -> str.startsWith("J"));

		System.out.println("Languages which ends with a ");
		filter(languages, (str) -> str.endsWith("a"));

		System.out.println("Print all languages :");
		filter(languages, (str) -> true);

		System.out.println("Print no language : ");
		filter(languages, (str) -> false);

		System.out.println("Print language whose length greater than 4:");
		filter(languages, (str) -> str.length() > 4);
	}

	public static void filter(List<String> names, Predicate<String> condition) {
		// 老方法：
		for (String name : names) {
			if (condition.test(name)) {
				System.out.println(name + " ");
			}
		}
		// 新方法
		names.stream().filter((name) -> (condition.test(name))).forEach((name) -> System.out.println("name:" + name));
	}

	public static void testMap() {
		// 不使用lambda表达式为每个订单加上12%的税
		List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
		for (Integer cost : costBeforeTax) {
			double price = cost + .12 * cost;
			System.out.println(price);
		}

		// 使用lambda表达式
		costBeforeTax.stream().map((cost) -> cost + .12 * cost).forEach(System.out::println);

	}

	public static void testReduce() {
		// 为每个订单加上12%的税
		// 老方法：
		List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
		double total = 0;
		for (Integer cost : costBeforeTax) {
			double price = cost + .12 * cost;
			total = total + price;
		}
		System.out.println("Total : " + total);

		// 新方法：
		double bill = costBeforeTax.stream().map((cost) -> cost + .12 * cost).reduce((sum, cost) -> sum + cost).get();
		System.out.println("Total : " + bill);
	}

	public static void testStringFilter() {
		// 创建一个字符串列表，每个字符串长度大于2
		List<String> strList = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
		List<String> filtered = strList.stream().filter(x -> x.length() > 2).collect(Collectors.toList());
		System.out.printf("Original List : %s, filtered list : %s %n", strList, filtered);
	}
}
