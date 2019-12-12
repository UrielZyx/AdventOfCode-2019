package day10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import org.javatuples.Pair;

import myMath.MyMath;

public class AsteroidMapper {

	int width, height;
	char[][] map;
	List<Pair<Integer,Integer>> asteroids = new ArrayList<>();
	Map<Pair<Integer, Integer>, PriorityQueue<Pair<Integer, Integer>>> linesOfSight;
	Pair<Integer, Integer> optimalPosition;
	
	public AsteroidMapper(String asteroidsMap) {
		String[] mapRows = asteroidsMap.split(",");
		width = mapRows[0].length();
		height = mapRows.length;
		map = new char[height][width];
		for (int i = 0; i < mapRows.length; i++) {
			for (int j = 0; j < mapRows[i].length(); j++) {
				map[i][j] = mapRows[i].charAt(j);
				if(map[i][j] == '#') {
					asteroids.add(Pair.with(i, j));
				}
			}
		}
	}

	public int visibilityFromBestPositionForStation() {
		int maxVisibleAsteroids = 0;
		Pair<Integer, Integer> direction;
		for (Pair<Integer, Integer> a1 : asteroids) {
			Map<Pair<Integer, Integer>, PriorityQueue<Pair<Integer, Integer>>> tempLinesOfSight = new HashMap<>();
			for (Pair<Integer, Integer> a2 : asteroids) {
				if (!a1.equals(a2)) {
					direction = findLineOfSight(a1, a2);
					tempLinesOfSight.putIfAbsent(direction, new PriorityQueue<>(
							(p1, p2) -> 
								p1.getValue0() > 0 ? 
										p1.getValue0() - p2.getValue0(): 
										p1.getValue1() - p2.getValue1()
					));
					tempLinesOfSight.get(direction).add(Pair.with(Math.abs(a1.getValue0() - a2.getValue0()),Math.abs(a1.getValue1() - a2.getValue1())));
				}
			}
			if (tempLinesOfSight.size() > maxVisibleAsteroids) {
				maxVisibleAsteroids = tempLinesOfSight.size();
				linesOfSight = tempLinesOfSight;
				optimalPosition = a1;
			}
		}
		return maxVisibleAsteroids;
	}

	public Pair<Integer, Integer> findNthBlastedAsteroid(int n) {
		visibilityFromBestPositionForStation();
		Pair<Integer, Integer> currentBlasted = Pair.with(0, 0);
		List<Pair<Integer, Integer>> orderedDirections = linesOfSight.keySet().stream()
			.sorted(AsteroidMapper::compareDirections)
			.collect(Collectors.toList());
		int i, j = 0;
		for (i = 0; i < n; i++) {
			for (;linesOfSight.get(orderedDirections.get((i + j) % orderedDirections.size())).isEmpty();j++);
			currentBlasted = linesOfSight.get(orderedDirections.get(((i + j) % orderedDirections.size()))).poll();
		}
		return computeLocation(currentBlasted, orderedDirections.get((i + j - 1) % orderedDirections.size()));
		
	}
	
	private Pair<Integer, Integer> computeLocation(Pair<Integer, Integer> currentBlasted, Pair<Integer, Integer> direction) {
		int x = optimalPosition.getValue0();
		int y = optimalPosition.getValue1();
		x += currentBlasted.getValue0() * Math.signum(direction.getValue0());
		y += currentBlasted.getValue1() * Math.signum(direction.getValue1());
		return Pair.with(x, y);
	}

	private Pair<Integer, Integer> findLineOfSight(Pair<Integer, Integer> a1, Pair<Integer, Integer> a2) {
		Pair<Integer,Integer> distance = Pair.with(a2.getValue0()-a1.getValue0(), a2.getValue1()-a1.getValue1());
		if (distance.getValue0() == 0 || distance.getValue1() == 0) {
			return Pair.with(canonical(distance.getValue0()), canonical(distance.getValue1()));
		}
		int gcd = findGCD(Math.abs(distance.getValue0()), Math.abs(distance.getValue1()));
		return Pair.with(distance.getValue0()/gcd, distance.getValue1()/gcd);
	}

	private int canonical(int n) {
		if (n > 0) {
			return 1;
		}
		if (n < 0) {
			return -1;
		}
		return 0;
	}

	private int findGCD(int x, int y) {
		return (int) MyMath.gcd(x, y);
	}
	
	private static int compareDirections(Pair<Integer, Integer> d1, Pair<Integer, Integer> d2) {
		int t = differentHalfs(d1, d2);
		if (t != 0) {
			return t;
		}
		return sameHalf(d1, d2);
	}

	private static int differentHalfs(Pair<Integer, Integer> d1, Pair<Integer, Integer> d2) {
		if ((d1.getValue1() < 0 && d2.getValue1() > 0) || (d1.getValue1() > 0 && d2.getValue1() < 0)) {
			return d2.getValue1();
		}
		if (d1.equals(Pair.with(-1, 0))) {
			return -1;
		}
		if (d2.equals(Pair.with(-1, 0))) {
			return 1;
		}
		if (d1.equals(Pair.with(1, 0)) || d2.equals(Pair.with(1, 0))) {
			return d2.getValue1() - d1.getValue1();
		}
		return 0;
	}

	private static int sameHalf(Pair<Integer, Integer> d1, Pair<Integer, Integer> d2) {
		float f1 = ratio(d1), f2 = ratio(d2);
		int result = d1.getValue0() * d1.getValue1();
		return f1 > f2? -1: 1;
	}
	
	private static float ratio(Pair<Integer, Integer> d) {
		return ((float)-1 * d.getValue0())/((float)d.getValue1());
	}
}
