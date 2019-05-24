package com.example.marc.connect4.Game;

public class Player {

    private static final char PLAYER1 = '1';
    private static final char PLAYER2 = '2';

    private final char id;

    private Player(char id) {
        this.id = id;
    }

    public static Player player1() {
        return new Player(PLAYER1);
    }

    public static Player player2() {
        return new Player(PLAYER2);
    }

    Player() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isEqualTo(Player other) {
        return other != null && this.id == other.id;
    }

    public boolean isPlayer1() {
        return this.id == '1';
    }

    public boolean isPlayer2() {
        return this.id == '2';
    }
}
