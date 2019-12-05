package intcode.operations;

import java.util.Arrays;

import intcode.IntcodeMachine;
import intcode.operations.Params.ParamType;

public class InputOperation extends AbstractOperation{
	
	public InputOperation() {
		setParameters(Arrays.asList(ParamType.WRITE));
	}
	
	@Override
	protected void doOperation(Params params, IntcodeMachine machine) {
		
		machine.setPositionValue(params.getWriteParam(0), machine.getNextInput());
	}
	
}