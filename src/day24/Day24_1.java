package day24;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Collectors;

public class Day24_1 {
	public static void main(String[] args) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(
//				      "C:\\Users\\UrielZ\\Documents\\Workspaces\\Advent of code\\Advent\\src\\day24\\test input.txt"
				      "C:\\Users\\UrielZ\\Documents\\Workspaces\\Advent of code\\Advent\\src\\day24\\puzzle input.txt"
			      ));
			BugTracker tracker = new BugTracker(reader.lines().collect(Collectors.toList()));
			tracker.findFirstRepeatingState();
			System.out.println(tracker.getBioDiversity());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	//30446641
}
