package day14;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day14_1 {

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    //"C:\\Users\\UrielZ\\Documents\\Workspaces\\Advent of code\\Advent\\src\\day14\\my test input.txt"
                    // "C:\\Users\\נטע\\Documents\\אוריאל\\projects\\Advent Of Code\\AdventOfCode\\src\\day14\\my test input.txt"
                    // "C:\\Users\\נטע\\Documents\\אוריאל\\projects\\Advent Of Code\\AdventOfCode\\src\\day14\\first test input.txt"
                    "C:\\Users\\נטע\\Documents\\אוריאל\\projects\\Advent Of Code\\AdventOfCode\\src\\day14\\real input.txt"
                    ));
                    NanoFactory factory = new NanoFactory(reader.lines().collect(Collectors.toList()));
                    System.out.println(factory.numberOfOresNeededForFuel(1L));
                    // This is cheating (did a binary search):
                    // These tests are super time-consuming
                    System.out.println(factory.numberOfOresNeededForFuel(4436981L));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}