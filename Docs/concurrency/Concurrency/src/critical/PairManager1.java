package critical;

//Synchronize the entire method:
class PairManager1 extends PairManager {
	public synchronized void increment() {
		p.incrementX();
		p.incrementY();
		store(getPair());
	}
}
