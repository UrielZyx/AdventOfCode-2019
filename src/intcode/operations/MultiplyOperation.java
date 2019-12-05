package intcode.operations;

import java.util.List;

import intcode.IntcodeMachine;

public class MultiplyOperation extends AbstractOperation{
	
	public MultiplyOperation() {
		setNumberOfReadParameters(2);
		setNumberOfWriteParameters(1);
	}
	
	@Override
	protected void doOperation(Params params, IntcodeMachine machine, int modes) {
		
		machine.setPositionValue(
				params.getWriteParam(0), 
				params.getReadParam(machine, 0, modes % 10) * params.getReadParam(machine, 1, modes / 10));
	}

}
