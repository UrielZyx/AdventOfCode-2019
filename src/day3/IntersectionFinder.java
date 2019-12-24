package day3;

import java.util.HashMap;

import org.javatuples.Pair;

import myMath.Direction;

public abstract class IntersectionFinder {
	
	private HashMap<Pair<Integer, Integer>,Integer> wire = new HashMap<>();
	WirePath path1;
	WirePath path2;

	public IntersectionFinder(WirePath path1, WirePath path2) {
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
				wire.putIfAbsent(currentPosition, distance(currentPosition, delay));
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
					if (totalDistance(wire, currentPosition, delay) < minimumDelay) {
						minimumDelay = totalDistance(wire, currentPosition, delay);
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

	protected int totalDistance(HashMap<Pair<Integer, Integer>, Integer> wire, Pair<Integer, Integer> currentPosition,
			int delay) {
		return distance(currentPosition, delay);
	}

	protected abstract Integer distance(Pair<Integer, Integer> currentPosition, int delay);
}
