package day11;

import java.util.HashMap;
import java.util.Map;

import org.javatuples.Pair;

public class Grid {
	private Pair<Integer, Integer> position = Pair.with(0, 0);
	Map<Pair<Integer, Integer>, Integer> grid = new HashMap<>();
}
