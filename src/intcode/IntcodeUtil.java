package intcode;

import java.util.Map;

public class IntcodeUtil {
	
	public static void runConfigurationSetup(int[] memory, Map<Integer, Integer> configurations) {
		for (int i : configurations.keySet()) {
			memory[i]=configurations.get(i);
		}
	}
	
	public static long getPositionModePosition(IntcodeMachine machine, long i) {
		return machine.getPositionModePosition(i);
	}
	
	public static long getImmediateModePosition(IntcodeMachine machine, long i) {
		return machine.getImmediateModePosition(i);
	}
	
	public static long getRelativeModePosition(IntcodeMachine machine, long i) {
		return machine.getRelativeModePosition(i);
	}
}
