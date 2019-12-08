package day8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day8_1 {
	public static void main(String[] args) {
		try {
			String input = new BufferedReader(new FileReader("C:\\Users\\UrielZ\\Documents\\Workspaces\\Advent of code\\Advent\\src\\day8\\image input - 8.1.txt")).readLine();
			Image image = new Image(input,25,6);
			System.out.println(image.calculateChecksum());
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}