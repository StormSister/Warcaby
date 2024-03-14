package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board board = new Board();

        System.out.println("Initial Board:");
        System.out.println(board);

        Game game = new Game(board);
        game.start();

        scanner.close();
    }
}