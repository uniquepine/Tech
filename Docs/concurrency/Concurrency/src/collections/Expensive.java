package collections;

public class Expensive {

	public int compute(int i) {
		try {
			System.out.println("computing..." + i);
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return i;
	}
	
}
