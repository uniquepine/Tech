package visibility;

public class SynchronizedInteger {
	private int value;
	
	public synchronized int get() {
		return value;
	}
	
	public synchronized void set (int value) {
		this.value = value;
	}
	
	/*
	 * 理解volatile变量的一种有效方法：
	 * 把的读操作和写操作分别替换成get方法和set方法。
	 * 然而，在访问volatile变量时不会执行加锁操作，因此也就不会使线程阻塞。
	 * 因此，volatile变量是一种比synchronized关键字更轻量级的同步机制
	 */
}
