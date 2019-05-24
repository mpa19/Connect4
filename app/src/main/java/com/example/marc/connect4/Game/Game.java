package com.example.marc.connect4.Game;

public class Game {

    private final Board board;
    private final int toWin;

    private Status status;

    public Game(int rows, int columns, int toWin) {

        this.toWin = toWin;
        this.board = new Board(rows, columns);
        this.status = Status.PLAYER1_PLAYS;
    }

    public Status getStatus() {
        return status;
    }

    public boolean canPlayColumn(int column) {
        return board.canPlayColumn(column);
    }

    private void comprovarpotjugarel2() {  // funció auxiliar utilitzat dins d'acció firstplayer
        if (!board.hasValidMoves()) {
            this.status = Status.DRAW;
        } else {
            this.status = Status.PLAYER2_PLAYS;
        }
    }

    private void firstplayer(Position pos) {    // acció utilitzat dins de la funció move play dins la condició if
        if (board.maxConnected(pos) >= toWin) {
            this.status = Status.PLAYER1_WINS;
        } else {
            comprovarpotjugarel2();
        }
    }

    private void comprovarpotjugarel1() {    // funció auxiliar utilitzat dins d'acció secondplayer
        if (!board.hasValidMoves()) {
            this.status = Status.DRAW;
        } else {
            this.status = Status.PLAYER1_PLAYS;
        }
    }

    private void secondplayer(Position pos) {   // acció utilitzat dins de la funció move play en l'apartat de else
        if (board.maxConnected(pos) >= toWin) {
            this.status = Status.PLAYER2_WINS;
        } else {
            comprovarpotjugarel1();
        }
    }

    public Move play(int column) {
        if (status == status.PLAYER1_PLAYS) {
            Position pos = board.play(column, Player.player1());
            firstplayer(pos);
            return new Move(Player.player1(), pos);
        } else {
            Position pos = board.play(column, Player.player2());
            secondplayer(pos);
            return new Move(Player.player2(), pos);
        }
    }

    public boolean isFinished() {
        return (this.status != Status.PLAYER1_PLAYS && this.status != Status.PLAYER2_PLAYS);
    }

}
