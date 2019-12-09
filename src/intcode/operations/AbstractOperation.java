package intcode.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.javatuples.Pair;

import intcode.IntcodeMachine;
import intcode.operations.Params.ParamType;

public abstract class AbstractOperation implements BiFunction<IntcodeMachine, Long, Long> {

	public enum OpCode {
		EMPTY((m,i)->-1L),
		ADD(new AddOperation()),
		MULTIPLY(new MultiplyOperation()),
		INPUT(new InputOperation()),
		OUTPUT(new OutputOperation()),
		JNZ(new JumpIfTrueOperation()),
		JEZ(new JumpIfFalseOperation()),
		LESS_THAN(new LessThanOperation()),
		EQUALS(new EqualsOperation()),
		TEMP9((m,i)->-1L),
		TEMP10((m,i)->-1L),
		TEMP11((m,i)->-1L),
		TEMP12((m,i)->-1L),
		TEMP13((m,i)->-1L),
		TEMP14((m,i)->-1L),
		TEMP15((m,i)->-1L),
		TEMP16((m,i)->-1L),
		TEMP17((m,i)->-1L),
		TEMP18((m,i)->-1L),
		TEMP19((m,i)->-1L),
		TEMP20((m,i)->-1L),
		TEMP21((m,i)->-1L),
		TEMP22((m,i)->-1L),
		TEMP23((m,i)->-1L),
		TEMP24((m,i)->-1L),
		TEMP25((m,i)->-1L),
		TEMP26((m,i)->-1L),
		TEMP27((m,i)->-1L),
		TEMP28((m,i)->-1L),
		TEMP29((m,i)->-1L),
		TEMP30((m,i)->-1L),
		TEMP31((m,i)->-1L),
		TEMP32((m,i)->-1L),
		TEMP33((m,i)->-1L),
		TEMP34((m,i)->-1L),
		TEMP35((m,i)->-1L),
		TEMP36((m,i)->-1L),
		TEMP37((m,i)->-1L),
		TEMP38((m,i)->-1L),
		TEMP39((m,i)->-1L),
		TEMP40((m,i)->-1L),
		TEMP41((m,i)->-1L),
		TEMP42((m,i)->-1L),
		TEMP43((m,i)->-1L),
		TEMP44((m,i)->-1L),
		TEMP45((m,i)->-1L),
		TEMP46((m,i)->-1L),
		TEMP47((m,i)->-1L),
		TEMP48((m,i)->-1L),
		TEMP49((m,i)->-1L),
		TEMP50((m,i)->-1L),
		TEMP51((m,i)->-1L),
		TEMP52((m,i)->-1L),
		TEMP53((m,i)->-1L),
		TEMP54((m,i)->-1L),
		TEMP55((m,i)->-1L),
		TEMP56((m,i)->-1L),
		TEMP57((m,i)->-1L),
		TEMP58((m,i)->-1L),
		TEMP59((m,i)->-1L),
		TEMP60((m,i)->-1L),
		TEMP61((m,i)->-1L),
		TEMP62((m,i)->-1L),
		TEMP63((m,i)->-1L),
		TEMP64((m,i)->-1L),
		TEMP65((m,i)->-1L),
		TEMP66((m,i)->-1L),
		TEMP67((m,i)->-1L),
		TEMP68((m,i)->-1L),
		TEMP69((m,i)->-1L),
		TEMP70((m,i)->-1L),
		TEMP71((m,i)->-1L),
		TEMP72((m,i)->-1L),
		TEMP73((m,i)->-1L),
		TEMP74((m,i)->-1L),
		TEMP75((m,i)->-1L),
		TEMP76((m,i)->-1L),
		TEMP77((m,i)->-1L),
		TEMP78((m,i)->-1L),
		TEMP79((m,i)->-1L),
		TEMP80((m,i)->-1L),
		TEMP81((m,i)->-1L),
		TEMP82((m,i)->-1L),
		TEMP83((m,i)->-1L),
		TEMP84((m,i)->-1L),
		TEMP85((m,i)->-1L),
		TEMP86((m,i)->-1L),
		TEMP87((m,i)->-1L),
		TEMP88((m,i)->-1L),
		TEMP89((m,i)->-1L),
		TEMP90((m,i)->-1L),
		TEMP91((m,i)->-1L),
		TEMP92((m,i)->-1L),
		TEMP93((m,i)->-1L),
		TEMP94((m,i)->-1L),
		TEMP95((m,i)->-1L),
		TEMP96((m,i)->-1L),
		TEMP97((m,i)->-1L),
		TEMP98((m,i)->-1L),
		HALT((m,i)->-1L);
		
		public final BiFunction<IntcodeMachine, Long, Long> operation;
		
		private OpCode(BiFunction<IntcodeMachine, Long, Long> operation) {
			this.operation=operation;
		}
	}

	private List<ParamType> requiredParameters;

	@Override
	public Long apply (IntcodeMachine machine, Long i) {
		Params params = getParams(machine, i, getRequiredParameters(), getParamsModes(machine, i));
		doOperation(params,machine);
		return getNewInstructionCounter(i);
	}

	protected abstract void doOperation(Params params, IntcodeMachine machine);

	protected long getNewInstructionCounter(long i) {
		return i + getNumberOfStepsToSkip();
	}

	protected int getNumberOfStepsToSkip() {
		return getNumberOfRequiredParameters() + 1;
	}

	protected int getNumberOfRequiredParameters() {
		return getRequiredParameters().size();
	}

	protected List<ParamType> getRequiredParameters() {
		return requiredParameters;
	}

	protected void setParameters(List<ParamType> params) {
		requiredParameters = params;
	}

	protected static Params getParams(IntcodeMachine machine, long i, List<ParamType> requiresParams, int modes) {
		Params params = new Params();
		long j = 0, value;

		for (ParamType type : requiresParams) {
			value = i + ++j;
			if (type.equals(ParamType.READ)) {
				value = machine.getValue(value, modes % 10);
				modes /= 10;
			}
			params.addParam(type, value);
		}

		return params;
	}

	protected long immediateValue(int i, IntcodeMachine machine) {
		return machine.getImmediateValue(i);
	}

	protected long positionValue(int i, IntcodeMachine machine) {
		return machine.getPositionValue(i);
	}

	protected long getValue(IntcodeMachine machine, int i, int mode) {
		return machine.getValue(i, mode);
	}

	private int getParamsModes(IntcodeMachine machine, long i) {
		return (int)machine.getImmediateValue(i) / 100;
	}
}
