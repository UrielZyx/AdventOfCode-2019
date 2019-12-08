package intcode.operations;

import java.util.Arrays;

import intcode.IntcodeMachine;
import intcode.operations.Params.ParamType;

public class AddOperation extends AbstractOperation{
	
	public AddOperation() {
		setParameters(Arrays.asList(ParamType.READ, ParamType.READ, ParamType.WRITE));
	}
	
	@Override
	protected void doOperation(Params params, IntcodeMachine machine) {
		
		machine.setPositionValue(
				params.getWriteParam(0), 
				params.getReadParam(0) + params.getReadParam(1));
	}
	
}