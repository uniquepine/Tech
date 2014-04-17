package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThrealPool {

	public static void main(String[] args) {
		//ExecutorService exec = Executors.newCachedThreadPool();
		ExecutorService exec = Executors.newFixedThreadPool(5);
		//ExecutorService exec = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 5; i++)
			exec.execute(new LiftOff());
		exec.shutdown();
	}
} 
