package interfere;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LazyInitRace {
	private static LazyInitRace instance = null;
	
	public static LazyInitRace getInstance() {
		if (instance == null) {
			try {
				System.out.println("Ready to new lazy expensive object");
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Initing expensive object...");
			instance = new LazyInitRace();
		}
		return instance;
	}
	
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 3; i++)
			exec.execute(new Runnable() {
				@Override
				public void run() {
					LazyInitRace.getInstance();
				}
			});
		exec.shutdown();
	}
}
