package day22;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class Day22_1 {
    public static void main(String[] args) {
        // Deck deck = new Deck(10);
        long start = System.currentTimeMillis();
        Deck deck = new Deck(10007);
        try {
            deck.setShufflingMethod(input());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        deck.shuffle(2019);
        System.out.println(System.currentTimeMillis() - start);
        //1498
    }

    private static List<String> input() throws FileNotFoundException {
        return new BufferedReader(new FileReader(
            // "C:\\Users\\נטע\\Documents\\אוריאל\\projects\\Advent Of Code\\AdventOfCode\\src\\day22\\my test input.txt"
            // "C:\\Users\\נטע\\Documents\\אוריאל\\projects\\Advent Of Code\\AdventOfCode\\src\\day22\\test input 1.txt"
            // "C:\\Users\\נטע\\Documents\\אוריאל\\projects\\Advent Of Code\\AdventOfCode\\src\\day22\\test input 2.txt"
            // "C:\\Users\\נטע\\Documents\\אוריאל\\projects\\Advent Of Code\\AdventOfCode\\src\\day22\\test input 3.txt"
            // "C:\\Users\\נטע\\Documents\\אוריאל\\projects\\Advent Of Code\\AdventOfCode\\src\\day22\\test input 4.txt"
            "C:\\Users\\נטע\\Documents\\אוריאל\\projects\\Advent Of Code\\AdventOfCode\\src\\day22\\puzzle input.txt"
            )).lines().collect(Collectors.toList());
    }
}