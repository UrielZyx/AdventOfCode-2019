package intcode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

import intcode.operations.AbstractOperation.OpCode;
import intcode.operations.AddOperation;
import intcode.operations.MultiplyOperation;

public class IntcodeMachine {
	
	public enum ParametersMode {
		POSITION (IntcodeUtil::getPositionValue),
		IMMEDIATE (IntcodeUtil::getImmediateValue);
		
		public final BiFunction<IntcodeMachine, Integer, Integer> getValue;
		
		private ParametersMode(BiFunction<IntcodeMachine, Integer, Integer> getValue) {
			this.getValue = getValue;
		}
	}
	
	private int[] memory;
	private List<Integer> input;
	private List<Integer> output;
	
	public IntcodeMachine(int[] memory) {
		this(memory, new ArrayList<>());
	}
	
	public IntcodeMachine(int[] memory, List<Integer> input) {
		this.memory=memory;
		this.input = input;
		this.output = new ArrayList<>();
	}

	public void runProgram() {
		OpCode instruction;
		int i = 0;
				
		while (i < memory.length) {
			instruction=getOpcode(getImmediateValue(i));
			i = instruction.operation.apply(this, i); 
			if(i<0) {
				break;
			}
		}
	}

	public void printResult() {
		System.out.println(result());
	}

	public int result() {
		return result(0);
	}
	
	public int result(int i) {
		return getImmediateValue(i);
	}

	public void printAll() {
		IntStream.of(memory).forEach(i -> System.out.print(i+", "));
	}

	private OpCode getOpcode(int i) {
		int operation = i % 100;
		if (operation<=OpCode.HALT.ordinal()) {
			return OpCode.values()[operation];
		}
		return OpCode.EMPTY;
	}
		
	public int getPositionValue(int i) {
		return memory[getImmediateValue(i)];
	}

	public int getImmediateValue(int i) {
		return memory[i];
	}
	
	public void setPositionValue (int i, int value) {
		memory[getImmediateValue(i)] = value;
	}

	public int getValue(int i, int mode) {
		return ParametersMode.values()[mode].getValue.apply(this, i);
	}
	
	public List<Integer> getInput() {
		return input;
	}

	public Integer getOutput(int i) {
		List<Integer> out = getOutput();
		if (i > out.size()) {
			return null;
		}
		return getOutput().get(i);
	}

	public List<Integer> getOutput() {
		return output;
	}

	@Override
	public String toString() {
		return memory.toString();
	}
}