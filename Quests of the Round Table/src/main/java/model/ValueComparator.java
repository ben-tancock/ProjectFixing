package model;

import java.util.Comparator;
import java.util.Map;

public class ValueComparator implements Comparator<Integer> {
	Map<Integer, Integer> base;
	
	public ValueComparator(Map<Integer, Integer> base) {
		this.base = base;
	}
	
	@Override
	public int compare(Integer a, Integer b) {
		if(base.get(a) >= base.get(b)) {
			return -1;
		} else {
			return 1;
		}
	}

}
