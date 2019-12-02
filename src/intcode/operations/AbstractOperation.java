package intcode.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import intcode.Intcode;

public abstract class AbstractOperation implements BiFunction<int[], Integer, Integer> {

	private int numberOfParameters;

	@Override
	public Integer apply(int[] memory, Integer i) {
		List<Integer> params = getParams(memory, i, getNumberOfParameters());
		doOperation(params,memory);
		return getNewInstructionPointer(i);
	}

	protected abstract void doOperation(List<Integer> params, int[] memory);

	protected int getNumberOfParameters() {
		return numberOfParameters;
	}

	protected void setNumberOfParameters(int numberOfParameters) {
		this.numberOfParameters=numberOfParameters;
	}

	protected Integer getNewInstructionPointer(int i) {
		return i + getNumberOfParameters() + 1;
	}

	protected static List<Integer> getParams(int[] memory, Integer i, int numberOfParameters) {
		List<Integer> params = new ArrayList<>();
		for (int j = 1; j <= numberOfParameters; j++) {
			params.add(i+j);
		}
		return params;
	}

	protected int valueAtAddress(int i, int[] memory) {
		return Intcode.valueAtAddress(i, memory);
	}

}
