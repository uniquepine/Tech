package interfere;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockEvenGenerator extends IntGenerator {
	private int currentEvenValue = 0;
	private Lock lock = new ReentrantLock();

	public int next() {
		lock.lock();
//		lock.tryLock();
//		lock.tryLock(100, TimeUnit.MILLISECONDS);
//		lock.lockInterruptibly()
		try {
			++currentEvenValue;
			Thread.yield(); // Cause failure faster
			++currentEvenValue;
			return currentEvenValue;
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		EvenChecker.test(new LockEvenGenerator());
	}
}