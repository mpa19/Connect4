package com.example.marc.connect4.Game;

public class Position {

    private final int row;
    private final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public Position move(Direction direction) {
        return new Position(this.row + direction.getChangeInRow(), this.column + direction.getChangeInColumn());
    }

    public boolean isEqualTo(Position other) {
        return other != null && this.row == other.row && this.column == other.column;
    }

    public static int pathLength(Position pos1, Position pos2) {
        int fila, columna;

        fila = pos2.getRow() - pos1.getRow();
        columna = pos2.getColumn() - pos1.getColumn();

        if (fila > 0) {                   // per controla la posicio vertical i el diagonal
            fila++;
        } else {
            fila = (fila * -1) + 1;
        }

        if (columna > 0) {            //per controlar la posició horizontal i el diagonal
            columna++;
        } else {
            columna = (columna * -1) + 1;
        }

        if (fila == 0 && columna == 0) {  // quan es les dues posicions són iguals.
            fila++;
            columna++;
        }

        if (fila > columna) {
            return fila;
        } else {
            return columna;
        }
    }
}

