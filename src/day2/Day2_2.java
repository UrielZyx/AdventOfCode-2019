package day2;

import java.util.HashMap;
import java.util.Map;

import intcode.Intcode;
import intcode.IntcodeUtil;

public class Day2_2 {

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				int[] memory = new int[] {1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,9,1,19,1,19,5,23,1,9,23,27,2,27,6,31,1,5,31,35,2,9,35,39,2,6,39,43,2,43,13,47,2,13,47,51,1,10,51,55,1,9,55,59,1,6,59,63,2,63,9,67,1,67,6,71,1,71,13,75,1,6,75,79,1,9,79,83,2,9,83,87,1,87,6,91,1,91,13,95,2,6,95,99,1,10,99,103,2,103,9,107,1,6,107,111,1,10,111,115,2,6,115,119,1,5,119,123,1,123,13,127,1,127,5,131,1,6,131,135,2,135,13,139,1,139,2,143,1,143,10,0,99,2,0,14,0};
				Map<Integer, Integer> config = new HashMap<>();
				config.put(1, i);
				config.put(2, j);
				IntcodeUtil.runConfigurationSetup(memory,config);
				Intcode program = new Intcode(memory);
				program.runProgram();
				if (program.result()==19690720) {
					System.out.println(i+", "+j);
				}
			}
		}
	}
	
}
