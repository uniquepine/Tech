package interrupt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class InterrputPrime extends Thread {
	private final BlockingQueue<BigInteger> primes;


	public InterrputPrime(BlockingQueue<BigInteger> primes) {
		this.primes = primes;
	}
	public void run() {
		try {
			BigInteger p = BigInteger.ONE;
			while (!Thread.currentThread().isInterrupted()) {
				p = p.nextProbablePrime();
				synchronized (this) {
					primes.put(p);
				}
			}
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}

	public void cancel() {
		this.interrupt();
	}

	public synchronized List<BigInteger> get() {
		return new ArrayList<BigInteger>(primes);
	}
	
	public static void main(String[] args) {
		BlockingQueue<BigInteger> primes = new ArrayBlockingQueue<BigInteger>(5);
		InterrputPrime prime = new InterrputPrime(primes);
		prime.start();
		
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
