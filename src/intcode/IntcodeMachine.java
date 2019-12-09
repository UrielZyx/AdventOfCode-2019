package intcode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import intcode.operations.AbstractOperation.OpCode;

public class IntcodeMachine {
	
	public enum ParametersMode {
		POSITION (IntcodeUtil::getPositionValue),
		IMMEDIATE (IntcodeUtil::getImmediateValue);
		
		public final BiFunction<IntcodeMachine, Long, Long> getValue;
		
		private ParametersMode(BiFunction<IntcodeMachine, Long, Long> getValue) {
			this.getValue = getValue;
		}
	}
	
	private Map<Long,Long> memory;
	private List<Long> input;
	private List<Long> output = new ArrayList<>();
	int inputCounter = 0;
	private long index = 0;
	
	public IntcodeMachine(int[] memory) {
		for (int i = 0; i < memory.length; i++) {
			this.memory.put((long)i, (long)memory[i]);
		}
	}
	
	public IntcodeMachine(Long[] memory) {
		for (int i = 0; i < memory.length; i++) {
			this.memory.put((long)i, memory[i]);
		}
	}
	
	public IntcodeMachine(List<Long> memory) {
		for (int i = 0; i < memory.size(); i++) {
			this.memory.put((long)i, memory.get(i));
		}
	}

	public boolean runProgram() {
		OpCode instruction;
		inputCounter = 0;
				
		while (index < memory.size()) {
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

	public long memory0() {
		return getImmediateValue(0);
	}
		
	public long getPositionValue(long i) {
		return memory.get(getImmediateValue(i));
	}
	
	public void setPositionValue (long i, long value) {
		memory.put(getImmediateValue(i), value);
	}

	public long getImmediateValue(long i) {
		return memory.get(i);
	}

	public long getValue(long i, int mode) {
		return ParametersMode.values()[mode].getValue.apply(this, i);
	}
	
	public long getNextInput() {
		return getInput().get(inputCounter++);
	}

	public IntcodeMachine setInput(List<? extends Number> input) {
		this.input.addAll(toLongList(input));
		return this;
	}
	
	@SuppressWarnings("unchecked")
	private List<Long> toLongList(List<? extends Number> list) {
		List<Long> result = new ArrayList<>();
		for (Number number : list) {
			if (number instanceof Long) {
				return (List<Long>)list;
			}
			if (number instanceof Integer) {
				result.add(((Integer)number).longValue());
			}
		}
		return result;
	}

	public List<Long> getInput() {
		return input;
	}

	public Long getOutput(int i) {
		List<Long> out = getOutput();
		if (i > out.size()) {
			return null;
		}
		return getOutput().get(i);
	}
	
	public void addOutput(long value) {
		getOutput().add(value);
	}

	public List<Long> getOutput() {
		return output;
	}

	@Override
	public String toString() {
		return memory.toString();
	}

	private OpCode getOpcode() {
		int operation = (int)getImmediateValue(index) % 100;
		if (operation<=OpCode.HALT.ordinal()) {
			return OpCode.values()[operation];
		}
		return OpCode.EMPTY;
	}

	private boolean noAvailableInput(OpCode instruction) {
		return instruction.equals(OpCode.INPUT) && inputCounter >= input.size();
	}
}