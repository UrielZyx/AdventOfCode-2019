package intcode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Intcode {

	enum OpCode {
		EMPTY(0,(m,p)->false),
		ADD(3,addOperation),
		MULTIPLY(3,multiplyOperation),
		HALT(0,(m,p)->false);
		
		final int numberOfParameters;
		final BiFunction<int[], List<Integer>, Boolean> operation;
		
		private OpCode(int numberOfParameters, BiFunction<int[], List<Integer>, Boolean> operation) {
			this.numberOfParameters=numberOfParameters;
			this.operation=operation;
		}
	}
	
	private int[] memory;
	
	public Intcode(int[] memory) {
		this.memory=memory;
	}

	public void runProgram() {
		OpCode instruction;
		List<Integer> params;
		for (int i = 0;i< memory.length; i += instruction.numberOfParameters+1) {
			instruction=getOpcode(memory[i]);
			params = new ArrayList<>();
			for (int j = 1; j <= instruction.numberOfParameters; j++) {
				params.add(i+j);
			}
			if(!instruction.operation.apply(memory, params)) {
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
		
	private static int valueAtAddress(int i, int[] memory) {
		return memory[memory[i]];
	}

	//Operations:

	private static final BiFunction<int[], List<Integer>, Boolean> addOperation = (memory, params)->{
		memory[memory[params.get(2)]] = valueAtAddress(params.get(0), memory) + valueAtAddress(params.get(1), memory);
		return true;
	};
	private static final BiFunction<int[], List<Integer>, Boolean> multiplyOperation = (memory, params)->{
		memory[memory[params.get(2)]] = valueAtAddress(params.get(0), memory) * valueAtAddress(params.get(1), memory);
		return true;
	};
}