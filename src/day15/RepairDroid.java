package day15;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import org.javatuples.Pair;

import intcode.IntcodeMachine;

public class RepairDroid {
	
	enum Direction {
		EMPTY (0,0),
		NORTH (0, 1),
		SOUTH (0, -1),
		WEST (-1, 0),
		EAST (1, 0);
		
		int x, y;
		
		private Direction(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	IntcodeMachine machine;
	Map<Pair<Integer, Integer>, Long> room = new HashMap<>();
	Stack<Pair<Integer, Integer>> path = new Stack<>();
	int shortestPathLength = Integer.MAX_VALUE, maxX=0, maxY=0, minX=0, minY=0;
	boolean backtracing = false;
	Direction currentDirection = Direction.NORTH;
	private Iterator<Long> directionIterator = new Iterator<Long>() {
		
		@Override
		public Long next() {
			return Long.valueOf(currentDirection.ordinal());
		}
		
		@Override
		public boolean hasNext() {
			return currentDirection != null;
		}
	};

	public RepairDroid(String input) {
		machine = new IntcodeMachine (input)
				.setInputIterator(directionIterator)
				.setOutputHandler(this::outputHandler);
		path.push(Pair.with(0, 0));
		room.put(Pair.with(0, 0), 1L);
	}

	public int getLengthOfShortestPath() {
		machine.runProgram();
		printGrid();
		return shortestPathLength;
	}

	private void printGrid() {
		System.out.println("x range: " + minX + "," + maxX + "\ty range: " + minY + "," + maxY);
		for (int i = maxY; i >= minY; i--) {
			for (int j = minX; j <= maxX; j++) {
				if (j == 0 && i == 0) {
					System.out.print("*");
				} else {
					long t = room.getOrDefault(Pair.with(j, i), -1L);
					System.out.print(t==0?"#":t==1?".":t==-1?" ":"@");	
				}
			}
			System.out.println();
		}		
	}

	private void outputHandler(Long output) {
		System.out.println("Location: " + path.peek().getValue0() + "," + path.peek().getValue1() + "\tdirection: " + currentDirection.toString() + "\tOutput: " + output);
		if (!backtracing) {
			handleNewOutput(output);
		}
		backtracing = false;
		boolean canMove = false;
		for (Direction direction : Direction.values()) {
			if (!room.containsKey(incrementedLocation(direction))) {
				currentDirection = direction;
				canMove = true;
				break;
			}
		}
		
		if (!canMove) {
			backTrace();
			if (path.isEmpty()) {
				currentDirection = null;
			}
		}
	}

	private void handleNewOutput(Long output) {
		Pair<Integer, Integer> nextLocation = incrementedLocation(currentDirection);
		room.put(nextLocation, output);
		updateBorders(nextLocation);
		if (output != 0) {
			path.add(nextLocation);
		}
		if (output == 2 && path.size() <= shortestPathLength) {
			shortestPathLength = path.size() - 1;
		}
	}

	private void updateBorders(Pair<Integer, Integer> location) {
		if (location.getValue0() < minX) {
			minX = location.getValue0();
		}
		if (location.getValue0() > maxX) {
			maxX = location.getValue0();
		}
		if (location.getValue1() < minY) {
			minY = location.getValue1();
		}
		if (location.getValue1() > maxY) {
			maxY = location.getValue1();
		}
	}

	private void incrementDirection() {
		switch (currentDirection) {
		case NORTH:
			currentDirection = Direction.SOUTH;
			break;
		case SOUTH:
			currentDirection = Direction.EAST;
			break;
		case EAST:
			currentDirection = Direction.WEST;
			break;
		case WEST:
			currentDirection = Direction.NORTH;
			break;
		default:
			currentDirection = null;
		}
	}

	private Pair<Integer, Integer> incrementedLocation(Direction direction) {
		return incrementedLocation(path.peek(), direction);
	}

	private Pair<Integer, Integer> incrementedLocation(Pair<Integer, Integer> location, Direction direction) {
		return Pair.with(location.getValue0() + direction.x, location.getValue1() + direction.y);
	}

	private void backTrace() {
		backtracing = true;
		Pair<Integer, Integer> currLocation = path.pop();
		if (path.isEmpty()) {
			return;
		}
		Pair<Integer, Integer> prevLocation = path.peek();
		int xMovement = prevLocation.getValue0() - currLocation.getValue0();
		int yMovement = prevLocation.getValue1() - currLocation.getValue1();
		Arrays.stream(Direction.values()).forEach(
				dir -> {
					if (dir.x == xMovement && dir.y == yMovement) {
						currentDirection = dir;
					}});
	}

}
