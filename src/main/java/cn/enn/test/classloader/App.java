package cn.enn.test.classloader;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

	public static void main(String[] args) throws Exception {

		args = new String[] {"cn.enn.test.classloader.Foo", "1212", "1313"};
		String progClass = args[0];
		String progArgs[] = new String[args.length - 1];
		System.arraycopy(args, 1, progArgs, 0, progArgs.length);

		MyClassLoad ccl = new MyClassLoad(App.class.getClassLoader());
		Class<?> clas = ccl.loadClass(progClass);
		Class<?> mainArgType[] = { (new String[0]).getClass() };
		Method main = clas.getMethod("main", mainArgType);
		Object argsArray[] = { progArgs };
		main.invoke(null, argsArray);

		// Below method is used to check that the Foo is getting loaded
		// by our custom class loader i.e MyClassLoad
		Method printCL = clas.getMethod("printCL");
		printCL.invoke(null, new Object[0]);
	}

	public static void test1() {
		log.info("class loader for HashMap: {}", java.util.HashMap.class.getClassLoader());
		log.info("class loader for this class: {}", App.class.getClassLoader());
	}
}
