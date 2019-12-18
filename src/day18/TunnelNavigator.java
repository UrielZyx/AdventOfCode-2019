package day18;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javatuples.Pair;

public class TunnelNavigator {

    private static final int MAX = Integer.MAX_VALUE / 2;
    int height, width;
    char[][] grid;
    int[][][][] distances = new int[0][0][0][0];
    Blockers[][][][] blockers = new Blockers[0][0][0][0];
    Map<Character, Pair<Integer, Integer>> specialLocations = new HashMap<>();

	public TunnelNavigator(List<String> input) {
        height = input.size();
        width = input.get(0).length();
        grid = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = input.get(i).charAt(j);
                specialLocations.put(grid[i][j], Pair.with(i, j));
            }
        }
        specialLocations.remove('#');
        specialLocations.remove('.');
	}

	public int getShortestPathToAllKeys() {
        initPaths();
        floydWarshall();
		return getShortestPath();
	}

    private void initPaths() {
        int x, y;
        Blockers doors;
        distances = new int[height][width][height][width];
        blockers = new Blockers[height][width][height][width];
        List<Pair<Integer, Integer>> directions = 
                Arrays.asList(
                    Pair.with(1, 0), 
                    Pair.with(0, 1));

        initAsMax();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (Pair<Integer,Integer> direction : directions) {
                    x = i + direction.getValue0();
                    y = j + direction.getValue1();
                    if (hasValue(i, j, x, y)) {
                        doors = checkBlockers(i, j, x, y);
                        distances[i][j][x][y] = distances[x][y][i][j] = 1;
                        blockers[i][j][x][y] = new Blockers().add(doors);
                        blockers[x][y][i][j] = new Blockers().add(doors);
                    }
                }
            }
        }
    }

    private void initAsMax() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < height; k++) {
                    for (int l = 0; l < width; l++) {
                        distances[i][j][k][l] = MAX;
                    }
                }
            }
        }
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                distances[i][j][i][j] = 0;
                blockers[i][j][i][j] = new Blockers();
            }
        }
    }

    private boolean hasValue(int i, int j, int x, int y) {
        if (x < 0 || y < 0 || x >= height || y >= width) {
            return false;
        }
        if (grid[i][j] == '#' || grid[x][y] == '#') {
            return false;
        }
        return true;
    }

    private Blockers checkBlockers(int i, int j, int x, int y) {
        Blockers doors = new Blockers();
        if (grid[i][j] >= 'A' && grid[i][j] <= 'B') {
            doors.add(grid[i][j]);
        }
        if (grid[x][y] >= 'A' && grid[x][y] <= 'B') {
            doors.add(grid[x][y]);
        }
        return doors;
    }

    private void floydWarshall() {
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        for (int k = 0; k < height; k++) {
                            for (int l = 0; l < width; l++) {
                                if (distances[i][j][x][y] + distances[x][y][k][l] < distances[i][j][k][l]) {
                                    distances[i][j][k][l] = distances[i][j][x][y] + distances[x][y][k][l];
                                    blockers[i][j][k][l] = new Blockers()
                                        .add(blockers[i][j][x][y])
                                        .add(blockers[x][y][k][l]);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private int getShortestPath() {
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
                        + distances[l1.getValue0()][l1.getValue1()][l2.getValue0()][l2.getValue1()]
                        + "\tBlockers: "
                        + blockers[l1.getValue0()][l1.getValue1()][l2.getValue0()][l2.getValue1()].toString()
                        + "\t")));
        return 0;
    }

}
