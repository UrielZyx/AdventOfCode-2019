package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import day11.PaintingRobot;

public class PaintingRobotTest {

	@Test
	public void test() {
		PaintingRobot robot = new PaintingRobot("104,1,104,0,104,0,104,0,104,1,104,0,104,1,104,0,104,0,104,1,104,1,104,0,104,1,104,0");
		robot.paint();
		assertArrayEquals(new int[] {6}, new int[] {robot.getNumberOfPaintedPanels()});
	}

}
