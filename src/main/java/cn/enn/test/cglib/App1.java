package cn.enn.test.cglib;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class App1 {

	public static void main(String[] args) {
		
		final ReentrantLock reentrantLock = new ReentrantLock();
		final Condition condition = reentrantLock.newCondition();
		Thread thread = new Thread(() -> {
			try {
				reentrantLock.lock();
				System.out.println("我要等一个新信号");
				condition.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("拿到一个信号！！");
			reentrantLock.unlock();
		}, "waitThread1");

		thread.start();

		Thread thread1 = new Thread(() -> {
			reentrantLock.lock();
			System.out.println("我拿到锁了");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			condition.signalAll();
			System.out.println("我发了一个信号！！");
			reentrantLock.unlock();
		}, "signalThread");

		thread1.start();
	}
}
