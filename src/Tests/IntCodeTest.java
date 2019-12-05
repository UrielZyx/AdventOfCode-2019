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
		IntcodeMachine program = new IntcodeMachine(memory, Arrays.asList(7));
		
		program.runProgram();

		assertEquals(1, program.getOutput().size());
		assertEquals(7, (int)program.getOutput().get(0));
	}
}
