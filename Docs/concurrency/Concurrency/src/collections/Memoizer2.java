package collections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Memoizer2 {

	private final static Map<Integer, Integer> cache = new ConcurrentHashMap<Integer, Integer>();
	private final static Expensive exp = new Expensive();

	public static void compute(int j) {
		Integer result = cache.get(j);
		if (result == null) {
			System.out.println(j);
			result = exp.compute(j);
			System.out.println(j+"-reulst:" + result);
			cache.put(j, result);
		}
		else {
			System.out.println("get from cache:" + j+"-result:" +cache.get(j));
		}
	}
	
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i<5; i++) {

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			final int j = 1;
			exec.execute(new Runnable() {
				@Override
				public void run() {
					compute(j);
				}
				
				
			});
		}
		exec.shutdown();
	}

}
