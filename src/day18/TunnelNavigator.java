package day18;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.javatuples.Pair;

public class TunnelNavigator {

    private static final int MAX = Integer.MAX_VALUE / 2;
    int height, width;
    Map<Pair<Integer, Integer>, Character> grid = new HashMap<>();
    Map<Pair<Integer, Integer>, Map<Pair<Integer, Integer>, Integer>> distances;
    Map<Pair<Integer, Integer>, Map<Pair<Integer, Integer>, Blockers>> blockers;
    Map<Character, Pair<Integer, Integer>> specialLocations = new HashMap<>();

	public TunnelNavigator(List<String> input) {
        height = input.size();
        width = input.get(0).length();
        char temp;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                temp = input.get(i).charAt(j);
                if (temp != '#') {
					grid.put(Pair.with(i,  j), temp);
	                if (Character.isLowerCase(temp) || temp == '@') {
		                specialLocations.put(temp, Pair.with(i, j));
					}
				}
            }
        }
        specialLocations.remove('.');
	}

	public int getShortestPathToAllKeys() {
        initPaths();
        floydWarshall();
//        printDistances();
        System.out.println("Done with Floyd-Warshal");
		return getShortestPath();
	}

    private void initPaths() {
        int x, y;
        Blockers doors;
        distances = new HashMap<>();
        blockers = new HashMap<>();
        List<Pair<Integer, Integer>> directions = 
                Arrays.asList(
                    Pair.with(1, 0), 
                    Pair.with(0, 1));

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (Pair<Integer,Integer> direction : directions) {
                    x = i + direction.getValue0();
                    y = j + direction.getValue1();
                    if (hasValues(i, j, x, y)) {
                        doors = checkBlockers(i, j, x, y);
                        distances.put(Pair.with(i, j), distances.getOrDefault(Pair.with(i, j), new HashMap<>()));
                        distances.get(Pair.with(i, j))
                    		.put(Pair.with(x, y), 1);
                        distances.put(Pair.with(x, y), distances.getOrDefault(Pair.with(x, y), new HashMap<>()));
                        distances.get(Pair.with(x, y))
                    		.put(Pair.with(i, j), 1);
                        blockers.put(Pair.with(i, j), blockers.getOrDefault(Pair.with(i, j), new HashMap<>()));
                        blockers.get(Pair.with(i, j))
                    		.put(Pair.with(x, y), new Blockers().add(doors));
                        blockers.put(Pair.with(x, y), blockers.getOrDefault(Pair.with(x, y), new HashMap<>()));
                        blockers.get(Pair.with(x, y))
                    		.put(Pair.with(i, j), new Blockers().add(doors));
                    }
                }
                if(grid.containsKey(Pair.with(i, j))) {
                    distances.put(Pair.with(i, j), distances.getOrDefault(Pair.with(i, j), new HashMap<>()));
                    distances.get(Pair.with(i, j))
                		.put(Pair.with(i, j), 0);
                    blockers.put(Pair.with(i, j), blockers.getOrDefault(Pair.with(i, j), new HashMap<>()));
                    blockers.get(Pair.with(i, j))
                		.put(Pair.with(i, j), new Blockers());
                }
            }
        }
    }

    private boolean hasValues(int i, int j, int x, int y) {
        return grid.containsKey(Pair.with(i, j)) && grid.containsKey(Pair.with(x, y));
    }

    private Blockers checkBlockers(int i, int j, int x, int y) {
        Blockers doors = new Blockers();
        if (Character.isUpperCase(grid(i, j))) {
            doors.add(grid(i, j));
        }
        if (Character.isUpperCase(grid(x, y))) {
            doors.add(grid(x, y));
        }
        return doors;
    }

	private void floydWarshall() {
		for (Pair<Integer, Integer> i : grid.keySet()) {
			for (Pair<Integer, Integer> j : grid.keySet()) {
				for (Pair<Integer, Integer> k : grid.keySet()) {
					if (getDistance(j, i) + getDistance(i, k) < getDistance(j, k)){
						distances.getOrDefault(j, new HashMap<>())
							.put(k, distances.get(j).get(i) + distances.get(i).get(k));
						blockers.getOrDefault(j, new HashMap<>())
							.put(k, 
								new Blockers()
								.add(blockers.get(j).get(i))
								.add(blockers.get(i).get(k)));
					}
				}	
			}
		}
    }
	
	private void printDistances() {
        specialLocations.keySet()
        .stream()
        .map(specialLocations::get)
        .forEach(l1 -> 
            specialLocations.keySet()
            .stream()
            .map(specialLocations::get)
            .forEach(l2 -> 
                System.out.println("l1: "
                    + l1.toString()
                    + "\tl2: "
                    + l2.toString()
                    + "\tDistance: "
                    + getDistance(l1, l2)
                    + "\tBlockers: "
                    + getBlockers(l1, l2)
                    + "\t")));
	}

    private int getShortestPath() {
    	List<Character> keys = specialLocations.keySet().stream()
    		.filter(Character::isLowerCase)
    		.collect(Collectors.toList());
    	keys.add(0, '@');
        return getShortestPathOfPermutation(keys, new HashSet<>(), 1);
    }

	private int getShortestPathOfPermutation(List<Character> keys, Set<Character> collectedKeys, int i) {
		Character temp;
		int minimalDistance = Integer.MAX_VALUE;
		int currentPathLength;
		
		if (i == keys.size()) {
			return 0;
		}
		
		for (int j = i; j < keys.size(); j++) {
			if (getBlockers(specialLocations.get(keys.get(i-1)), specialLocations.get(keys.get(j)))
					.doesNotBlock(collectedKeys)) {
				temp = keys.get(i);
				keys.set(i, keys.get(j));
				keys.set(j, temp);
				collectedKeys.add(keys.get(i));
				
				currentPathLength = Integer.min( 
											getShortestPathOfPermutation(keys, collectedKeys, i + 1)
												+ getDistance(specialLocations.get(keys.get(i-1)), specialLocations.get(keys.get(i)))
											, MAX);
				if (minimalDistance > currentPathLength) {
					minimalDistance = currentPathLength;
				}
				
				collectedKeys.remove(keys.get(i));
				temp = keys.get(i);
				keys.set(i, keys.get(j));
				keys.set(j, temp);
			}
		}
		return minimalDistance;
	}

	private char grid(int i, int j) {
    	return grid.get(Pair.with(i, j));
	}

	private Integer getDistance(Pair<Integer, Integer> i, Pair<Integer, Integer> j) {
		return distances.getOrDefault(i, new HashMap<>()).getOrDefault(j, MAX);
	}

    private Blockers getBlockers(Pair<Integer, Integer> i, Pair<Integer, Integer> j) {
		return blockers.getOrDefault(i, new HashMap<>()).getOrDefault(j, new Blockers());
	}
}
