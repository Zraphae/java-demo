package cn.enn.test.classloader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Foo {
	
	static public void main(String args[]) throws Exception {
		log.info("Foo Constructor >>> {} {}", args[0],  args[1]);
		Bar bar = new Bar(args[0], args[1]);
		bar.printCL();
	}

	public void printCL() {
		log.info("Foo ClassLoader: {}", Foo.class.getClassLoader());
	}
}
