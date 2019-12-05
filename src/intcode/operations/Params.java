package intcode.operations;

import java.util.List;

import org.javatuples.Pair;

import intcode.IntcodeMachine;

public class Params {
	
	Pair<List<Integer>, List<Integer>> pair;

	public Params(List<Integer> readParams, List<Integer> writeParams) {
		pair = Pair.with(readParams, writeParams);
	}
	
	public int getReadParam(IntcodeMachine machine, int i, int mode) {
		return machine.getValue(getReadParam(i), mode);
	}
	
	public int getReadParam(int i) {
		return getReadParams().get(i);
	}
	
	public int getWriteParam(int i) {
		return getWriteParams().get(i);
	}
	
	public List<Integer> getReadParams() {
		return pair.getValue0();
	}
	
	public List<Integer> getWriteParams() {
		return pair.getValue1();
	}
	
	@Override
	public String toString() {
		return pair.toString();
	}
}
