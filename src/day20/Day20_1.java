package day20;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class Day20_1 {
	public static void main(String[] args) {
		Maze maze = new Maze(linesOf("C:\\urm\\advent-of-code\\AdventOfCode-2019\\src\\day20\\puzzle input.txt"));
		System.out.println(maze.shortestPath());
	}

	private static List<List<Integer>> linesOf(String path) {
		try {
			return new BufferedReader(new FileReader(path))
					.lines()
					.map(s -> s.chars().mapToObj(Integer::valueOf).collect(Collectors.toList()))
					.collect(Collectors.toList());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
