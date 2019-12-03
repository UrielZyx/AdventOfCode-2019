package day3;

import org.javatuples.Pair;

public enum Direction {
	R (Pair.with(1,0)),
	L (Pair.with(-1,0)),
	U (Pair.with(0,1)),
	D (Pair.with(0,-1));
	
	Pair<Integer, Integer> directionVector;
	
	private Direction(Pair<Integer, Integer> directionVector) {
		this.directionVector=directionVector;
	}
}
