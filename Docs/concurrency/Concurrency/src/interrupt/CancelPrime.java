package interrupt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CancelPrime implements Runnable {
	private final List<BigInteger> primes = new ArrayList<BigInteger>();
	
	//use volatile for cancelled status
	private volatile boolean cancelled;

	public void run() {
		BigInteger p = BigInteger.ONE;
		while (!cancelled) {
			p = p.nextProbablePrime();
			synchronized (this) {
				primes.add(p);
			}
		}
	}

	public void cancel() {
		cancelled = true;
	}

	public synchronized List<BigInteger> get() {
		return new ArrayList<BigInteger>(primes);
	}
	
	public static void main(String[] args) {
		CancelPrime prime = new CancelPrime();
		new Thread(prime).start();
		
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}  finally {
			prime.cancel();
		}
		System.out.println(prime.get());
	}
}
