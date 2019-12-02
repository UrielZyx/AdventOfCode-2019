package Tests;
import static org.junit.Assert.*;

import org.junit.Test;

import intcode.Intcode;
import junit.framework.Assert;

public class IntCodeTest {

	@Test
	public void additionTest() {
		int[] memory = new int[] {1,0,0,0,99};
		Intcode program = new Intcode(memory);
		
		program.runProgram();
		
		assertEquals(program.result(0), 2);
	}

	@Test
	public void multiplicationTest() {
		int[] memory = new int[] {2,3,0,3,99};
		Intcode program = new Intcode(memory);
		
		program.runProgram();
		
		assertEquals(program.result(3), 6);
	}

	@Test
	public void multiplicationWithFarPlacesTest() {
		int[] memory = new int[] {2,4,4,5,99,0};
		Intcode program = new Intcode(memory);
		
		program.runProgram();
		
		assertEquals(program.result(5), 9801);
	}

	@Test
	public void multipleInstructionsTest() {
		int[] memory = new int[] {1,9,10,3,2,3,11,0,99,30,40,50};
		Intcode program = new Intcode(memory);
		
		program.runProgram();

		assertEquals(program.result(0), 3500);
		assertEquals(program.result(3), 70);
	}

	@Test
	public void opCodeOverrideTest() {
		int[] memory = new int[] {1,1,1,4,99,5,6,0,99};
		Intcode program = new Intcode(memory);
		
		program.runProgram();

		assertEquals(program.result(0), 30);
		assertEquals(program.result(4), 2);
	}

}
