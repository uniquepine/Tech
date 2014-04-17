package thread;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletionServiceDemo {

	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		CompletionService<String> completionService = new ExecutorCompletionService<String>(exec);
		for (int i = 0; i < 10; i++)
			completionService.submit(new TaskWithResult(i));
		
		for (int i = 0; i < 10; i++) {
			try {
				Future<String> fs  = completionService.take();
				// get() here won't block:
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
} 
