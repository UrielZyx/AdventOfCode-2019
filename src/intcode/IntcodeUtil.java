package intcode;

import java.util.Map;

public class IntcodeUtil {
	
	public static void runConfigurationSetup(int[] memory, Map<Integer, Integer> configurations) {
		for (int i : configurations.keySet()) {
			memory[i]=configurations.get(i);
		}
	}
	
	public static long getPositionValue(IntcodeMachine machine, long i) {
		return machine.getPositionValue(i);
	}
	
	public static long getImmediateValue(IntcodeMachine machine, long i) {
		return machine.getImmediateValue(i);
	}
	
	public static long getRelativeValue(IntcodeMachine machine, long i) {
		return machine.getRelativeValue(i);
	}
}
