package day24;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import myMath.Direction;

public class BugTracker {

	private Map<Integer, Character[][]> grid = new HashMap<>();
	
	public BugTracker(List<String> lines) {
		for (int i = 0; i < getGrid().length; i++) {
			for (int j = 0; j < getGrid()[i].length; j++) {
				getGrid()[i][j] = '.';
			}
		}

		for (int i = 1; i < getGrid().length - 1; i++) {
			for (int j = 1; j < getGrid()[i].length - 1; j++) {
				getGrid()[i][j] = lines.get(i - 1).charAt(j - 1);
			}
		}
		
		printGrid();
	}

	public void findFirstRepeatingState() {
		Set<Integer> previousDiversities = new HashSet<>();
		int currentDiversity = getBioDiversity();
		while (previousDiversities.add(currentDiversity)) {
			nextStep();
			currentDiversity = getBioDiversity();
		}
	}

	public void runSteps(int n) {
		for (int i = 0; i < n; i++) {
			nextStep();
		}
	}

	public long getNumberOfBugs() {
		return getNumberOfBugs(0);
	} 

	public long getNumberOfBugs(int l) {
		return Arrays.stream(getGrid(l))
			.flatMap(Arrays::stream)
			.filter(c -> c == '#')
			.count();
	} 

	public int getBioDiversity() {
		int sum = 0;
		int positionValue = 1;
		
		for (int i = 1; i < getGrid().length - 1; i++) {
			for (int j = 1; j < getGrid()[i].length - 1; j++) {
				sum += (getGrid()[i][j] == '#')? positionValue: 0;
				positionValue *= 2;
			}
		}
		
		return sum;
	}
		
	protected void nextStep() {
		IntStream.range(1, getGrid().length - 1)
			.forEach(i -> IntStream.range(1, getGrid()[i].length - 1)
					.forEach(j -> {
						long count = Arrays.stream(Direction.values())
						.map(d -> d.directionVector)
						.map(p -> getGrid()[i + p.getValue0()][j + p.getValue1()])
						.filter(c -> c == '#' || c == 'B')
						.count();
						updateCell(i, j, count);
					}));
		
		updateTempValues();
		printGrid();
	}

	protected void updateTempValues() {
		updateTempValues(0);
	}

	protected void updateTempValues(int level) {
		for (int i = 0; i < getGrid(level).length; i++) {
			for (int j = 0; j < getGrid(level)[i].length; j++) {
				if (getGrid(level)[i][j] == 'E' || getGrid(level)[i][j] == 'B') {
					getGrid(level)[i][j] = '#';
				} else {
					getGrid(level)[i][j] = '.';
				}
			}
		}
	}

	protected void updateCell(int i, int j, long count) {
		updateCell(i, j, 0, count);
	}

	protected boolean updateCell(int i, int j, int level, long count) {
		if (getGrid(level)[i][j] == '#') {
			if (count == 1) {
				getGrid(level)[i][j] = 'B';
				return true;
			}
		} else if (getGrid(level)[i][j] == '.') {
			if (count == 1 || count == 2) {
				getGrid(level)[i][j] = 'E';
				return true;
			}
		}
		return false;
	}

	protected void printGrid() {
		for (int i = 1; i < getGrid().length - 1; i++) {
			for (int j = 1; j < getGrid()[i].length - 1; j++) {
				System.out.print(getGrid()[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	protected Character[][] getGrid() {
		return getGrid(0);
	}

	protected Character[][] getGrid(int i) {
		Character[][] g = grid.get(i);
		if (g == null) {
			g = new Character[7][7];
			for (int j = 0; j < g.length; j++) {
				for (int k = 0; k < g[j].length; k++) {
					g[j][k] = '.';
				}
			}
			grid.put(i, g);
		}
		return g;
	}
}
