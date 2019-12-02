package intcode.operations;

import java.util.List;

public class MultiplyOperation extends AbstractOperation{
	
	public MultiplyOperation() {
		setNumberOfParameters(3);
	}
	
	@Override
	protected void doOperation(List<Integer> params, int[] memory) {
		memory[memory[params.get(2)]] = valueAtAddress(params.get(0), memory) * valueAtAddress(params.get(1), memory);
	}

}
