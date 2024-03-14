package org.example;

import java.util.Scanner;

public class Board {
    private int size;
    private Pawn[][] fields;
    private int player1Count;
    private int player2Count;

    public Board() {

        this.size = 0;

        this.player1Count = 0;
        this.player2Count = 0;
        this.setSize();
        this.fields = new Pawn[size][size];
        initializePawns();
    }

    private void setSize() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the size of the board (between 10 and 20): ");
            if (scanner.hasNextInt()) {
                size = scanner.nextInt();
                if (size >= 10 && size <= 20) {
                    break;
                } else {
                    System.out.println("Invalid size. Size must be between 10 and 20.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next();
            }
        }
    }


    private void initializePawns() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((i + j) % 2 != 0) {
                    if (player1Count < 2 * size && (i < size / 2)) {
                        fields[i][j] = new Pawn(Color.WHITE, i, j, size, fields);
                        player1Count++;
                    } else if (player2Count < 2 * size && (i >= size - 4)){
                        fields[i][j] = new Pawn(Color.BLACK, i, j, size, fields);
                        player2Count++;
                    }
                }
            }
        }
    }
    public void removePawn(int row, int col) {
        if (isValidPosition(row, col) && fields[row][col] != null) {
            fields[row][col] = null;
            if (fields[row][col].getColor() == Color.WHITE) {
                player1Count--;
            } else {
                player2Count--;
            }
        }
    }

    public void movePawn(int fromRow, int fromCol, int toRow, int toCol) {
        if (isValidPosition(fromRow, fromCol) && isValidPosition(toRow, toCol) &&
                fields[fromRow][fromCol] != null && fields[toRow][toCol] == null) {
            Pawn pawn = fields[fromRow][fromCol];

            // Sprawdź czy ruch jest możliwy
            if (pawn.isValidMove(toRow, toCol)) {
                int middleRow = (fromRow + toRow) / 2;
                int middleCol = (fromCol + toCol) / 2;

                // Jeśli ruch jest biciem
                if (Math.abs(toRow - fromRow) == 2 && Math.abs(toCol - fromCol) == 2 &&
                        fields[middleRow][middleCol] != null &&
                        fields[middleRow][middleCol].getColor() != pawn.getColor()) {
                    removePawn(middleRow, middleCol); // Usuń pionka przeciwnika
                }

                fields[toRow][toCol] = pawn;
                fields[fromRow][fromCol] = null;
            } else {
                System.out.println("Invalid move. Please try again.");
            }
        } else {
            System.out.println("Invalid move. Please try again.");
        }
    }

    public Pawn[][] getFields() {
        return fields;
    }

    public int getPlayer1Count() {
        return player1Count;
    }

    public int getPlayer2Count() {
        return player2Count;
    }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("   ");
        for (int i = 0; i < size; i++) {
            sb.append("\u001B[32m").append(" ").append((char) ('A' + i));
        }
        sb.append("\n");
        for (int i = 0; i < size; i++) {
            sb.append((i + 1 < 10 ? " " : "")).append((i + 1)).append(" ");
            for (int j = 0; j < size; j++) {
                if ((i + j) % 2 == 0) {
                    sb.append("\u2B1C");
                } else {
                    if (fields[i][j] != null) {
                        sb.append(fields[i][j]);
                    } else {
                        sb.append("\u2B1B");
                    }
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}