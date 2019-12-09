package intcode.operations;

import java.util.Arrays;

import intcode.IntcodeMachine;
import intcode.operations.Params.ParamType;

public class AdjustRelativeBaseOperation extends AbstractOperation {
	
	public AdjustRelativeBaseOperation() {
		setParameters(Arrays.asList(ParamType.READ));
	}

	@Override
	protected void doOperation(Params params, IntcodeMachine machine) {
		machine.adjustRelativeBase(params.getReadParam(0));
	}

}
