package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import day3.IntersectionFinder;
import day3.ManhattanIntersectionFinder;
import day3.WirePath;

public class IntersectionFinderTest {

	@Test
	public void test1() {
		assertEquals(6,
				new ManhattanIntersectionFinder(
						new WirePath("R8,U5,L5,D3"),
						new WirePath("U7,R6,D4,L4")
						).findClosestIntersection());
	}

	@Test
	public void test2() {
		assertEquals(159,
				new ManhattanIntersectionFinder(
						new WirePath("R75,D30,R83,U83,L12,D49,R71,U7,L72"),
						new WirePath("U62,R66,U55,R34,D71,R55,D58,R83")
						).findClosestIntersection());
	}

	@Test
	public void test3() {
		assertEquals(135,
				new ManhattanIntersectionFinder(
						new WirePath("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"),
						new WirePath("U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")
						).findClosestIntersection());
	}
}
