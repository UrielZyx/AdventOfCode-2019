package day17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import intcode.IntcodeMachine;

public class ScaffoldingInformation {

	IntcodeMachine machine;
	int sumOfIntersectionParameters = 0;
	List<Character> programOutput = new ArrayList<>();
	char[][] grid;
	
	public ScaffoldingInformation(String program) {
		machine = new IntcodeMachine(program);
	}

	public int checkIntersections() {
		machine
			.setOutputHandler(this::outputHandler)
			.runProgram();
		int lineLength = programOutput.indexOf((char)10) + 1;
		grid = new char[programOutput.size() / lineLength][lineLength];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = getCell(i, j);
				if (i > 0 && j > 0 && j < grid[i].length - 1 && grid[i][j] == 35 && grid[i-1][j] == 35 && grid[i][j-1] == 35 && getCell(i, j+1) == 35) {
					sumOfIntersectionParameters += i * j;
					System.out.print("O");
				} else {

					System.out.print(grid[i][j]);
				}
			}
		}
		return sumOfIntersectionParameters;
	}

	private Character getCell(int i, int j) {
		return programOutput.get(i * grid[i].length + j);
	}
	
	private void outputHandler(Long output) {
		programOutput.add((char)output.intValue());
	}
	

	public long notifyRobots(String inputs) {
		List<Integer> in = new ArrayList<>();
		List<Long> out;
		for (int i = 0; i < inputs.length(); i++) {
			in.add((int)inputs.charAt(i));
		}
		machine
			.replaceInput(in)
			.runProgram();
		out = machine.getOutput();
		return out.get(out.size() - 1);
	}
}