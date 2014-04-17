package cooperate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeadlockingDiningPhilosophers {
	public static void main(String[] args) throws Exception {
		int ponder = 1;
		int size = 5;
		ExecutorService exec = Executors.newCachedThreadPool();
		Chopstick[] sticks = new Chopstick[size];
		for (int i = 0; i < size; i++)
			sticks[i] = new Chopstick();
		for (int i = 0; i < size; i++)
			exec.execute(new Philosopher(sticks[i], sticks[(i + 1) % size], i,
					ponder));

		System.in.read();
		exec.shutdownNow();
	}
}
