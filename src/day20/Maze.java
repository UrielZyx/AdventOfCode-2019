package day20;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import myMath.Direction;

public class Maze {

	private static final String START = "AA";
	private static final String END = "ZZ";
	private static final String I = "i";
	private static final String J = "j";
	List<List<Integer>> map;
	Multimap<String, Pair<Integer, Integer>> portals = ArrayListMultimap.create();
	Multimap<Pair<Integer, Integer>, Pair<Integer, Integer>> neighbors = ArrayListMultimap.create();
	Map<Pair<Integer, Integer>, Triplet<Integer, Integer, Integer>> portalNeighbors = new HashMap<>();
	Triplet<Integer, Integer, Integer> start = null;
	Triplet<Integer, Integer, Integer> end = null;
	int width = 0, height = 0;
	boolean recursive = false;

	public Maze(List<List<Integer>> list) {
		this(list, false);
	}

	public Maze(List<List<Integer>> list, boolean isRecursive) {
		recursive = isRecursive;
		map = list;
		height = map.size();
		width = map.get(0).size();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (get(i, j) == '.') {
					handleOpenSpace(i, j);
				} else if (isLetter(i, j)) {
					handlePortal(i, j);
				}
			}
		}
		processPortals();
	}

	private void processPortals() {
		for (String portal : portals.keySet()) {
			if (START.equals(portal)) {
				start = portals.get(portal).iterator().next().add(0);
			} else if (END.equals(portal)) {
				end = portals.get(portal).iterator().next().add(0);
			} else {
				Pair<Integer, Integer>[] points = portals.get(portal).toArray(new Pair[1]);
				int depth = depthDiff(points[0], points[1]);
				portalNeighbors.put(points[0], points[1].add(depth));
				portalNeighbors.put(points[1], points[0].add(-1 * depth));
			}
		}
	}

	private int depthDiff(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
		if (!recursive) {
			return 0;
		}
		if (isOuter(p1)) {
			return -1;
		}
		return 1;
	}

	private boolean isOuter(Pair<Integer, Integer> p1) {
		Integer y = p1.getValue0();
		Integer x = p1.getValue1();
		return y == 2 || y == height - 3 || x == 2 || x == width - 3;
	}

	private void handleOpenSpace(int i, int j) {
		Pair<Integer, Integer> p = Pair.with(i, j);
		for (Direction d : Direction.values()) {
			if (get(i + d.x(), j + d.y()) == '.') {
				Pair<Integer, Integer> other = Pair.with(i + d.x(), j + d.y());
				neighbors.put(p, other);
			}
		}
	}

	private void handlePortal(int i, int j) {
		if (isLetter(i + 1, j)) {
			setPortalPosition(i, j, I);
		}
		if (isLetter(i, j + 1)) {
			setPortalPosition(i, j, J);
		}
	}

	private void setPortalPosition(int i, int j, String direction) {
		if (I.equals(direction)) {
			if (get(i - 1, j) == '.') {
				setPortal(getLetters(i, j, direction), i - 1, j);
			}
			if (get(i + 2, j) == '.') {
				setPortal(getLetters(i, j, direction), i + 2, j);
			}
		}
		if (J.equals(direction)) {
			if (get(i, j - 1) == '.') {
				setPortal(getLetters(i, j, direction), i, j - 1);
			}
			if (get(i, j + 2) == '.') {
				setPortal(getLetters(i, j, direction), i, j + 2);
			}
		}
	}

	private void setPortal(String letters, int i, int j) {
		portals.put(letters, Pair.with(i, j));
	}

	private String getLetters(int i, int j, String direction) {
		char a = (char) get(i, j);
		char b;
		if (direction.equals(I)) {
			b = (char) get(i + 1, j);
		} else {
			b = (char) get(i, j + 1);
		}
		return String.valueOf(a) + String.valueOf(b);
	}

	private boolean isLetter(int i, int j) {
		return get(i, j) >= 'A' && get(i, j) <= 'Z';
	}

	private int get(int i, int j) {
		if (i >= 0 && i < height && j >= 0 & j < width) {
			return map.get(i).get(j);
		}
		return ' ';
	}

	public int shortestPath() {

		Map<Triplet<Integer, Integer, Integer>, Integer> distances = new HashMap<>();
		Queue<Triplet<Integer, Integer, Integer>> q = new PriorityQueue<>(
				(p1, p2) -> distances.get(p2) - distances.get(p1));
		distances.put(start, 0);
		q.add(start);
		while (!q.isEmpty()) {
			Triplet<Integer, Integer, Integer> current = q.poll();
			for (Triplet<Integer, Integer, Integer> neighbor : getNeighbors(current)) {
				walk(distances, q, current, neighbor);
			}
			Triplet<Integer, Integer, Integer> portal = getPortal(current);
			if (portal != null) {
				walk(distances, q, current, portal);
			}
			q.remove(current);
		}
		return distances.get(end);
	}

	private List<Triplet<Integer, Integer, Integer>> getNeighbors(Triplet<Integer, Integer, Integer> current) {
		return neighbors
				.get(current.removeFrom2())
				.stream()
				.map(p -> p.add(current.getValue2()))
				.collect(Collectors.toList());
	}

	private Triplet<Integer, Integer, Integer> getPortal(Triplet<Integer, Integer, Integer> current) {
		Triplet<Integer, Integer, Integer> portal = portalNeighbors.get(current.removeFrom2());
		if (portal == null) {
			return null;
		}

		if (portal.getValue2() != 0) {
			int newDepth = portal.getValue2() + current.getValue2();
			if (newDepth < 0 || newDepth > 30) {
				return null;
			}
			portal = portal.setAt2(newDepth);
		}
		return portal;
	}

	private void walk(Map<Triplet<Integer, Integer, Integer>, Integer> distances,
			Queue<Triplet<Integer, Integer, Integer>> q, Triplet<Integer, Integer, Integer> current,
			Triplet<Integer, Integer, Integer> neighbor) {
		int d = distances.get(current);
		if (!distances.containsKey(neighbor) || distances.get(neighbor) > d + 1) {
			distances.put(neighbor, d + 1);
			q.remove(neighbor);
			q.add(neighbor);
		}
	}
}
