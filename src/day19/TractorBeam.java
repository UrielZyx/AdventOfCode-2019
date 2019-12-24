package day19;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javatuples.Pair;

import intcode.IntcodeMachine;

public class TractorBeam {

	IntcodeMachine machine;
	int height = 50, width = 50;
	Map<Pair<Integer, Integer>, Long> grid = new HashMap<>();
	String program;
	int i, j;
	
	public TractorBeam(String program) {
		this.program = program;
	}

	public long NumberOfpoints() {
		
		for (i = 0; i < height; i++) {
			for (j = 0; j < width; j++) {
				checkPoint();
			}
		}
		
		return grid
				.keySet()
				.stream()
				.map(grid::get)
				.mapToLong(Long::longValue)
				.sum();
	}

	public List<Integer> findDistanceFromShip() {
		boolean shipFits = false;
		int xBorder = 0, yBorder = 0;
		for (i = xBorder; !shipFits; i++) {
			for (j = yBorder; j <= i && !shipFits; j++) {
				checkPoint();
				if (grid.get(Pair.with(i, j)) == 1) {
					if (i >= 99 && j >= 99) {
						shipFits = true;
						for (int x = i - 99; x <= i; x++) {
							for (int y = j - 99; y <= j; y++) {
								shipFits = shipFits && grid.get(Pair.with(x, y)) == 1;
							}
						}
					}
				}
//				else {
//					xBorder = i + 1;
//					yBorder = j + 1;
//				}
			}
		}
		
		return Arrays.asList(i - 100, j - 100);
	}

	private void checkPoint() {
		new IntcodeMachine(program)
			.addInput(Arrays.asList(i, j))
			.setOutputHandler(o -> grid.put(Pair.with(i, j), o))
			.runProgram();
	}

}
