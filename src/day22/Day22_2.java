package day22;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class Day22_2 {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // Deck deck = new Deck(10);
        Deck deck = new Deck(119315717514047L);
        try {
            deck.setShufflingMethod(input());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // deck.findOrigin(3, 1);
        deck.findOrigin(2020, 1);
        // deck.findOrigin(2020, 101741582076661L);
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