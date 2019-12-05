package intcode.operations;

import java.util.List;

import org.javatuples.Pair;

import intcode.IntcodeMachine;

public class InputOperation extends AbstractOperation{
	
	public InputOperation() {
		setNumberOfReadParameters(0);
		setNumberOfWriteParameters(1);
	}
	
	@Override
	protected void doOperation(Params params, IntcodeMachine machine) {
		
		machine.setPositionValue(params.getWriteParam(0), machine.getNextInput());
	}
	
}