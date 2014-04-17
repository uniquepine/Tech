package interrupt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingPrime implements Runnable {
	private final BlockingQueue<BigInteger> primes;
	
	//use volatile for cancelled status
	private volatile boolean cancelled;

	public BlockingPrime(BlockingQueue<BigInteger> primes) {
		this.primes = primes;
	}
	public void run() {
		try {
			BigInteger p = BigInteger.ONE;
			while (!cancelled) {
				p = p.nextProbablePrime();
				synchronized (this) {
					primes.put(p);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void cancel() {
		cancelled = true;
	}

	public synchronized List<BigInteger> get() {
		return new ArrayList<BigInteger>(primes);
	}
	
	public static void main(String[] args) {
		BlockingQueue<BigInteger> primes = new ArrayBlockingQueue<BigInteger>(5);
		BlockingPrime prime = new BlockingPrime(primes);
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
