package Tests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import intcode.IntcodeMachine;

public class IntCodeTests {
	
	//Basic Math

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
	
	//IO and position vs. immediate

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
	
	//Branching

	@Test
	public void equalsReturnsTrueWhenImmediateEqualsTest() {
		int[] memory = new int[] {3,3,1108,-1,8,3,4,3,99};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(8));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(1L, (long)program.getOutput().get(0));
	}

	@Test
	public void equalsReturnsFalseWhenImmediateNotEqualsTest() {
		int[] memory = new int[] {3,3,1108,-1,8,3,4,3,99};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(7));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(0L, (long)program.getOutput().get(0));
	}

	@Test
	public void lessThanReturnsTrueWhenImmediateLessThanTest() {
		int[] memory = new int[] {3,3,1107,-1,8,3,4,3,99};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(7));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(1L, (long)program.getOutput().get(0));
	}

	@Test
	public void lessThanReturnsFalseWhenImmediateEqualsTest() {
		int[] memory = new int[] {3,3,1107,-1,8,3,4,3,99};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(8));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(0L, (long)program.getOutput().get(0));
	}

	@Test
	public void lessThanReturnsFalseWhenImmediateGreaterTest() {
		int[] memory = new int[] {3,3,1107,-1,8,3,4,3,99};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(9));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(0L, (long)program.getOutput().get(0));
	}

	@Test
	public void equalsReturnsTrueWhenPositionEqualsTest() {
		int[] memory = new int[] {3,9,8,9,10,9,4,9,99,-1,8};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(8));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(1L, (long)program.getOutput().get(0));
	}

	@Test
	public void equalsReturnsFalseWhenPositionNotEqualsTest() {
		int[] memory = new int[] {3,9,8,9,10,9,4,9,99,-1,8};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(7));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(0L, (long)program.getOutput().get(0));
	}

	@Test
	public void lessThanReturnsTrueWhenPositionLessThanTest() {
		int[] memory = new int[] {3,9,7,9,10,9,4,9,99,-1,8};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(7));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(1L, (long)program.getOutput().get(0));
	}

	@Test
	public void lessThanReturnsFalseWhenPositionEqualsTest() {
		int[] memory = new int[] {3,9,7,9,10,9,4,9,99,-1,8};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(8));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(0L, (long)program.getOutput().get(0));
	}

	@Test
	public void lessThanReturnsFalseWhenPositionGreaterTest() {
		int[] memory = new int[] {3,9,7,9,10,9,4,9,99,-1,8};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(9));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(0L, (long)program.getOutput().get(0));
	}

	@Test
	public void recognizesLessThanTest() {
		int[] memory = new int[] {3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(7));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(999L, (long)program.getOutput().get(0));
	}

	@Test
	public void recognizesEqualsTest() {
		int[] memory = new int[] {3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(8));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(1000L, (long)program.getOutput().get(0));
	}

	@Test
	public void recognizesGreaterThanTest() {
		int[] memory = new int[] {3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(9));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(1001L, (long)program.getOutput().get(0));
	}
	
	//Complete machine

	@Test
	public void quineTest() {
		int[] memory = new int[] {109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99};
		IntcodeMachine program = new IntcodeMachine(memory.clone());
		
		program.runProgram();

		assertEquals(memory.length, program.getOutput().size());
		for (int i = 0; i < memory.length; i++) {
			assertEquals(memory[i], (long)program.getOutput().get(i));
		}
	}

	@Test
	public void largeMultiplicationTest() {
		List<Long> memory = Arrays.asList(1102L,34915192L,34915192L,7L,4L,7L,99L,0L);
		IntcodeMachine program = new IntcodeMachine(memory);
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(1219070632396864L, (long)program.getOutput().get(0));
	}

	@Test
	public void largeValuesTest() {
		List<Long> memory = Arrays.asList(104L,1125899906842624L,99L);
		IntcodeMachine program = new IntcodeMachine(memory);
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(1125899906842624L, (long)program.getOutput().get(0));
	}

	@Test
	public void relativeInputTest() {
		int[] memory = new int[] {109,1,203,0,104,0,104,1};
		IntcodeMachine program = new IntcodeMachine(memory).addInput(Arrays.asList(5));
		
		program.runProgram();

		assertEquals(2, program.getOutput().size());
		assertEquals(109, (long)program.getOutput().get(0));
		assertEquals(5, (long)program.getOutput().get(1));
	}
}
