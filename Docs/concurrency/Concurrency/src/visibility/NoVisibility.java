package visibility;

public class NoVisibility {
	private volatile static boolean ready = false;
	private volatile static long number;

	private static Object obj = new Object();
	
	private static class ReaderThread extends Thread {
		public void run() {
				while (!ready) {
					System.out.println("Not Ready!");
					Thread.yield();
				}
				System.out.println(number);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		
			for (int i=0; i< 50; i++) {
				Thread thread = new ReaderThread();
				thread.setPriority(Thread.MIN_PRIORITY);
				thread.start();
			}
			number = 44L;
			ready = true;
			System.out.println("Set Vaule!");
		}
}
