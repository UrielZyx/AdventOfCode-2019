package intcode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

import intcode.operations.AddOperation;
import intcode.operations.MultiplyOperation;

public class IntcodeMachine {

	enum OpCode {
		EMPTY((m,i)->-1),
		ADD(new AddOperation()),
		MULTIPLY(new MultiplyOperation()),
		HALT((m,i)->-1);
		
		final BiFunction<IntcodeMachine, Integer, Integer> operation;
		
		private OpCode(BiFunction<IntcodeMachine, Integer, Integer> operation) {
			this.operation=operation;
		}
	}
	
	public enum ParametersMode {
		POSITION (IntcodeUtil::getPositionValue),
		IMMEDIATE (IntcodeUtil::getImmediateValue);
		
		public final BiFunction<IntcodeMachine, Integer, Integer> getValue;
		
		private ParametersMode(BiFunction<IntcodeMachine, Integer, Integer> getValue) {
			this.getValue = getValue;
		}
	}
	
	private int[] memory;
	
	public IntcodeMachine(int[] memory) {
		this.memory=memory;
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
	
	@Override
	public String toString() {
		return memory.toString();
	}
}