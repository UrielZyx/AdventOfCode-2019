package intcode.operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Params {
	
	public enum ParamType{
		READ,
		WRITE
	}

	private Map<ParamType,List<Integer>> params;

	public Params() {
		params = new HashMap<>();
		params.put(ParamType.READ, new ArrayList<>());
		params.put(ParamType.WRITE, new ArrayList<>());
	}
	
	public int getReadParam(int i) {
		return getReadParams().get(i);
	}
	
	public int getWriteParam(int i) {
		return getWriteParams().get(i);
	}
	
	public List<Integer> getReadParams() {
		return params.get(ParamType.READ);
	}
	
	public List<Integer> getWriteParams() {
		return params.get(ParamType.WRITE);
	}

	public void addParam(ParamType type, int value) {
		params.get(type).add(value);
	}
}
