package fr.batch.voyage;

import java.util.Comparator;

public class IntegerCompare implements Comparator<Integer> {

	@Override
	public int compare(Integer arg0, Integer arg1) {
		return arg1.compareTo(arg0);
	}

}
