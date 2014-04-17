package collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RemoveItem {

	static List<Integer> list = new ArrayList<Integer>(10);
	
	public static void main(String[] args) {
		for (int i=0; i<10; i++ ) {
			list.add(i);
		}
		
//		for(int i: list) {
//			if (i == 5) {
//				//list.remove(i);
//				list.add(10);
//			}
//		}
		
		Iterator<Integer> it = list.iterator();
		
		while(it.hasNext()) {
			int i = it.next();
			if (i==5) {
				//it.remove();
				//list.remove(5);
				//list.add(10);
			}
		}
		
		System.out.println(list);
	}
}
