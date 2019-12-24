package intcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import intcode.operations.AbstractOperation.OpCode;

public class IntcodeMachine {
	
	public enum ParametersMode {
		POSITION (IntcodeUtil::getPositionModePosition),
		IMMEDIATE (IntcodeUtil::getImmediateModePosition),
		RELATIVE (IntcodeUtil::getRelativeModePosition);
		
		public final BiFunction<IntcodeMachine, Long, Long> getValue;
		
		private ParametersMode(BiFunction<IntcodeMachine, Long, Long> getValue) {
			this.getValue = getValue;
		}
	}
	
	private Map<Long,Long> memory = new HashMap<>();
	private List<Long> input = new ArrayList<>();
	private List<Long> output = new ArrayList<>();
	long relativeBase = 0;
	Iterator<Long> inputIterator = input.iterator();
	private Consumer<Long> outputHandler = System.out::println;
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

	public IntcodeMachine(String memory) {
		String[] memoryCells = memory.split(",");
		for (int i = 0; i < memoryCells.length; i++) {
			this.memory.put((long)i, Long.parseLong(memoryCells[i]));
		}
	}

	public boolean runProgram() {
		OpCode instruction;
				
		while (true) {
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
		return getMemoryAt(0);
	}

	public long getRelativeModePosition(long i) {
		return getImmediateModePosition(relativeBase + getMemoryAt(i));
	}

	public void adjustRelativeBase(long i) {
		relativeBase += i;
	}
		
	public long getPositionModePosition(long i) {
		return getMemoryAt(getImmediateModePosition(i));
	}
	
	public void setPositionValue (long i, long value) {
		memory.put(getImmediateModePosition(i), value);
	}

	public long getImmediateModePosition(long i) {
		return i;
	}

	public long getMemoryAt(long i) {
		return memory.getOrDefault(i, 0L);
	}

	public long getPositionByMode(long i, int mode) {
		return ParametersMode.values()[mode].getValue.apply(this, i);
	}
	
	public long getNextInput() {
		long result = inputIterator.next();
		inputIterator.remove();
		return result;
	}
	
	public IntcodeMachine addInput(List<? extends Number> newInput) {
		this.input.addAll(toLongList(newInput));
		inputIterator = this.input.iterator();
		return this;
	}
	
	public IntcodeMachine setInputIterator(Iterator<Long> newInput) {
		this.inputIterator = newInput;
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

	public Iterable<Long> getInput() {
		return input;
	}
	
	public void addOutput(long value) {
		getOutput().add(value);
		outputHandler.accept(value);
	}

	public IntcodeMachine setOutputHandler(Consumer<Long> handler) {
		this.outputHandler = handler;
		return this;
	}

	public Long getOutput(int i) {
		List<Long> out = getOutput();
		if (i > out.size()) {
			return null;
		}
		return getOutput().get(i);
	}

	public List<Long> getOutput() {
		return output;
	}

	@Override
	public String toString() {
		return memory.toString();
	}

	private OpCode getOpcode() {
		int operation = (int)getMemoryAt(index) % 100;
		if (operation<=OpCode.HALT.ordinal()) {
			return OpCode.values()[operation];
		}
		return OpCode.EMPTY;
	}

	private boolean noAvailableInput(OpCode instruction) {
		return instruction.equals(OpCode.INPUT) && !inputIterator.hasNext();
	}
}