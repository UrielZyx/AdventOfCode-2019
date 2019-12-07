package intcode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import intcode.operations.AbstractOperation.OpCode;

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
	private List<Integer> output = new ArrayList<>();
	int inputCounter = 0;
	private int index = 0;
	
	public IntcodeMachine(int[] memory) {
		this.memory=memory;
	}

	public boolean runProgram() {
		OpCode instruction;
		inputCounter = 0;
				
		while (index < memory.length) {
			instruction=getOpcode();
			if (noAvailableInput(instruction)) {
				break;
			}
			index = instruction.operation.apply(this, index); 
			if(index < 0) {
				break;
			}
		}
		return index < 0;
	}

	public void printMemory0() {
		System.out.println(memory0());
	}

	public int memory0() {
		return getImmediateValue(0);
	}

	public void printAll() {
		IntStream.of(memory).forEach(i -> System.out.print(i+", "));
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
	
	public int getNextInput() {
		return getInput().get(inputCounter++);
	}

	public IntcodeMachine setInput(List<Integer> input) {
		this.input = input;
		return this;
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
	
	public void addOutput(int value) {
		getOutput().add(value);
	}

	public List<Integer> getOutput() {
		return output;
	}

	@Override
	public String toString() {
		return memory.toString();
	}

	private OpCode getOpcode() {
		int operation = getImmediateValue(index) % 100;
		if (operation<=OpCode.HALT.ordinal()) {
			return OpCode.values()[operation];
		}
		return OpCode.EMPTY;
	}

	private boolean noAvailableInput(OpCode instruction) {
		return instruction.equals(OpCode.INPUT) && inputCounter >= input.size();
	}
}