package day3;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.javatuples.Pair;

public class DelayIntersectionFinder extends IntersectionFinder<Pair<Integer, Integer>> {

	public DelayIntersectionFinder(WirePath path1, WirePath path2) {
		super(path1, path2);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Comparator<Pair<Integer, Integer>> getComparator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PriorityQueue<Pair<Integer, Integer>> createWireQueue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void trackPath(WirePath path, PriorityQueue wire) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected int distance(Pair<Integer, Integer> p) {
		// TODO Auto-generated method stub
		return 0;
	}

}
