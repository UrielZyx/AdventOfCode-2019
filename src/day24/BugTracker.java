package day24;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import myMath.Direction;

public class BugTracker {

	char grid[][] = new char[7][7];
	
	public BugTracker(List<String> lines) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = '.';
			}
		}

		for (int i = 1; i < grid.length - 1; i++) {
			for (int j = 1; j < grid[i].length - 1; j++) {
				grid[i][j] = lines.get(i - 1).charAt(j - 1);
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

	private void nextStep() {
		IntStream.range(1, grid.length - 1)
			.forEach(i -> IntStream.range(1, grid[i].length - 1)
					.forEach(j -> {
						long count = Arrays.stream(Direction.values())
						.map(d -> d.directionVector)
						.map(p -> grid[i + p.getValue0()][j + p.getValue1()])
						.filter(c -> c == '#' || c == 'B')
						.count();
						if (grid[i][j] == '#') {
							if (count == 1) {
								grid[i][j] = 'B';	
							}
						} else if (grid[i][j] == '.') {
							if (count == 1 || count == 2) {
								grid[i][j] = 'E';
							}
						}
					}));
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 'E' || grid[i][j] == 'B') {
					grid[i][j] = '#';
				} else {
					grid[i][j] = '.';
				}
			}
		}
		printGrid();
	}

	public int getBioDiversity() {
		int sum = 0;
		int positionValue = 1;
		
		for (int i = 1; i < grid.length - 1; i++) {
			for (int j = 1; j < grid[i].length - 1; j++) {
				sum += (grid[i][j] == '#')? positionValue: 0;
				positionValue *= 2;
			}
		}
		
		return sum;
	}

	private void printGrid() {
		for (int i = 1; i < grid.length - 1; i++) {
			for (int j = 1; j < grid[i].length - 1; j++) {
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	} 

}
