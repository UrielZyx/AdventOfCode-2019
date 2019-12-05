package intcode.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.javatuples.Pair;

import intcode.IntcodeMachine;

public abstract class AbstractOperation implements BiFunction<IntcodeMachine, Integer, Integer> {

	public enum OpCode {
		EMPTY((m,i)->-1),
		ADD(new AddOperation()),
		MULTIPLY(new MultiplyOperation()),
		INPUT(new InputOperation()),
		OUTPUT(new OutputOperation()),
		HALT((m,i)->-1);
		
		public final BiFunction<IntcodeMachine, Integer, Integer> operation;
		
		private OpCode(BiFunction<IntcodeMachine, Integer, Integer> operation) {
			this.operation=operation;
		}
	}

	private int numberOfReadParameters;
	private int numberOfWriteParameters;

	@Override
	public Integer apply (IntcodeMachine machine, Integer i) {
		Params params = getParams(machine, i, getNumberOfReadParameters(), getNumberOfWriteParameters());
		doOperation(params,machine,getParamsModes(machine, i));
		return getNewInstructionCounter(i);
	}

	protected abstract void doOperation(Params params, IntcodeMachine machine, int parameterMode);

	protected int getNumberOfReadParameters() {
		return numberOfReadParameters;
	}

	protected void setNumberOfReadParameters(int number) {
		this.numberOfReadParameters=number;
	}

	protected int getNumberOfWriteParameters() {
		return numberOfWriteParameters;
	}

	protected void setNumberOfWriteParameters(int number) {
		this.numberOfWriteParameters=number;
	}

	protected Integer getNewInstructionCounter(int i) {
		return i + getNumberOfReadParameters() + getNumberOfWriteParameters() + 1;
	}

	protected static Params getParams(IntcodeMachine machine, Integer i, int numberOfReadParameters, int numberOfWriteParameters) {
		List<Integer> readParams = new ArrayList<>();
		int j;
		for (j = 1; j <= numberOfReadParameters; j++) {
			readParams.add(i+j);
		}
		List<Integer> writeParams = new ArrayList<>();
		for (; j <= numberOfReadParameters + numberOfWriteParameters; j++) {
			writeParams.add(i+j);
		}
		return new Params(readParams, writeParams);
	}

	protected int immediateValue(int i, IntcodeMachine machine) {
		return machine.getImmediateValue(i);
	}

	protected int positionValue(int i, IntcodeMachine machine) {
		return machine.getPositionValue(i);
	}

	protected int getValue(IntcodeMachine machine, int i, int mode) {
		return machine.getValue(i, mode);
	}

	private int getParamsModes(IntcodeMachine machine, int i) {
		return machine.getImmediateValue(i) / 100;
	}
}
