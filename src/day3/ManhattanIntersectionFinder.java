package day3;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.javatuples.Pair;
import org.javatuples.Tuple;

public class ManhattanIntersectionFinder extends IntersectionFinder<Pair<Integer, Integer>>{
	
	public ManhattanIntersectionFinder(WirePath path1, WirePath path2) {
		super(path1, path2);
	}

	protected PriorityQueue<Pair<Integer, Integer>> createWireQueue() {
		return new PriorityQueue<Pair<Integer,Integer>>(ManhattanIntersectionFinder::manhattanDistanceComparator);
	}

	protected void trackPath(WirePath path, PriorityQueue wire) {
		Pair<Integer, Integer> currentPosition = Pair.with(0, 0);
		
		for (Pair<Direction, Integer> turn : path) {
			Pair<Integer, Integer> direction = turn.getValue0().directionVector;
			for (int i = 0; i < turn.getValue1(); i++) {
				currentPosition = addVectors(currentPosition,direction);
				wire.add(currentPosition);
			}
		}
	}
	
	private static Pair<Integer, Integer> addVectors(Pair<Integer, Integer> position,
			Pair<Integer, Integer> direction) {
		return Pair.with(
				position.getValue0() + direction.getValue0(),
				position.getValue1() + direction.getValue1());
	}

	@Override
	protected Comparator<Pair<Integer, Integer>> getComparator() {
		return ManhattanIntersectionFinder::manhattanDistanceComparator;
	}

	private static int manhattanDistanceComparator(Pair<Integer,Integer> p1,Pair<Integer,Integer> p2) {
			int result = manhattanDistance(p1) - manhattanDistance(p2);
			if(result == 0) {
				result = p1.getValue0() - p2.getValue0();
				if(result == 0) {
					result = p1.getValue1() - p2.getValue1();
				}
			}
			return result;
	}

	@Override
	protected int distance(Pair<Integer, Integer> p) {
		return manhattanDistance(p);
	}

	private static int manhattanDistance(Pair<Integer, Integer> p) {
		return Math.abs(p.getValue0()) + Math.abs(p.getValue1());
	}

}
