package intcode.operations;

import java.util.List;

import org.javatuples.Pair;

import intcode.IntcodeMachine;

public class OutputOperation extends AbstractOperation{
	
	public OutputOperation() {
		setNumberOfReadParameters(1);
		setNumberOfWriteParameters(0);
	}
	
	@Override
	protected void doOperation(Params params, IntcodeMachine machine) {
		
		machine.addOutput(params.getReadParam(0));
	}
	
}