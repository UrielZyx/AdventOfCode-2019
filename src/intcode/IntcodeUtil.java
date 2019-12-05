package intcode;

import java.util.Map;

public class IntcodeUtil {
	
	public static void runConfigurationSetup(int[] memory, Map<Integer, Integer> configurations) {
		for (int i : configurations.keySet()) {
			memory[i]=configurations.get(i);
		}
	}
	
	public static int getPositionValue(IntcodeMachine machine, int i) {
		return machine.getPositionValue(i);
	}
	
	public static int getImmediateValue(IntcodeMachine machine, int i) {
		return machine.getImmediateValue(i);
	}
}
