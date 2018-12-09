package cn.enn.test.guava;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class App {

	public static void main(String[] args) throws IOException {

		// testOptional();
//		testPreconditions();
		// testJoiner();
		// testSplitter();
		// testLoadingCache();
		 testCallBack();
		
	}

	public static void testOptional() {

		Integer c = 1;
		Integer d = null;

		Optional<Integer> cOp = Optional.of(c);
		Optional<Integer> dOp = Optional.fromNullable(d);

		log.info("cOpIsPresent: {}", cOp.isPresent());
		log.info("dOpIsPresent: {}", dOp.isPresent());
		log.info("cOpIsPresent: {}", cOp.or(0));
		log.info("dOpIsPresent: {}", dOp.or(0));
	}

	public static int testPreconditions() {
		int a = 1;
		int b = 0;
		Preconditions.checkArgument(b != 0, "====):");
		return a / b;
	}

	public static void testJoiner() throws IOException {
		/*
		 * on:制定拼接符号，如：test1-test2-test3 中的 “-“ 符号 skipNulls()：忽略NULL,返回一个新的Joiner实例
		 * useForNull(“Hello”)：NULL的地方都用字符串”Hello”来代替
		 */
		StringBuffer sb = new StringBuffer();
		Joiner.on(",").skipNulls().appendTo(sb, "Hello", null);
		System.out.println(sb);
		System.out.println(Joiner.on(",").useForNull("none").join(1, null, 3));
		System.out.println(Joiner.on(",").skipNulls().join(Arrays.asList(1, 2, 3, 4, null, 6)));
		Map<String, String> map = new HashMap<>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		map.put("key3", "value3");
		System.out.println(Joiner.on(",").withKeyValueSeparator("=").join(map));
	}

	public static void testSplitter() {
		/*
		 * on():指定分隔符来分割字符串 limit():当分割的子字符串达到了limit个时则停止分割 fixedLength():根据长度来拆分字符串
		 * trimResults():去掉子串中的空格 omitEmptyStrings():去掉空的子串
		 * withKeyValueSeparator():要分割的字符串中key和value间的分隔符,分割后的子串中key和value间的分隔符默认是=
		 */
		System.out.println(Splitter.on(",").limit(3).trimResults().split(" a,  b,  c,  d"));// [ a, b, c,d]
		System.out.println(Splitter.fixedLength(3).split("1 2 3"));// [1 2, 3]
		// System.out.println(Splitter.on(" ").omitEmptyStrings().splitToList("1 2 3"));
		System.out.println(Splitter.on(",").omitEmptyStrings().split("1,,,,2,,,3"));// [1, 2, 3]
		System.out.println(Splitter.on(" ").trimResults().split("1 2 3").toString()); // [1, 2, 3],默认的连接符是,
		System.out.println(Splitter.on(";").withKeyValueSeparator(":").split("a:1;b:2;c:3"));// {a=1, b=2, c=3}
	}

	public static void testLoadingCache() {

		LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumSize(5) // 最大缓存数目
				.expireAfterAccess(1, TimeUnit.SECONDS) // 缓存1秒后过期
				.build(new CacheLoader<String, String>() {
					@Override
					public String load(String key) throws Exception {
						return "123";
					}
				});
		cache.put("j", "java");
		cache.put("c", "cpp");
		cache.put("s", "scala");
		cache.put("g", "go");
		try {
			System.out.println(cache.get("j"));
			System.out.println(cache.get("c"));
			System.out.println(cache.get("s")); // 输出s
			System.out.println(cache.get("g")); // 输出s
			TimeUnit.SECONDS.sleep(2);
			System.out.println(cache.get("s")); // 输出s
			System.out.println(cache.get("g")); // 输出s
			System.out.println(cache.get("g")); // 输出s
			System.out.println(cache.size());
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static void testCallBack() {
		Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(1, TimeUnit.SECONDS)
				.build();

		cache.put("java", "javavalue");
		try {
			TimeUnit.SECONDS.sleep(2);
			String result = cache.get("java", () -> "hello java");
			System.out.println(result);
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
