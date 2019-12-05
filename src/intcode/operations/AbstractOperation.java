package intcode.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.javatuples.Pair;

import intcode.IntcodeMachine;

public abstract class AbstractOperation implements BiFunction<IntcodeMachine, Integer, Integer> {

	public enum OpCode {
		EMPTY((m,i)->-1),
		ADD(new AddOperation()),
		MULTIPLY(new MultiplyOperation()),
		INPUT(new InputOperation()),
		OUTPUT(new OutputOperation()),
		JNZ(new JumpIfTrueOperation()),
		JEZ(new JumpIfFalseOperation()),
		LESS_THAN(new LessThanOperation()),
		EQUALS(new EqualsOperation()),
		TEMP9((m,i)->-1),
		TEMP10((m,i)->-1),
		TEMP11((m,i)->-1),
		TEMP12((m,i)->-1),
		TEMP13((m,i)->-1),
		TEMP14((m,i)->-1),
		TEMP15((m,i)->-1),
		TEMP16((m,i)->-1),
		TEMP17((m,i)->-1),
		TEMP18((m,i)->-1),
		TEMP19((m,i)->-1),
		TEMP20((m,i)->-1),
		TEMP21((m,i)->-1),
		TEMP22((m,i)->-1),
		TEMP23((m,i)->-1),
		TEMP24((m,i)->-1),
		TEMP25((m,i)->-1),
		TEMP26((m,i)->-1),
		TEMP27((m,i)->-1),
		TEMP28((m,i)->-1),
		TEMP29((m,i)->-1),
		TEMP30((m,i)->-1),
		TEMP31((m,i)->-1),
		TEMP32((m,i)->-1),
		TEMP33((m,i)->-1),
		TEMP34((m,i)->-1),
		TEMP35((m,i)->-1),
		TEMP36((m,i)->-1),
		TEMP37((m,i)->-1),
		TEMP38((m,i)->-1),
		TEMP39((m,i)->-1),
		TEMP40((m,i)->-1),
		TEMP41((m,i)->-1),
		TEMP42((m,i)->-1),
		TEMP43((m,i)->-1),
		TEMP44((m,i)->-1),
		TEMP45((m,i)->-1),
		TEMP46((m,i)->-1),
		TEMP47((m,i)->-1),
		TEMP48((m,i)->-1),
		TEMP49((m,i)->-1),
		TEMP50((m,i)->-1),
		TEMP51((m,i)->-1),
		TEMP52((m,i)->-1),
		TEMP53((m,i)->-1),
		TEMP54((m,i)->-1),
		TEMP55((m,i)->-1),
		TEMP56((m,i)->-1),
		TEMP57((m,i)->-1),
		TEMP58((m,i)->-1),
		TEMP59((m,i)->-1),
		TEMP60((m,i)->-1),
		TEMP61((m,i)->-1),
		TEMP62((m,i)->-1),
		TEMP63((m,i)->-1),
		TEMP64((m,i)->-1),
		TEMP65((m,i)->-1),
		TEMP66((m,i)->-1),
		TEMP67((m,i)->-1),
		TEMP68((m,i)->-1),
		TEMP69((m,i)->-1),
		TEMP70((m,i)->-1),
		TEMP71((m,i)->-1),
		TEMP72((m,i)->-1),
		TEMP73((m,i)->-1),
		TEMP74((m,i)->-1),
		TEMP75((m,i)->-1),
		TEMP76((m,i)->-1),
		TEMP77((m,i)->-1),
		TEMP78((m,i)->-1),
		TEMP79((m,i)->-1),
		TEMP80((m,i)->-1),
		TEMP81((m,i)->-1),
		TEMP82((m,i)->-1),
		TEMP83((m,i)->-1),
		TEMP84((m,i)->-1),
		TEMP85((m,i)->-1),
		TEMP86((m,i)->-1),
		TEMP87((m,i)->-1),
		TEMP88((m,i)->-1),
		TEMP89((m,i)->-1),
		TEMP90((m,i)->-1),
		TEMP91((m,i)->-1),
		TEMP92((m,i)->-1),
		TEMP93((m,i)->-1),
		TEMP94((m,i)->-1),
		TEMP95((m,i)->-1),
		TEMP96((m,i)->-1),
		TEMP97((m,i)->-1),
		TEMP98((m,i)->-1),
		HALT((m,i)->-1);
		
		public final BiFunction<IntcodeMachine, Integer, Integer> operation;
		
		private OpCode(BiFunction<IntcodeMachine, Integer, Integer> operation) {
			this.operation=operation;
		}
	}

	private int numberOfReadParameters;
	private int numberOfWriteParameters;

	@Override
	public Integer apply (IntcodeMachine machine, Integer i) {
		Params params = getParams(machine, i, getNumberOfReadParameters(), getNumberOfWriteParameters(), getParamsModes(machine, i));
		doOperation(params,machine);
		return getNewInstructionCounter(i);
	}

	protected abstract void doOperation(Params params, IntcodeMachine machine);

	protected Integer getNewInstructionCounter(int i) {
		return i + getNumberOfStepsToSkip();
	}

	protected int getNumberOfStepsToSkip() {
		return getNumberOfReadParameters() + getNumberOfWriteParameters() + 1;
	}

	protected int getNumberOfReadParameters() {
		return numberOfReadParameters;
	}

	protected void setNumberOfReadParameters(int number) {
		this.numberOfReadParameters=number;
	}

	protected int getNumberOfWriteParameters() {
		return numberOfWriteParameters;
	}

	protected void setNumberOfWriteParameters(int number) {
		this.numberOfWriteParameters=number;
	}

	protected static Params getParams(IntcodeMachine machine, Integer i, int numberOfReadParameters, int numberOfWriteParameters, int modes) {
		List<Integer> readParams = new ArrayList<>();
		int j, position;
		for (j = 1; j <= numberOfReadParameters; j++) {
			position = i+j;
			position = machine.getValue(position, modes % 10);
			modes /= 10;
			readParams.add(position);
		}
		List<Integer> writeParams = new ArrayList<>();
		for (; j <= numberOfReadParameters + numberOfWriteParameters; j++) {
			writeParams.add(i+j);
		}
		return new Params(readParams, writeParams);
	}

	protected int immediateValue(int i, IntcodeMachine machine) {
		return machine.getImmediateValue(i);
	}

	protected int positionValue(int i, IntcodeMachine machine) {
		return machine.getPositionValue(i);
	}

	protected int getValue(IntcodeMachine machine, int i, int mode) {
		return machine.getValue(i, mode);
	}

	private int getParamsModes(IntcodeMachine machine, int i) {
		return machine.getImmediateValue(i) / 100;
	}
}
