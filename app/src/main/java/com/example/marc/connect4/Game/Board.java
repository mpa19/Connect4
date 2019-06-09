package com.example.marc.connect4.Game;



public class Board {

    private final int numRows;
    private final int numColumns;
    private final Player[][] cells;

    public Board(int numRows, int numColumns) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.cells = new Player[this.numRows][this.numColumns];

        for (int i = 0; i < this.numRows; ++i) {
            for (int j = 0; j < this.numColumns; ++j) {
                cells[i][j] = null;
            }
        }
    }

    public boolean canPlayColumn(int column) {
        return ((column > 0 || column <= this.numColumns) && lastEmptyRow(column) >= 0);
    }

    public boolean hasValidMoves() {
        for (int j = 0; j < this.numColumns; j++) {
            if (lastEmptyRow(j) >= 0) {
                return true;
            }
        }
        return false;
    }

    public Position play(int column, Player player) {
        if (canPlayColumn(column)) {
            Position j = new Position(lastEmptyRow(column), column); // anotem la nova posició
            cells[lastEmptyRow(column)][column] = player; // com que dalt a baix fins al ultima fila i la columna dada per diferents jugadors.
            return j;                                   //retorno la nova posició
        } else {
            return null;
        }
    }

    public int lastEmptyRow(int column) {
        int aux = -1;
        if (column < 0 || column >= this.numColumns) {
            return -1;
        } else {
            for (int i = 0; i < this.numRows; ++i) {
                if (cells[i][column] == null) {
                    aux = i;
                }
            }
            return aux;
        }
    }

    public int maxConnected(Position position) {
        //down
        // el codi em va sortir massa llarg però, per simplificar em pots fer en funcions auxiliars.
        // en aquest cas no he fet funcions auxiliars perquè si feia les funcions auxiliars per tant, sortia els errors per això he deixat així.
        int max;
        Position pos = position.move(Direction.DOWN);
        int l = 1;
        while (pos.getRow() < this.numRows && cells[position.getRow()][position.getColumn()].isEqualTo(cells[pos.getRow()][pos.getColumn()])) {
            l++;
            pos = pos.move(Direction.DOWN);
        }
        // up
        Direction UP = Direction.DOWN.invert();

        pos = position.move(UP);
        while (pos.getRow() >= 0 && cells[position.getRow()][position.getColumn()].isEqualTo(cells[pos.getRow()][pos.getColumn()])) {
            l++;
            pos = pos.move(UP);
        }
        max = l;

        // horitzontal.
        l = 1;
        pos = position.move(Direction.RIGHT);
        while (pos.getColumn() < this.numColumns && cells[position.getRow()][position.getColumn()].isEqualTo(cells[pos.getRow()][pos.getColumn()])) {
            l++;
            pos = pos.move(Direction.RIGHT);
        }

        Direction LEFT = Direction.RIGHT.invert();

        pos = position.move(LEFT);
        while (pos.getColumn() >= 0 && cells[position.getRow()][position.getColumn()].isEqualTo(cells[pos.getRow()][pos.getColumn()])) {
            l++;
            pos = pos.move(LEFT);
        }
        if (l > max) {
            max = l;
        }

        // main_diagonal
        l = 1;
        pos = position.move(Direction.MAIN_DIAGONAL);
        while (pos.getRow() < this.numRows && pos.getColumn() < this.numColumns && cells[position.getRow()][position.getColumn()].isEqualTo(cells[pos.getRow()][pos.getColumn()])) {
            l++;
            pos = pos.move(Direction.MAIN_DIAGONAL);
        }

        // inversa de main diagonal
        Direction invers = Direction.MAIN_DIAGONAL.invert();

        pos = position.move(invers);
        while (pos.getColumn() >= 0 && pos.getRow() >= 0 && cells[position.getRow()][position.getColumn()].isEqualTo(cells[pos.getRow()][pos.getColumn()])) {
            l++;
            pos = pos.move(invers);
        }
        if (l > max) {
            max = l;
        }

        // contra_diagonal
        l = 1;
        pos = position.move(Direction.CONTRA_DIAGONAL);
        while (pos.getRow() < this.numRows && pos.getColumn() >= 0 && cells[position.getRow()][position.getColumn()].isEqualTo(cells[pos.getRow()][pos.getColumn()])) {
            l++;
            pos = pos.move(Direction.CONTRA_DIAGONAL);
        }

        // inversa de contra diagonal
        Direction invers2 = Direction.CONTRA_DIAGONAL.invert();

        pos = position.move(invers2);
        while (pos.getColumn() < this.numColumns && pos.getRow() >= 0 && cells[position.getRow()][position.getColumn()].isEqualTo(cells[pos.getRow()][pos.getColumn()])) {
            l++;
            pos = pos.move(invers2);
        }
        if (l > max) {
            max = l;
        }
        return max;
    }
}
