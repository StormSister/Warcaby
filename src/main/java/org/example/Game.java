package org.example;

import java.util.Scanner;


public class Game {
    private Board board;
    private Scanner scanner;
    private boolean player1Turn;

    public Game(Board board) {
        this.board = board;
        this.scanner = new Scanner(System.in);
        this.player1Turn = true;
    }

    public void start() {
        while (!checkForWinner()) {
            playRound();
        }
        scanner.close();
    }

    public void playRound() {
        System.out.println(board);
        if (player1Turn) {
            System.out.println("Player 1's turn (White)");
        } else {
            System.out.println("Player 2's turn (Black)");
        }

        int fromX, fromY, toX, toY;
        while (true) {
            System.out.print("Enter starting position (e.g., A1): ");
            String fromPosition = scanner.next().toUpperCase();
            fromX = fromPosition.charAt(0) - 'A';
            fromY = Integer.parseInt(fromPosition.substring(1)) - 1;

            System.out.print("Enter ending position (e.g., B2): ");
            String toPosition = scanner.next().toUpperCase();
            toX = toPosition.charAt(0) - 'A';
            toY = Integer.parseInt(toPosition.substring(1)) - 1;

            if (isValidMove(fromX, fromY, toX, toY)) {
                clearConsole();
                break;
            } else {
                System.out.println("Invalid move. Please try again.");
            }
        }

        board.movePawn(fromY, fromX, toY, toX);

        player1Turn = !player1Turn;
    }

    private boolean isValidMove(int fromX, int fromY, int toX, int toY) {
        if (!board.isValidPosition(fromY, fromX) || !board.isValidPosition(toY, toX)) {
            return false;
        }

        Pawn pawn = board.getFields()[fromY][fromX];
        if (pawn == null || (player1Turn && pawn.getColor() != Color.WHITE) || (!player1Turn && pawn.getColor() != Color.BLACK)) {
            return false;
        }

        return pawn.isValidMove(toY, toX);
    }

    private boolean checkForWinner() {
        if (board.getPlayer1Count() == 0) {
            System.out.println("Player 2 (Black) wins!");
            return true;
        } else if (board.getPlayer2Count() == 0) {
            System.out.println("Player 1 (White) wins!");
            return true;
        } else if (board.getPlayer1Count() == 1 && board.getPlayer2Count() == 1) {
            System.out.println("It's a draw!");
            return true;
        }
        return false;
    }

    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {

        }
    }
}