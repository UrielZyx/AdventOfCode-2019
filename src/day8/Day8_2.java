package day8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import elvenImageFormat.Image;

public class Day8_2 {
	
	private final static int WIDTH = 25;
	private final static int HEIGHT = 6;
	private final static String BLACK_CHAR = " ";
	private final static String WHITE_CHAR = "#";
	
	public static void main(String[] args) {
		
		try {
			String input = new BufferedReader(new FileReader("C:\\Users\\UrielZ\\Documents\\Workspaces\\Advent of code\\Advent\\src\\day8\\image input - day 8.txt")).readLine();
			Image image = new Image(input,WIDTH,HEIGHT);
			int[][] finalImage = image.getFinalImage();

			for (int i = 0; i < HEIGHT; i++) {
				for (int j = 0; j < WIDTH; j++) {
					if (finalImage[i][j] == 0) {
						System.out.print(BLACK_CHAR);
					} else {
						System.out.print(WHITE_CHAR);
					}
				}
				System.out.println();
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}