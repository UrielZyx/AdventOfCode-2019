package day11;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.javatuples.Pair;

import intcode.IntcodeMachine;

public class PaintingRobot {
	
	private enum Mode {
		PAINT,
		MOVE
	}
	
	private enum Direction {
		UP (0, 1),
		RIGHT (1, 0),
		DOWN (0, -1),
		LEFT (-1, 0);
		
		int x,y;
		
		private Direction(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	private IntcodeMachine machine;
	Map<Pair<Integer, Integer>, Integer> grid = new HashMap<>();
	int topBorder = 0;
	int rightBorder = 0;
	int bottomBorder = 0;
	int leftBorder = 0;
	
	private Pair<Integer, Integer> position = Pair.with(0, 0);
	private Direction direction = Direction.UP;
	private Mode mode = Mode.PAINT; 

	private Set<Pair<Integer, Integer>> paintedPanels = new HashSet<>();

	public PaintingRobot(String memory) {
		machine = new IntcodeMachine(memory)
				.setInputIterator(new GridIterator(this))
				.setOutputHandler(this::readOutput);
	}

	public int[][] paint(Map<Pair<Integer, Integer>, Integer> initialGrid) {
		grid = initialGrid;
		return paint();
	}

	public int[][] paint() {
		machine.runProgram();
		return gridMatrix();
	}

	public int getNumberOfPaintedPanels() {
		return paintedPanels.size();
	}

	public Pair<Integer, Integer> getPosition() {
		return position;
	}

	public void setPosition(Pair<Integer, Integer> position) {
		this.position = position;
	}

	public long getPannelColor(Pair<Integer, Integer> position) {
		return grid.getOrDefault(position, 0);	
	}

	private int[][] gridMatrix() {
		int [][] matrix = initializeMatrix();
		
		for (Pair<Integer, Integer> p : grid.keySet()) {
			matrix[p.getValue1() - bottomBorder][p.getValue0() - leftBorder] = grid.get(p);
		}
		
		return matrix;
	}

	private int[][] initializeMatrix() {
		int height = topBorder - bottomBorder + 1, width = rightBorder - leftBorder + 1;
		int[][] result = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				result[i][j] = 0;
			}
		}
		return result;
	}

	private void readOutput(Long output) {
		if (mode.equals(Mode.PAINT)) {
			grid.put(position, output.intValue());
			paintedPanels.add(position);
			updateBorders();
			mode = Mode.MOVE;
		} else if (mode.equals(Mode.MOVE)) {
			direction = Direction.values()[(direction.ordinal() + (int)(2 * output - 1) + Direction.values().length) % Direction.values().length];
			position = Pair.with(position.getValue0() + direction.x, position.getValue1() + direction.y);
			mode = Mode.PAINT;
		}
	}

	private void updateBorders() {
		if (position.getValue0() < leftBorder) {
			leftBorder = position.getValue0();
		}
		if (position.getValue0() > rightBorder) {
			rightBorder = position.getValue0();
		}
		if (position.getValue1() < bottomBorder) {
			bottomBorder = position.getValue1();
		}
		if (position.getValue1() > topBorder) {
			topBorder = position.getValue1();
		}
	}
}
