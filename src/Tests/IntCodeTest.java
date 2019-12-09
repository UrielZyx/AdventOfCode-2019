package Tests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import intcode.IntcodeMachine;

public class IntCodeTest {

	@Test
	public void additionTest() {
		int[] memory = new int[] {1,0,0,0,99};
		IntcodeMachine program = new IntcodeMachine(memory);
		
		program.runProgram();
		
		assertEquals(2, program.getImmediateValue(0));
	}

	@Test
	public void multiplicationTest() {
		int[] memory = new int[] {2,3,0,3,99};
		IntcodeMachine program = new IntcodeMachine(memory);
		
		program.runProgram();
		
		assertEquals(6, program.getImmediateValue(3));
	}

	@Test
	public void multiplicationWithFarPlacesTest() {
		int[] memory = new int[] {2,4,4,5,99,0};
		IntcodeMachine program = new IntcodeMachine(memory);
		
		program.runProgram();
		
		assertEquals(9801, program.getImmediateValue(5));
	}

	@Test
	public void multipleInstructionsTest() {
		int[] memory = new int[] {1,9,10,3,2,3,11,0,99,30,40,50};
		IntcodeMachine program = new IntcodeMachine(memory);
		
		program.runProgram();

		assertEquals(3500, program.getImmediateValue(0));
		assertEquals(70, program.getImmediateValue(3));
	}

	@Test
	public void opCodeOverrideTest() {
		int[] memory = new int[] {1,1,1,4,99,5,6,0,99};
		IntcodeMachine program = new IntcodeMachine(memory);
		
		program.runProgram();

		assertEquals(30, program.getImmediateValue(0));
		assertEquals(2, program.getImmediateValue(4));
	}

	@Test
	public void immediateValueTest() {
		int[] memory = new int[] {1002,4,3,4,33};
		IntcodeMachine program = new IntcodeMachine(memory);
		
		program.runProgram();

		assertEquals(99, program.getImmediateValue(4));
	}

	@Test
	public void negativesTest() {
		int[] memory = new int[] {1101,100,-1,4,0};
		IntcodeMachine program = new IntcodeMachine(memory);
		
		program.runProgram();

		assertEquals(99, program.getImmediateValue(4));
	}

	@Test
	public void inputOutputTest() {
		int[] memory = new int[] {3,0,4,0,99};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(7));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(7L, (long)program.getOutput().get(0));
	}

	@Test
	public void EqualsReturnsTrueWhenImmediateEqualsTest() {
		int[] memory = new int[] {3,3,1108,-1,8,3,4,3,99};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(8));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(1L, (long)program.getOutput().get(0));
	}

	@Test
	public void EqualsReturnsFalseWhenImmediateNotEqualsTest() {
		int[] memory = new int[] {3,3,1108,-1,8,3,4,3,99};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(7));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(0L, (long)program.getOutput().get(0));
	}

	@Test
	public void LessThanReturnsTrueWhenImmediateLessThanTest() {
		int[] memory = new int[] {3,3,1107,-1,8,3,4,3,99};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(7));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(1L, (long)program.getOutput().get(0));
	}

	@Test
	public void LessThanReturnsFalseWhenImmediateEqualsTest() {
		int[] memory = new int[] {3,3,1107,-1,8,3,4,3,99};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(8));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(0L, (long)program.getOutput().get(0));
	}

	@Test
	public void LessThanReturnsFalseWhenImmediateGreaterTest() {
		int[] memory = new int[] {3,3,1107,-1,8,3,4,3,99};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(9));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(0L, (long)program.getOutput().get(0));
	}

	@Test
	public void EqualsReturnsTrueWhenPositionEqualsTest() {
		int[] memory = new int[] {3,9,8,9,10,9,4,9,99,-1,8};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(8));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(1L, (long)program.getOutput().get(0));
	}

	@Test
	public void EqualsReturnsFalseWhenPositionNotEqualsTest() {
		int[] memory = new int[] {3,9,8,9,10,9,4,9,99,-1,8};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(7));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(0L, (long)program.getOutput().get(0));
	}

	@Test
	public void LessThanReturnsTrueWhenPositionLessThanTest() {
		int[] memory = new int[] {3,9,7,9,10,9,4,9,99,-1,8};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(7));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(1L, (long)program.getOutput().get(0));
	}

	@Test
	public void LessThanReturnsFalseWhenPositionEqualsTest() {
		int[] memory = new int[] {3,9,7,9,10,9,4,9,99,-1,8};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(8));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(0L, (long)program.getOutput().get(0));
	}

	@Test
	public void LessThanReturnsFalseWhenPositionGreaterTest() {
		int[] memory = new int[] {3,9,7,9,10,9,4,9,99,-1,8};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(9));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(0L, (long)program.getOutput().get(0));
	}

	@Test
	public void RecognizesLessThanTest() {
		int[] memory = new int[] {3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(7));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(999L, (long)program.getOutput().get(0));
	}

	@Test
	public void RecognizesEqualsTest() {
		int[] memory = new int[] {3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(8));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(1000L, (long)program.getOutput().get(0));
	}

	@Test
	public void RecognizesGreaterThanTest() {
		int[] memory = new int[] {3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(9));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(1001L, (long)program.getOutput().get(0));
	}
}
