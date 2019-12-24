package day24;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import day11.Grid;
import myMath.Direction;

public class RecursiveBugTracker extends BugTracker {

	int lowestLevel = 0, highestLevel = 0;
	
	public RecursiveBugTracker(List<String> lines) {
		super(lines);
	}

	@Override
	protected void nextStep() {
		IntStream.rangeClosed(lowestLevel - 1, highestLevel + 1)
			.forEach(level -> 
				IntStream.range(1, getGrid(level).length - 1)
					.forEach(i -> 
						IntStream.range(1, getGrid(level)[i].length - 1)
							.forEach(j -> {
								if (i == 3 && j == 3) {
									return;
								}
								long count = countAdjacentBugs(i, j, level);
								if(updateCell(i, j, level, count)) {
									lowestLevel = Integer.min(lowestLevel, level);
									highestLevel = Integer.max(highestLevel, level);
								}
							}
					)
			)
		);
		updateTempValues();
	}
	
	@Override
	public long getNumberOfBugs() {
		return IntStream.rangeClosed(lowestLevel, highestLevel)
			.mapToLong(this::getNumberOfBugs)
			.sum();
	}

	@Override
	protected void updateTempValues() {
		IntStream.rangeClosed(lowestLevel, highestLevel)
			.forEach(this::updateTempValues);
	}
	
	private long countAdjacentBugs(int i, int j, int level) {
		return countSameLevelAdjacentBugs(i, j, level) + countDifferentLevelAdjacentBugs(i, j, level);
	}

	private long countSameLevelAdjacentBugs(int i, int j, int level) {
		return Arrays.stream(Direction.values())
		.map(d -> d.directionVector)
		.map(p -> getGrid(level)[i + p.getValue0()][j + p.getValue1()])
		.filter(c -> c == '#' || c == 'B')
		.count();
	}

	private long countDifferentLevelAdjacentBugs(int i, int j, int level) {
		long sum = 0;
		char c;
		
		if (i == 1) {
			c = getGrid(level - 1)[2][3];
			if (c == '#' || c == 'B') {
				sum++;
			}
		}
		if (i == 5) {
			c = getGrid(level - 1)[4][3];
			if (c == '#' || c == 'B') {
				sum++;
			}
		}
		if (j == 1) {
			c = getGrid(level - 1)[3][2];
			if (c == '#' || c == 'B') {
				sum++;
			}
		}
		if (j == 5) {
			c = getGrid(level - 1)[3][4];
			if (c == '#' || c == 'B') {
				sum++;
			}
		}

		if (i == 2 && j == 3) {
			sum += IntStream.range(1, 6)
					.mapToObj(t -> getGrid(level + 1)[1][t])
					.filter(b -> b == '#' || b == 'B')
					.count();
		}
		if (i == 4 && j == 3) {
			sum += IntStream.range(1, 6)
					.mapToObj(t -> getGrid(level + 1)[5][t])
					.filter(b -> b == '#' || b == 'B')
					.count();
		}
		if (i == 3 && j == 2) {
			sum += IntStream.range(1, 6)
					.mapToObj(t -> getGrid(level + 1)[t][1])
					.filter(b -> b == '#' || b == 'B')
					.count();
		}
		if (i == 3 && j == 4) {
			sum += IntStream.range(1, 6)
					.mapToObj(t -> getGrid(level + 1)[t][5])
					.filter(b -> b == '#' || b == 'B')
					.count();
		}
		
		return sum;
	}
}
