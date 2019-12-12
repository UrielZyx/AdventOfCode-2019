package day12;

public class Day12_1 {

	public static void main(String[] args) {
		MoonTracker tracker = new MoonTracker("<x=17, y=5, z=1>;<x=-2, y=-8, z=8>;<x=7, y=-6, z=14>;<x=1, y=-10, z=4>");
		tracker.step(1000);
		System.out.println(tracker);
	}
	
}
