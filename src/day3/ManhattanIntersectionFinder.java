package day3;

import org.javatuples.Pair;

public class ManhattanIntersectionFinder extends IntersectionFinder{

	public ManhattanIntersectionFinder(WirePath path1, WirePath path2) {
		super(path1, path2);
	}

	@Override
	protected Integer distance(Pair<Integer, Integer> p, int delay) {
		return Math.abs(p.getValue0()) + Math.abs(p.getValue1());
	}

}
