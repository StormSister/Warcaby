package org.example;

public class Pawn {
    private Color color;
    private Coordinates position;
    private boolean isCrowned;
    private int size;
    private Pawn[][] fields;


    public Pawn(Color color, int x, int y, int size, Pawn[][] fields) {
        this.color = color;
        this.position = new Coordinates(x, y);
        this.isCrowned = false;
        this.size = size;
        this.fields = fields;
    }


    public Color getColor() {
        return color;
    }

    public Coordinates getPosition() {
        return position;
    }

    public boolean isCrowned() {
        return isCrowned;
    }

    public void setCrowned(boolean crowned) {
        isCrowned = crowned;
    }

    public boolean isValidMove(int newX, int newY) {
        if (!isValidPosition(newX, newY)) {
            return false;
        }

        int currentX = position.getX();
        int currentY = position.getY();

        if (Math.abs(newX - currentX) == 2 && Math.abs(newY - currentY) == 2) {
            int middleX = (newX + currentX) / 2;
            int middleY = (newY + currentY) / 2;

            if (fields[middleX][middleY] != null && fields[middleX][middleY].getColor() != color &&
                    fields[newX][newY] == null) {
                return true;
            }
        } else if (Math.abs(newX - currentX) == 1 && Math.abs(newY - currentY) == 1) {
            return fields[newX][newY] == null;
        }
        return false;
    }
    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }



    @Override
    public String toString() {
        if (color == Color.WHITE) {
            return "🔘";
        } else {
            return "⭕";
        }
    }
}


enum Color {
    WHITE,
    BLACK
}