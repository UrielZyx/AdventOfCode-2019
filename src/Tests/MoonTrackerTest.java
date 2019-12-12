package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import day12.MoonTracker;

public class MoonTrackerTest {

	@Test
	public void steppingTest() {
		MoonTracker tracker = new MoonTracker("<x=-1, y=0, z=2>;<x=2, y=-10, z=-7>;<x=4, y=-8, z=8>;<x=3, y=5, z=-1>");
		tracker.step(10);
		assertArrayEquals(new int[] {179}, new int[] {tracker.getTotalEnergy()});
	}

	@Test
	public void repeatingTest() {
		MoonTracker tracker = new MoonTracker("<x=-1, y=0, z=2>;<x=2, y=-10, z=-7>;<x=4, y=-8, z=8>;<x=3, y=5, z=-1>");
		assertArrayEquals(new long[] {2772}, new long[] {tracker.findFirstRepetitionPoint()});
	}

	@Test
	public void longRepeatingTest() {
		MoonTracker tracker = new MoonTracker("<x=-8, y=-10, z=0>;<x=5, y=5, z=10>;<x=2, y=-7, z=3>;<x=9, y=-8, z=-3>");
		assertArrayEquals(new long[] {4686774924L}, new long[] {tracker.findFirstRepetitionPoint()});
	}

}
