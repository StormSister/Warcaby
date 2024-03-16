package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Board board = new Board();
        Game game = new Game(board);
        game.start();

    }
}