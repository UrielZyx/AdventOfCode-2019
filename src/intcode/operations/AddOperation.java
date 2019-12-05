package intcode.operations;

import java.util.List;

import org.javatuples.Pair;

import intcode.IntcodeMachine;

public class AddOperation extends AbstractOperation{
	
	public AddOperation() {
		setNumberOfReadParameters(2);
		setNumberOfWriteParameters(1);
	}
	
	@Override
	protected void doOperation(Params params, IntcodeMachine machine, int modes) {
		
		machine.setPositionValue(
				params.getWriteParam(0), 
				params.getReadParam(machine, 0, modes % 10) + params.getReadParam(machine, 1, modes / 10));
	}
	
}