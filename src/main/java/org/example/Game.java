package org.example;

import java.util.Scanner;
import java.io.File; // Import for File
import java.io.FileNotFoundException; // Import for FileNotFoundException



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
        displayAsciiArt("src/main/ascii_art.txt");
        while (!checkForWinner()) {
            playRound();
        }
        scanner.close();
    }

    private void displayAsciiArt(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void playRound() {
        System.out.println(board);
        if (player1Turn) {
            System.out.println("Player 1's turn (Blue)");
        } else {
            System.out.println("Player 2's turn (Orange)");
        }

        int fromX, fromY, toX, toY;
        while (true) {
            clearConsole();
            System.out.print("Enter starting position (e.g., A1, A 12) or Q to quit: ");
            String fromPosition = scanner.nextLine().toUpperCase().trim();

            if (fromPosition.equals("Q")) {
                if (confirmQuit()) {
                    System.out.println("Exiting the game...");
                    return;
                } else {
                    continue;
                }
            }

            if (!isValidCoordinateFormat(fromPosition)) {
                System.out.println("Error: Invalid format. Please enter coordinates in the format A1, B2, etc.");
                continue;
            }

            fromPosition = fromPosition.replaceAll("\\s", "");
            fromX = fromPosition.charAt(0) - 'A';
            fromY = Integer.parseInt(fromPosition.substring(1)) - 1;

            System.out.print("Enter ending position (e.g., B2, B 15) or Q to quit: ");
            String toPosition = scanner.nextLine().toUpperCase().trim();

            if (toPosition.equals("Q")) {
                if (confirmQuit()) {
                    System.out.println("Exiting the game...");
                    return;
                } else {
                    continue;
                }
            }

            if (!isValidCoordinateFormat(toPosition)) {
                System.out.println("Error: Invalid format. Please enter coordinates in the format A1, B2, etc.");
                continue;
            }

            toPosition = toPosition.replaceAll("\\s", "");
            toX = toPosition.charAt(0) - 'A';
            toY = Integer.parseInt(toPosition.substring(1)) - 1;

            Pawn pawn = board.getFields()[fromY][fromX];

            if (isValidMove(fromX, fromY, toX, toY)) {
                break;
            } else {
                System.out.println("Invalid move. Please try again.");
            }
        }

        board.movePawn(fromY, fromX, toY, toX);
        player1Turn = !player1Turn;
    }

    private boolean isValidCoordinateFormat(String input) {
        return input.matches("^[A-Za-z]\\s?(1[0-9]|20|[1-9])$");
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

    private boolean confirmQuit() {
        System.out.print("Do you want to quit the game? (Y/N): ");
        String quitChoice = scanner.next().toUpperCase();
        if (quitChoice.equals("Y")) {
            System.out.print("Do you want to start a new game? (Y/N): ");
            String newGameChoice = scanner.next().toUpperCase();
            if (newGameChoice.equals("Y")) {
                Main.main(new String[0]);
            } else {
                System.out.println("Goodbye!");
                System.exit(0);
            }
        }
        return quitChoice.equals("Y");
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