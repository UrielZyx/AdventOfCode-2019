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

	private Map<ParamType,List<Long>> params;

	public Params() {
		params = new HashMap<>();
		params.put(ParamType.READ, new ArrayList<>());
		params.put(ParamType.WRITE, new ArrayList<>());
	}
	
	public long getReadParam(int i) {
		return getReadParams().get(i);
	}
	
	public long getWriteParam(int i) {
		return getWriteParams().get(i);
	}
	
	public List<Long> getReadParams() {
		return params.get(ParamType.READ);
	}
	
	public List<Long> getWriteParams() {
		return params.get(ParamType.WRITE);
	}

	public void addParam(ParamType type, long value) {
		params.get(type).add(value);
	}
}
