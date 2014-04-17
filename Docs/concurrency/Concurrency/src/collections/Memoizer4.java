package collections;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Memoizer4 {

	private final static ConcurrentMap<Integer, Future<Integer>> cache = new ConcurrentHashMap<Integer, Future<Integer>>();
	private final static Expensive exp = new Expensive();

	public static void compute(final int j) {
		Future<Integer> f = cache.get(j);
		if (f == null) {
			System.out.println(j);
			FutureTask<Integer> ft = new FutureTask<Integer>(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return exp.compute(j);
				}
				
			});
			cache.putIfAbsent(j, ft);
			if (f==null) {
				f = ft;
				ft.run();
			}
		}
		try {
			System.out.println("get from cache:" + j+"-result:" +f.get());
		} catch (ExecutionException e){
		} catch (InterruptedException e) {
			e.printStackTrace();
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
