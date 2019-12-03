package day3;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import org.javatuples.Pair;
import org.javatuples.Triplet;

public class DelayIntersectionFinder {
	
	private HashMap<Pair<Integer, Integer>,Integer> wire = new HashMap<>();
	WirePath path1;
	WirePath path2;

	public DelayIntersectionFinder(WirePath path1, WirePath path2) {
		this.path1 = path1;
		this.path2 = path2;
	}

	private void trackPath(WirePath path, HashMap<Pair<Integer, Integer>, Integer> wire) {
		Pair<Integer, Integer> currentPosition = Pair.with(0, 0);
		int delay = 1;

		for (Pair<Direction, Integer> turn : path) {
			Pair<Integer, Integer> direction = turn.getValue0().directionVector;
			for (int i = 1; i <= turn.getValue1(); i++, delay++) {
				currentPosition = addVectors(currentPosition,direction);
				wire.putIfAbsent(currentPosition, delay);
			}
		}
	}

	private int checkPath(WirePath path, HashMap<Pair<Integer, Integer>, Integer> wire) {
		Pair<Integer, Integer> currentPosition = Pair.with(0, 0);
		Integer minimumDelay = Integer.MAX_VALUE; 
		int delay = 1;
		
		for (Pair<Direction, Integer> turn : path) {
			Pair<Integer, Integer> direction = turn.getValue0().directionVector;
			for (int i = 1; i <= turn.getValue1(); i++, delay++) {
				currentPosition = addVectors(currentPosition,direction);
				if (wire.containsKey(currentPosition)) {
					if (wire.get(currentPosition) + delay < minimumDelay) {
						minimumDelay=wire.get(currentPosition) + delay;
					}
				}
			}
		}
		return minimumDelay;
	}
	
	protected static Pair<Integer, Integer> addVectors(Pair<Integer, Integer> position,
			Pair<Integer, Integer> direction) {
		return Pair.with(
				position.getValue0() + direction.getValue0(),
				position.getValue1() + direction.getValue1());
	}

	public int findClosestIntersection() {
		trackPath(path1, wire);
		return checkPath(path2, wire);
	}

}