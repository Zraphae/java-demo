package cn.enn.test.nio.thread;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
@Builder
public class ThreadPool {

	@Builder.Default
	private int corePoolSize = Runtime.getRuntime().availableProcessors();
	@Builder.Default
	private int maximumPoolSize = Runtime.getRuntime().availableProcessors() * 10;
	@Builder.Default
	private long keepAliveTime = 1000;
	@Builder.Default
	private TimeUnit unit = TimeUnit.MILLISECONDS;
	@Builder.Default
	private BlockingQueue<Runnable> workQueue = new SynchronousQueue<>();
	@Builder.Default
	private RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();

	public ExecutorService newThreadPool() {

		log.info(String.format(
				"create threadPoolExecutor finished, params corePoolSize:%d, maximumPoolSize:%d, keepAliveTime:%d, ",
				corePoolSize, maximumPoolSize, keepAliveTime));
		return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
	}

}
