package day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javatuples.Pair;

import intcode.IntcodeMachine;

public class AmplifierRunner {
	/***********************************************************************
	 * AmplifierRunner was broken while working on Day 9.
	 * I hate AmplifierRunner anyway and hope to never see it again.
	 * As long as it doesn't appear again, I'm not fixing it. Sorry.
	 * It worked in aa87b378b845974e534a1af7bddf24388b6fefcb
	 ***********************************************************************/

    private final int[] program;
    private boolean feedbackLoopMode;
    Map<Integer,IntcodeMachine> machines = new HashMap<>();
    private boolean isFirstTime;

	public AmplifierRunner(int[] program) {
        this.program = program;
	}

	public int maximalOutpotOfPhases(List<Integer> phaseSettings) {
        return maximalOutpotOfPhases(phaseSettings, false);
	}

	public int maximalOutpotOfPhases(List<Integer> phaseSettings, boolean runInFeedbackLoopMode) {
		feedbackLoopMode = runInFeedbackLoopMode;
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
        Pair<Boolean,List<Integer>> executionResult = Pair.with(false, new ArrayList<>());
        isFirstTime = true;

        for (int i = 0; !executionResult.getValue0(); i++) {
            executionResult = runAmplifier(io, i, phaseSettings);
            io = executionResult.getValue1();
            int output = io.get(io.size()-1);
            io = new ArrayList<>();
            io.add(output);
        }
        return io.get(0);
    }

    private Pair<Boolean,List<Integer>> runAmplifier(List<Integer> io, int i, List<Integer> phases) {
        boolean amplifierHalted, isFinalValue = i % phases.size() == phases.size() - 1;

        updatePhaseForFirstTime(io, i, phases, isFinalValue);

        IntcodeMachine machine = machines.get(i % phases.size()).addInput(io);
        amplifierHalted = machine.runProgram();
        if (feedbackLoopMode) {
            isFinalValue = Boolean.logicalAnd(isFinalValue, amplifierHalted);
        }
        return Pair.with(isFinalValue, machine.getOutput().stream().map(l->l.intValue()));
    }

    private void updatePhaseForFirstTime(List<Integer> io, int i, List<Integer> phases, boolean isFinalValue) {
        if (isFirstTime) {
            io.add(0, phases.get(i));   
            machines.put(i, new IntcodeMachine(program.clone()).addInput(io));
        }
        if (isFinalValue) {
            isFirstTime = false;
        }
    }
}
