package Tests;
import static org.junit.Assert.*;

import org.junit.Test;

import intcode.IntcodeMachine;

public class IntCodeTest {

	@Test
	public void additionTest() {
		int[] memory = new int[] {1,0,0,0,99};
		IntcodeMachine program = new IntcodeMachine(memory);
		
		program.runProgram();
		
		assertEquals(2, program.result(0));
	}

	@Test
	public void multiplicationTest() {
		int[] memory = new int[] {2,3,0,3,99};
		IntcodeMachine program = new IntcodeMachine(memory);
		
		program.runProgram();
		
		assertEquals(6, program.result(3));
	}

	@Test
	public void multiplicationWithFarPlacesTest() {
		int[] memory = new int[] {2,4,4,5,99,0};
		IntcodeMachine program = new IntcodeMachine(memory);
		
		program.runProgram();
		
		assertEquals(9801, program.result(5));
	}

	@Test
	public void multipleInstructionsTest() {
		int[] memory = new int[] {1,9,10,3,2,3,11,0,99,30,40,50};
		IntcodeMachine program = new IntcodeMachine(memory);
		
		program.runProgram();

		assertEquals(3500, program.result(0));
		assertEquals(70, program.result(3));
	}

	@Test
	public void opCodeOverrideTest() {
		int[] memory = new int[] {1,1,1,4,99,5,6,0,99};
		IntcodeMachine program = new IntcodeMachine(memory);
		
		program.runProgram();

		assertEquals(30, program.result(0));
		assertEquals(2, program.result(4));
	}

}
