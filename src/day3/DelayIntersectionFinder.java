package day3;

import java.util.HashMap;

import org.javatuples.Pair;

public class DelayIntersectionFinder extends IntersectionFinder {

	public DelayIntersectionFinder(WirePath path1, WirePath path2) {
		super(path1, path2);
	}

	@Override
	protected Integer distance(Pair<Integer, Integer> currentPosition, int delay) {
		return delay;
	}

	@Override
	protected int totalDistance(HashMap<Pair<Integer, Integer>, Integer> wire, Pair<Integer, Integer> currentPosition,
			int delay) {
				return wire.get(currentPosition) + distance(currentPosition, delay);
	}
}