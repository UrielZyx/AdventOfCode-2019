package intcode.operations;

import java.util.List;

import org.javatuples.Pair;

import intcode.IntcodeMachine;

public class JumpIfTrueOperation extends AbstractOperation{
	
	Integer nextInstructionCounter = null;
	
	public JumpIfTrueOperation() {
		setNumberOfReadParameters(2);
		setNumberOfWriteParameters(0);
	}
	
	@Override
	protected void doOperation(Params params, IntcodeMachine machine) {
		
		nextInstructionCounter = null;
		
		if (params.getReadParam(0) != 0) {
			nextInstructionCounter = params.getReadParam(1);
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