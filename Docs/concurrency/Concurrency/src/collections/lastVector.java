package collections;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class lastVector {
	
	public volatile static boolean rightTime = false;
	public volatile static Vector<Integer> vector = new Vector<Integer>(10);
	
	public static Integer getLast(){
		int lastIndex = vector.size() - 1;
		rightTime = true;
//		try {
//			Thread.sleep(10);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		return vector.get(lastIndex);
	}
	
	public static void deleteLast() {
		while(!rightTime) {
			//wait 
		}
		int lastIndex = vector.size() - 1;
		vector.remove(lastIndex);
	}
	
	public static void main(String[] args) {
		for (int i = 0; i< vector.capacity(); i++) {
			vector.add(i);
		}
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println(getLast());
			}
		});
		exec.execute(new Runnable() {
			@Override
			public void run() {
				deleteLast();
			}
		});
		exec.shutdown();
	}
}
