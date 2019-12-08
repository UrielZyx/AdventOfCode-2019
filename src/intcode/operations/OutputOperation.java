package intcode.operations;

import java.util.Arrays;

import intcode.IntcodeMachine;
import intcode.operations.Params.ParamType;

public class OutputOperation extends AbstractOperation{
	
	public OutputOperation() {
		setParameters(Arrays.asList(ParamType.READ));
	}
	
	@Override
	protected void doOperation(Params params, IntcodeMachine machine) {
		
		machine.addOutput(params.getReadParam(0));
	}
	
}