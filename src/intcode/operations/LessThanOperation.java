package intcode.operations;

import java.util.List;

import org.javatuples.Pair;

import intcode.IntcodeMachine;

public class LessThanOperation extends AbstractOperation{
	
	public LessThanOperation() {
		setNumberOfReadParameters(2);
		setNumberOfWriteParameters(1);
	}
	
	@Override
	protected void doOperation(Params params, IntcodeMachine machine) {
		
		machine.setPositionValue(
				params.getWriteParam(0), 
				params.getReadParam(0) < params.getReadParam(1)? 1: 0);
	}
}