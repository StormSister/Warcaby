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

        boolean isDiagonalMove = isDiagonalMove(newX, newY);
        if (isCrowned) {
            Coordinates capturedPieceForKing = isJumpValidForKing(newX, newY);
            if (capturedPieceForKing != null) {
                return true;
            }
            if (isDiagonalMove && isMoveValidForKing(newX, newY)) {
                return true;
            }
        } else {
            if (isDiagonalMove) {
                if (isMoveValidForRegularPawn(newX, newY)) {
                    return true;
                } else {
                    Coordinates capturedPiece = isJumpValidForRegularPawn(newX, newY);
                    return capturedPiece != null;
                }
            }
        }

        return false;
    }

    private boolean isDiagonalMove(int newX, int newY) {
        int deltaX = Math.abs(newX - position.getX());
        int deltaY = Math.abs(newY - position.getY());
        return deltaX == deltaY;
    }

    private boolean isMoveValidForRegularPawn(int newX, int newY) {
        int deltaX = newX - position.getX();
        int deltaY = newY - position.getY();

        int direction = (color == Color.WHITE) ? 1 : -1;
        return isDiagonalMove(newX, newY) && deltaX == direction && Math.abs(deltaY) == 1;
    }

    public Coordinates isJumpValidForRegularPawn(int newX, int newY) {
        int deltaX = Math.abs(newX - position.getX());
        int deltaY = Math.abs(newY - position.getY());

        if (deltaX == 2 && deltaY == 2) {
            int middleX = (newX + position.getX()) / 2;
            int middleY = (newY + position.getY()) / 2;
            if (fields[middleX][middleY] != null && fields[middleX][middleY].getColor() != color) {
                return new Coordinates(middleX, middleY);
            }
        }

        return null;
    }

    private boolean isMoveValidForKing(int newX, int newY) {
        Coordinates pieceOnPath = getPieceOnPath(newX, newY);
        return isDiagonalMove(newX, newY) && pieceOnPath != null && pieceOnPath.getX() == 0 && pieceOnPath.getY() == 0;
    }

    public Coordinates isJumpValidForKing(int newX, int newY) {
        if (isDiagonalMove(newX, newY)) {
            Coordinates pieceOnPath = getPieceOnPath(newX, newY);
            if (pieceOnPath != null && fields[newX][newY] == null) {
                return pieceOnPath;
            }
        }
        return null;
    }

    public Coordinates getPieceOnPath(int newX, int newY) {
        int directionX = Integer.compare(newX, position.getX());
        int directionY = Integer.compare(newY, position.getY());
        int x = position.getX() + directionX;
        int y = position.getY() + directionY;
        int pieceCount = 0;
        Coordinates singlePieceCoordinates = null;
        while (x != newX && y != newY) {
            if (fields[x][y] != null) {
                pieceCount++;
                if (pieceCount > 1) {
                    return null;
                } else {
                    singlePieceCoordinates = new Coordinates(x, y);
                }
            }
            x += directionX;
            y += directionY;
        }
        if (pieceCount == 0) {
            return new Coordinates(0, 0);
        } else if (pieceCount == 1) {
            return singlePieceCoordinates;
        } else {
            return null;
        }
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }





    @Override
    public String toString() {
        if (isCrowned) {
            if (color == Color.WHITE) {
                return "\uD83D\uDD39";
            } else {
                return "\uD83D\uDD38";
            }
        } else {
            if (color == Color.WHITE) {
                return "\uD83D\uDD35";
            } else {
                return "\uD83D\uDFE0";
            }
        }
    }
}


enum Color {
    WHITE,
    BLACK
}