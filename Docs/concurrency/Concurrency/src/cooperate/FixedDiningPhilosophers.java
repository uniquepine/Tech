package cooperate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedDiningPhilosophers {
	public static void main(String[] args) throws Exception {
		int ponder = 0;
		int size = 5;
		ExecutorService exec = Executors.newCachedThreadPool();
		Chopstick[] sticks = new Chopstick[size];
		for (int i = 0; i < size; i++)
			sticks[i] = new Chopstick();
		for (int i = 0; i < size; i++)
			if (i < (size - 1))
				exec.execute(new Philosopher(sticks[i], sticks[i + 1], i,
						ponder));
			else
				exec.execute(new Philosopher(sticks[0], sticks[i], i, ponder));

		System.in.read();
		exec.shutdownNow();
	}
}
