package intcode.operations;

import java.util.List;

import org.javatuples.Pair;

import intcode.IntcodeMachine;

public class JumpIfFalseOperation extends AbstractOperation{
	
	Integer nextInstructionCounter = null;
	
	public JumpIfFalseOperation() {
		setNumberOfReadParameters(2);
		setNumberOfWriteParameters(0);
	}
	
	@Override
	protected void doOperation(Params params, IntcodeMachine machine, int modes) {
		
		nextInstructionCounter = null;
		
		if (params.getReadParam(machine, 0, modes % 10) == 0) {
			nextInstructionCounter = params.getReadParam(machine, 1, modes / 10);
		}
	}
	
	@Override
	protected Integer getNewInstructionCounter(int i) {
		if (nextInstructionCounter != null) {
			return nextInstructionCounter;
		}
		return super.getNewInstructionCounter(i);
	}	
}