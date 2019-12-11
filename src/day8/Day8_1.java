package day8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import elvenImageFormat.Image;

public class Day8_1 {
	
	private final static int WIDTH = 25;
	private final static int HEIGHT = 6;
	
	public static void main(String[] args) {
		try {
			String input = new BufferedReader(new FileReader("C:\\Users\\UrielZ\\Documents\\Workspaces\\Advent of code\\Advent\\src\\day8\\image input - day 8.txt")).readLine();
			Image image = new Image(input,WIDTH,HEIGHT);
			System.out.println(image.calculateChecksum());
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}