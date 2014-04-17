package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTaskDemo {

	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		
		List<FutureTask<String>> results = new ArrayList<FutureTask<String>>();
		for (int i = 0; i < 10; i++) {
			FutureTask<String> futurTask = new FutureTask<String>(new TaskWithResult(i));
			results.add(futurTask);
			exec.execute(futurTask);
		}
			
		for (FutureTask<String> fs : results)
			try {
				// get() blocks until completion:
				System.out.println(fs.get());
			} catch (InterruptedException e) {
				System.out.println(e);
				return;
			} catch (ExecutionException e) {
				System.out.println(e);
			} finally {
				exec.shutdown();
			}
	}
} 
