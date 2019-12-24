package day3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.javatuples.Pair;

import myMath.Direction;

public class WirePath implements Iterable<Pair<Direction,Integer>>{
	
	List<Pair<Direction,Integer>> path = new ArrayList<>();;
	
	public WirePath(String rawPath) {
		for (String turn : rawPath.split(",")) {
			path.add(Pair.with(
					Direction.valueOf(turn.substring(0, 1)), 
					Integer.parseInt(turn.substring(1))));
		}
	}

	@Override
	public Iterator<Pair<Direction, Integer>> iterator() {
		return path.iterator();
	} 
}
