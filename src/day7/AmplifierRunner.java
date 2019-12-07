package day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import intcode.IntcodeMachine;

public class AmplifierRunner {

    private final int[] program;

	public AmplifierRunner(int[] program) {
        this.program = program;
	}

	public int maximalOutpotOfPhases(List<Integer> phaseSettings) {
		return recursiveMaximalOutput(phaseSettings, 0);
	}

    private int recursiveMaximalOutput(List<Integer> phaseSettings, int index) {
        if (index == phaseSettings.size()) {
            return runAmplifiers(phaseSettings);
        }
        
        int maxOutput = Integer.MIN_VALUE;
        int t;

        for (int i = index; i < phaseSettings.size(); i++) {
            t=phaseSettings.get(index);
            phaseSettings.set(index, phaseSettings.get(i));
            phaseSettings.set(i, t);

            maxOutput = Integer.max(maxOutput, recursiveMaximalOutput(phaseSettings, index + 1));

            t=phaseSettings.get(index);
            phaseSettings.set(index, phaseSettings.get(i));
            phaseSettings.set(i, t);
        }
        return maxOutput;
    }

    private int runAmplifiers(List<Integer> phaseSettings) {
        List<Integer> io = new ArrayList<>();
        io.add(0);
        for (Integer phase : phaseSettings) {
            io.add(0, phase);
            io = runAmplifier(io);
            
            int output = io.get(io.size()-1);
            io = new ArrayList<>();
            io.add(output);
        }
        return io.get(0);
    }

    private List<Integer> runAmplifier(List<Integer> io) {
        IntcodeMachine machine = new IntcodeMachine(program, io);
        machine.runProgram();
        return machine.getOutput();
    }

}
