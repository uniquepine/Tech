package cooperate;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


class WaitPerson2 implements Runnable {
	private Restaurant2 restaurant;

	public WaitPerson2(Restaurant2 r) {
		restaurant = r;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				System.out.println("Waitperson got " + restaurant.meal.take());
			}
		} catch (InterruptedException e) {
			System.out.println("WaitPerson2 interrupted");
		}
	}
}

class Chef2 implements Runnable {
	private Restaurant2 restaurant;
	private int count = 0;

	public Chef2(Restaurant2 r) {
		restaurant = r;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				if (++count == 10) {
					System.out.println("Out of food, closing");
					restaurant.exec.shutdownNow();
				}
				System.out.println("Order up! ");
				restaurant.meal.put(new Meal(count));
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch (InterruptedException e) {
			System.out.println("Chef2 interrupted");
		}
	}
}

public class Restaurant2 {
	BlockingQueue<Meal> meal = new ArrayBlockingQueue<Meal>(1);
	ExecutorService exec = Executors.newCachedThreadPool();
	WaitPerson2 waitPerson = new WaitPerson2(this);
	Chef2 chef = new Chef2(this);

	public Restaurant2() {
		exec.execute(chef);
		exec.execute(waitPerson);
	}

	public static void main(String[] args) {
		new Restaurant2();
	}
}
