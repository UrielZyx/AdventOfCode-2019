package day18;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day18_1 {

    public static void main(String[] args) {
        try {
        	long start = System.currentTimeMillis();
            BufferedReader reader = new BufferedReader(new FileReader(
//                    "C:\\Users\\UrielZ\\Documents\\Workspaces\\Advent of code\\Advent\\src\\day18\\test input 1.txt"
//                    "C:\\Users\\UrielZ\\Documents\\Workspaces\\Advent of code\\Advent\\src\\day18\\test input 2.txt"
//                    "C:\\Users\\UrielZ\\Documents\\Workspaces\\Advent of code\\Advent\\src\\day18\\test input 3.txt"
                    "C:\\Users\\UrielZ\\Documents\\Workspaces\\Advent of code\\Advent\\src\\day18\\test input 4.txt"
//                    "C:\\Users\\UrielZ\\Documents\\Workspaces\\Advent of code\\Advent\\src\\day18\\test input 5.txt"
//                    "C:\\Users\\UrielZ\\Documents\\Workspaces\\Advent of code\\Advent\\src\\day18\\puzzle input.txt"
                    ));
            TunnelNavigator navigator = new TunnelNavigator(reader.lines().collect(Collectors.toList()));
            System.out.println(navigator.getShortestPathToAllKeys());
            System.out.println(System.currentTimeMillis() - start);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}