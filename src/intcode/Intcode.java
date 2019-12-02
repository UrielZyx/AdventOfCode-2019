package intcode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

import intcode.operations.AddOperation;
import intcode.operations.MultiplyOperation;

public class Intcode {

	enum OpCode {
		EMPTY((m,i)->-1),
		ADD(new AddOperation()),
		MULTIPLY(new MultiplyOperation()),
		HALT((m,i)->-1);
		
		final BiFunction<int[], Integer, Integer> operation;
		
		private OpCode(BiFunction<int[], Integer, Integer> operation) {
			this.operation=operation;
		}
	}
	
	private int[] memory;
	
	public Intcode(int[] memory) {
		this.memory=memory;
	}

	public void runProgram() {
		OpCode instruction;
		int i = 0;
				
		while (i < memory.length) {
			instruction=getOpcode(memory[i]);
			i = instruction.operation.apply(memory, i); 
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
		return memory[i];
	}

	public void printAll() {
		IntStream.of(memory).forEach(i -> System.out.print(i+", "));
	}

	private OpCode getOpcode(int i) {
		if (i<=OpCode.HALT.ordinal()) {
			return OpCode.values()[i];
		}
		return OpCode.EMPTY;
	}
	
	private int valueAtAddress(int i) {
		return valueAtAddress(i, memory);
	}
		
	public static int valueAtAddress(int i, int[] memory) {
		return memory[memory[i]];
	}
}