package cn.enn.test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

	public static void main(String[] args) {
		
		final List<String> imgPaths = Arrays.asList("/test/imgPath1", "/test/imgPath2");
		final int nThreads = 2;
		ExecutorService pool = Executors.newFixedThreadPool(nThreads);

		for (int i = 0; i < nThreads; i++) {
			final String imgPath = imgPaths.get(i);
			pool.submit(new Runnable() {
				
				@Override
				public void run() {
					
					for(int i =0; i< 10; i++) {
						log.info("imgPath: {}", imgPath);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
			log.info("start thread finished {}, imgPath: {}", Thread.currentThread().getName(), imgPath);
		}
		
		pool.shutdown();
	}

}
