package cn.enn.test.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class App {

	public void test() {
		System.out.println("hello world");
	}

	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(App.class);
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				System.out.println("before method run...");
				Object result = proxy.invokeSuper(obj, args);
				System.out.println("after method run...");
				return result;
			}
		});
		App sample = (App) enhancer.create();
		sample.test();
	}

}
